package br.com.denisgmarques.products.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "image")
public class Image  implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    private String type;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
