package br.com.denisgmarques.products.controller;

import br.com.denisgmarques.products.dto.ProductDTO;
import br.com.denisgmarques.products.model.Image;
import br.com.denisgmarques.products.model.Product;
import br.com.denisgmarques.products.repository.ImageRepository;
import br.com.denisgmarques.products.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductRestController extends BaseRestController {

	@Autowired
	private ProductRepository productRep;
	
	@Autowired
	private ImageRepository imageRep;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(value = "/products")
	public @ResponseBody List<Product> getAllProducts() {
		return productRep.findAll();
	}
	
	@GetMapping(value = "/products/full")
	public @ResponseBody List<ProductDTO> getAllProductsCascade() {
		List<Product> products = productRep.findAll();
		
		List<ProductDTO> dtos = new ArrayList<>();
		
		products.forEach(item -> {
			ProductDTO pdto = modelMapper.map(item, ProductDTO.class);
			pdto.getImages().addAll(imageRep.findByProduct(item));
			pdto.getChilds().addAll(productRep.findChilds(item));
			
			dtos.add(pdto);
		});
		
		return dtos;
	}

	@GetMapping(value = "/products/{id}")
	public @ResponseBody Product showProduct(@PathVariable Long id) {
		return productRep.getOne(id);
	}
	
	@GetMapping(value = "/products/{id}/full")
	public @ResponseBody ProductDTO showFullProduct(@PathVariable Long id) {
		Product product = productRep.getOne(id);
		ProductDTO pdto = modelMapper.map(product, ProductDTO.class);
		
		if (product != null) {
			pdto.getImages().addAll(imageRep.findByProduct(product));
			pdto.getChilds().addAll(productRep.findChilds(product));
		}
		
		return pdto;
	}
	
	@GetMapping(value = "/products/{id}/images")
	public @ResponseBody List<Image> showImagesFromProduct(@PathVariable Long id) {
		Product product = productRep.getOne(id);
		return imageRep.findByProduct(product);
	}
	
	@GetMapping(value = "/products/{id}/childs")
	public @ResponseBody List<Product> showChildsFromProduct(@PathVariable Long id) {
		Product product = productRep.getOne(id);
		return productRep.findChilds(product);
	}

	@PostMapping(value = "/products")
	public @ResponseBody Product insertProduct(@RequestBody Product product) {
		return productRep.save(product);
	}

	@PutMapping(value = "/products/{id}")
	public @ResponseBody String updateProduct(@PathVariable Long id, @RequestBody Product product) {
		Product storedProduct = productRep.getOne(id);
		storedProduct.setName(product.getName());
		storedProduct.setDescription(product.getDescription());
		storedProduct.setParent(product.getParent());
		productRep.save(storedProduct);
		
		return "{ 'message' : 'Product updated successfully' }";
	}

	@DeleteMapping(value = "/products/{id}")
	public @ResponseBody String deleteProduct(@PathVariable Long id) {
		productRep.deleteById(id);
		return "{ 'message' : 'Product deleted successfully' }";
	}
}
