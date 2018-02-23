package br.com.acme.products.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.acme.products.model.Image;
import br.com.acme.products.model.Product;

public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private Long id;
    
    private String name;
    
    private String description;

    private Product parent;

    private List<Product> childs = new ArrayList<>();
    
    private List<Image> images = new ArrayList<>();

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

	public List<Product> getChilds() {
		return childs;
	}

	public void setChilds(List<Product> childs) {
		this.childs = childs;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}
    
}
