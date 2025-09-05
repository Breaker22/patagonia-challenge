package ar.com.challenge.patagonia.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transfer_data")
@Getter
@Setter
public class TransferData implements Serializable {

	private static final long serialVersionUID = 2768362505049350548L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	private BigDecimal amount;
	
	private String debitAccount;
	
	private String creditAccount;
	
	private LocalDate date;
	
	@ManyToOne
	@JoinColumn(name = "enterprice_id")
	private Enterprice enterprice;

}
