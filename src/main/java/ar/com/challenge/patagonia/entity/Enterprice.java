package ar.com.challenge.patagonia.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "enterprice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enterprice implements Serializable {

	private static final long serialVersionUID = 8721109886325041223L;
	
	@Id
	private String cuit;
	
	private String bussinessName;
	
	private LocalDate adhesionDate;

}
