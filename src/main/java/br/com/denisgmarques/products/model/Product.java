package br.com.denisgmarques.products.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    private String name;
    
    private String description;

    @ManyToOne
    @JoinColumn(name="parent_product_id")
    private Product parent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Product getParent() {
		return parent;
	}

	public void setParent(Product parent) {
		this.parent = parent;
	}
}
