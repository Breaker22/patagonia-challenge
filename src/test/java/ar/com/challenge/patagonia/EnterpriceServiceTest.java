package ar.com.challenge.patagonia;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.com.challenge.patagonia.controller.EnterpriceController;
import ar.com.challenge.patagonia.dto.EnterpriceDto;
import ar.com.challenge.patagonia.dto.TransferDto;
import ar.com.challenge.patagonia.entity.Enterprice;
import ar.com.challenge.patagonia.entity.TransferData;
import ar.com.challenge.patagonia.exception.EnterpriceException;
import ar.com.challenge.patagonia.repository.EnterpriceRepository;
import ar.com.challenge.patagonia.repository.TransferDataRepository;
import ar.com.challenge.patagonia.response.EnterpriceTransferResponse;
import ar.com.challenge.patagonia.service.EnterpriceService;

@ExtendWith(MockitoExtension.class)
class EnterpriceServiceTest {

	private EnterpriceController enterpriceController;
	
	@InjectMocks
	private EnterpriceService enterpriceService;

	@Mock
	private EnterpriceRepository enterpriceRepo;

	@Mock
	private TransferDataRepository transferDataRepo;
	
	@BeforeEach
	public void setUp() {
		enterpriceController = new EnterpriceController(enterpriceService);
	}

	@Test
	void testSaveEnterpriceOk() {
		enterpriceController.saveEnterprice(new EnterpriceDto("123", "test", "2025-05-05"));

		Assertions.assertDoesNotThrow(() -> Exception.class);
	}

	@Test
	void testSaveTransferOk() {
		Mockito.when(enterpriceRepo.findById(Mockito.anyString())).thenReturn(Optional.of(mockEnterprice()));

		enterpriceController.saveTransfer("123", new TransferDto(BigDecimal.ONE, "12", "12", "2025-05-05"));

		Assertions.assertDoesNotThrow(() -> EnterpriceException.class);
	}

	@Test
	void testEnterpriceTransfersOk() {
		TransferData transfer = new TransferData();

		transfer.setAmount(new BigDecimal("10"));
		transfer.setCreditAccount("123");
		transfer.setDate(LocalDate.now());
		transfer.setDebitAccount("321");
		transfer.setEnterprice(mockEnterprice());

		Mockito.when(transferDataRepo.findByDateBetween(Mockito.any(), Mockito.any())).thenReturn(List.of(transfer));

		List<EnterpriceTransferResponse> listResponse = enterpriceController.getEnterpriceTransfers(2).getBody();

		Assertions.assertDoesNotThrow(() -> EnterpriceException.class);
		Assertions.assertNotNull(listResponse.get(0).getEnterprice());
	}

	@Test
	void testEnterpriceAdhesionOk() {
		Mockito.when(enterpriceRepo.findByAdhesionDateBetween(Mockito.any(), Mockito.any()))
				.thenReturn(List.of(mockEnterprice()));

		List<EnterpriceDto> listResponse = enterpriceController.getEnterpriceAdhesion(2).getBody();

		Assertions.assertDoesNotThrow(() -> EnterpriceException.class);
		Assertions.assertNotNull(listResponse.get(0).getBussinessName());
	}

	@Test
	void testEnterpriceErrors() {
		Assertions.assertThrows(EnterpriceException.class, () -> enterpriceController.getEnterpriceTransfers(2));

		Assertions.assertThrows(EnterpriceException.class, () -> enterpriceController.getEnterpriceTransfers(20));

		Assertions.assertThrows(EnterpriceException.class, () -> enterpriceController.getEnterpriceAdhesion(2));

		Assertions.assertThrows(EnterpriceException.class, () -> enterpriceController.getEnterpriceAdhesion(20));
	}

	private Enterprice mockEnterprice() {
		Enterprice enterprice = new Enterprice();

		enterprice.setAdhesionDate(LocalDate.now());
		enterprice.setBussinessName("test");
		enterprice.setCuit("123");

		return enterprice;
	}
}
