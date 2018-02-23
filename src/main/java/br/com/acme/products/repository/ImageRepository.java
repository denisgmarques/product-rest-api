package br.com.acme.products.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.acme.products.model.Image;
import br.com.acme.products.model.Product;

public interface ImageRepository extends JpaRepository<Image, Long>{
	
	@Query("SELECT i FROM Image i WHERE i.product = :p")
	public List<Image> findByProduct(@Param("p") Product p);
	
}
