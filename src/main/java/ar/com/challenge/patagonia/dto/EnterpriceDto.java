package ar.com.challenge.patagonia.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EnterpriceDto {
	
	private String cuit;
	
	private String bussinessName;
	
	private String adhesionDate;
	
	public LocalDate parseAdhesionDate() {
		return LocalDate.parse(adhesionDate);
	}

}
