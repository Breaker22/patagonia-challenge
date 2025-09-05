package ar.com.challenge.patagonia.response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ar.com.challenge.patagonia.dto.EnterpriceDto;
import ar.com.challenge.patagonia.dto.TransferDto;
import lombok.Data;

@Data
public class EnterpriceTransferResponse {
	
	private EnterpriceDto enterprice;
	
	private List<TransferDto> listTrasnfers;
	
	@JsonIgnore
	public LocalDate getEnterpriceDate() {
		return enterprice.parseAdhesionDate();
	}

}
