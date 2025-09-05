package ar.com.challenge.patagonia.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransferDto {
	
	private BigDecimal amount;
	
	private String debitAccount;
	
	private String creditAccount;
	
	private String date;

}
