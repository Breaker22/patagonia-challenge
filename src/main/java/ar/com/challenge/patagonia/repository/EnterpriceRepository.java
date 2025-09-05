package ar.com.challenge.patagonia.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.challenge.patagonia.entity.Enterprice;

@Repository
public interface EnterpriceRepository extends JpaRepository<Enterprice, String> {
	
	List<Enterprice> findByAdhesionDateBetween(LocalDate dateFrom, LocalDate dateTo);

}
