package ar.com.challenge.patagonia.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.challenge.patagonia.entity.TransferData;

@Repository
public interface TransferDataRepository extends JpaRepository<TransferData, String> {
	
	List<TransferData> findByDateBetween(LocalDate dateFrom, LocalDate dateTo);
	
}
