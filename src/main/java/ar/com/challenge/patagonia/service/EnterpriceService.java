package ar.com.challenge.patagonia.service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ar.com.challenge.patagonia.dto.EnterpriceDto;
import ar.com.challenge.patagonia.dto.TransferDto;
import ar.com.challenge.patagonia.entity.Enterprice;
import ar.com.challenge.patagonia.entity.TransferData;
import ar.com.challenge.patagonia.exception.EnterpriceException;
import ar.com.challenge.patagonia.interfaces.EnterpriceInterface;
import ar.com.challenge.patagonia.repository.EnterpriceRepository;
import ar.com.challenge.patagonia.repository.TransferDataRepository;
import ar.com.challenge.patagonia.response.EnterpriceTransferResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class EnterpriceService implements EnterpriceInterface {

	private final EnterpriceRepository enterpriceRepo;
	private final TransferDataRepository transferDataRepo;

	@Override
	public List<EnterpriceTransferResponse> getEnterpriceTransfers(Integer month) {

		validateMonth(month);

		log.info("Intentando buscar transferencias del mes {}", month);

		List<EnterpriceTransferResponse> listTransfers = new ArrayList<>();

		LocalDate dateFrom = LocalDate.of(LocalDate.now().getYear(), month, 1);

		if (month == 1) {
			dateFrom = LocalDate.of(LocalDate.now().getYear() - 1, 12, 1);
		}

		LocalDate dateTo = LocalDate.of(LocalDate.now().getYear(), month, 1).with(TemporalAdjusters.lastDayOfMonth());

		List<TransferData> listTransferData = transferDataRepo.findByDateBetween(dateFrom, dateTo);

		if (CollectionUtils.isEmpty(listTransferData)) {
			throw new EnterpriceException(HttpStatus.NOT_FOUND, "No existen datos para ese mes!");
		}

		Map<Enterprice, List<TransferData>> mapEnterpriceTransfer = listTransferData.stream()
				.collect(Collectors.groupingBy(TransferData::getEnterprice));

		for (Entry<Enterprice, List<TransferData>> entryTransfer : mapEnterpriceTransfer.entrySet()) {
			EnterpriceTransferResponse response = new EnterpriceTransferResponse();
			ArrayList<TransferDto> listTransferDto = new ArrayList<>();

			response.setEnterprice(new EnterpriceDto(entryTransfer.getKey().getCuit(),
					entryTransfer.getKey().getBussinessName(), entryTransfer.getKey().getAdhesionDate().toString()));

			for (TransferData transfer : entryTransfer.getValue()) {
				listTransferDto.add(new TransferDto(transfer.getAmount(), transfer.getDebitAccount(),
						transfer.getCreditAccount(), transfer.getDate().toString()));
			}
			
			listTransferDto.sort(Comparator.comparing(TransferDto::getDate));

			response.setListTrasnfers(listTransferDto);

			listTransfers.add(response);
		}

		listTransfers.sort(Comparator.comparing(EnterpriceTransferResponse::getEnterpriceDate));

		log.info("Se encontraron transferencias del mes {}", month);

		return listTransfers;
	}

	@Override
	public List<EnterpriceDto> getEnterpriceAdhesion(Integer month) {
		validateMonth(month);

		log.info("Intentando buscar empresas adheridas del mes {}", month);

		LocalDate dateFrom = LocalDate.of(LocalDate.now().getYear(), month, 1);

		if (month == 1) {
			dateFrom = LocalDate.of(LocalDate.now().getYear() - 1, 12, 1);
		}

		LocalDate dateTo = LocalDate.of(LocalDate.now().getYear(), month, 1).with(TemporalAdjusters.lastDayOfMonth());

		ArrayList<EnterpriceDto> listResponse = new ArrayList<>();
		List<Enterprice> listEnterprice = enterpriceRepo.findByAdhesionDateBetween(dateFrom, dateTo);

		if (CollectionUtils.isEmpty(listEnterprice)) {
			throw new EnterpriceException(HttpStatus.NOT_FOUND, "No existen datos para ese mes!");
		}

		for (Enterprice enterprice : listEnterprice) {
			listResponse.add(new EnterpriceDto(enterprice.getCuit(), enterprice.getBussinessName(),
					enterprice.getAdhesionDate().toString()));
		}

		log.info("Se encontraron empresas adheridas del mes {}", month);

		return listResponse;
	}

	@Override
	public void saveEnterprice(EnterpriceDto enterpriceDto) {
		log.info("Intentando guardar empresa");

		enterpriceRepo.save(new Enterprice(enterpriceDto.getCuit(), enterpriceDto.getBussinessName(),
				enterpriceDto.parseAdhesionDate()));

		log.info("La empresa se guardo OK");
	}

	@Override
	public void saveTransfer(String cuit, TransferDto transferDto) {
		log.info("Intentando guardar transferencia");

		Enterprice enterprice = enterpriceRepo.findById(cuit)
				.orElseThrow(() -> new EnterpriceException(HttpStatus.NOT_FOUND, "La empresa no existe!"));

		TransferData transfer = new TransferData();

		transfer.setAmount(transferDto.getAmount());
		transfer.setCreditAccount(transferDto.getCreditAccount());
		transfer.setDebitAccount(transferDto.getDebitAccount());
		transfer.setDate(LocalDate.parse(transferDto.getDate()));
		transfer.setEnterprice(enterprice);

		transferDataRepo.save(transfer);

		log.info("La transferencia se guardo OK");
	}

	private void validateMonth(Integer month) {
		if (month < 1 || month > 12) {
			throw new EnterpriceException(HttpStatus.BAD_REQUEST, "El mes ingresado no es valido!");
		}
	}
}
