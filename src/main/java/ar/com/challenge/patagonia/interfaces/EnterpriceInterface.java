package ar.com.challenge.patagonia.interfaces;

import java.util.List;

import ar.com.challenge.patagonia.dto.EnterpriceDto;
import ar.com.challenge.patagonia.dto.TransferDto;
import ar.com.challenge.patagonia.response.EnterpriceTransferResponse;

public interface EnterpriceInterface {
	
	List<EnterpriceTransferResponse> getEnterpriceTransfers(Integer month);
	
	List<EnterpriceDto> getEnterpriceAdhesion(Integer month);
	
	void saveEnterprice(EnterpriceDto enterpriceDto);
	
	void saveTransfer(String cuit, TransferDto transferDto);
	
}
