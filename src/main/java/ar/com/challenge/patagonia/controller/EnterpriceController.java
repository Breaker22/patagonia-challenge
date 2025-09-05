package ar.com.challenge.patagonia.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.challenge.patagonia.dto.EnterpriceDto;
import ar.com.challenge.patagonia.dto.TransferDto;
import ar.com.challenge.patagonia.exception.EnterpriceException;
import ar.com.challenge.patagonia.interfaces.EnterpriceInterface;
import ar.com.challenge.patagonia.response.EnterpriceTransferResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/enterprice")
@AllArgsConstructor
public class EnterpriceController {
	
	public final EnterpriceInterface enterpriceInterface;
	
	@PostMapping
	public void saveEnterprice(@RequestBody EnterpriceDto enterpriceDto) {
		enterpriceInterface.saveEnterprice(enterpriceDto);
	}
	
	@PostMapping("/transfer")
	public void saveTransfer(@RequestParam String cuit, @RequestBody TransferDto transferDto) {
		enterpriceInterface.saveTransfer(cuit, transferDto);
	}
	
	@GetMapping("/transfer")
	public ResponseEntity<List<EnterpriceTransferResponse>> getEnterpriceTransfers(@RequestParam Integer month) {
		return ResponseEntity.ok().body(enterpriceInterface.getEnterpriceTransfers(month));
	}
	
	@GetMapping("/adhesion")
	public ResponseEntity<List<EnterpriceDto>> getEnterpriceAdhesion(@RequestParam Integer month) {
		return ResponseEntity.ok().body(enterpriceInterface.getEnterpriceAdhesion(month));
	}
	
	@ExceptionHandler(EnterpriceException.class)
	private ResponseEntity<String> handleExEnterprice(EnterpriceException ex) {
		return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
	}
}
