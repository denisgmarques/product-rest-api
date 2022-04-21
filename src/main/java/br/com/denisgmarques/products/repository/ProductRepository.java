package br.com.denisgmarques.products.repository;

import br.com.denisgmarques.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{

	@Query("SELECT p FROM Product p WHERE p.parent = :parent")
	public List<Product> findChilds(@Param("parent") Product p);
	
}
