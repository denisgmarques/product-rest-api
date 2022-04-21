package br.com.denisgmarques.products.repository;

import br.com.denisgmarques.products.model.Image;
import br.com.denisgmarques.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long>{
	
	@Query("SELECT i FROM Image i WHERE i.product = :p")
	public List<Image> findByProduct(@Param("p") Product p);
	
}
