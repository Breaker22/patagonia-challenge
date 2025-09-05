package ar.com.challenge.patagonia.interfaces;

import java.util.List;

import ar.com.challenge.patagonia.dto.EnterpriceDto;
import ar.com.challenge.patagonia.dto.TransferDto;
import ar.com.challenge.patagonia.response.EnterpriceTransferResponse;

public interface EnterpriceInterface {

	/**
	 * Obtiene las transferencias de una empresa
	 * 
	 * @param month - mes a consultar
	 * @return la lista de empresas que tienen transferencias en ese mes
	 */
	List<EnterpriceTransferResponse> getEnterpriceTransfers(Integer month);

	/**
	 * Obtiene las empresas en un mes de adhesion
	 * 
	 * @param month - mes a consultar
	 * @return la lista de empresas adheridas
	 */
	List<EnterpriceDto> getEnterpriceAdhesion(Integer month);

	/**
	 * Guarda una empresa
	 * 
	 * @param enterpriceDto - datos de la empresa
	 */
	void saveEnterprice(EnterpriceDto enterpriceDto);

	/**
	 * Guarda una transferencia para una empresa
	 * 
	 * @param cuit        - cuit de la empresa
	 * @param transferDto - datos de la transferencia
	 */
	void saveTransfer(String cuit, TransferDto transferDto);

}
