package br.com.acme.products.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.acme.products.dto.ProductDTO;
import br.com.acme.products.model.Image;
import br.com.acme.products.model.Product;
import br.com.acme.products.repository.ImageRepository;
import br.com.acme.products.repository.ProductRepository;

@RestController
public class ProductRestController extends BaseRestController {

	@Autowired
	private ProductRepository productRep;
	
	@Autowired
	private ImageRepository imageRep;
	
	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public @ResponseBody List<Product> getAllProducts() {
		return productRep.findAll();
	}
	
	@RequestMapping(value = "/products/full", method = RequestMethod.GET)
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

	@RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
	public @ResponseBody Product showProduct(@PathVariable Long id) {
		return productRep.findOne(id);
	}
	
	@RequestMapping(value = "/products/{id}/full", method = RequestMethod.GET)
	public @ResponseBody ProductDTO showFullProduct(@PathVariable Long id) {
		Product product = productRep.findOne(id);
		ProductDTO pdto = modelMapper.map(product, ProductDTO.class);
		
		if (product != null) {
			pdto.getImages().addAll(imageRep.findByProduct(product));
			pdto.getChilds().addAll(productRep.findChilds(product));
		}
		
		return pdto;
	}
	
	@RequestMapping(value = "/products/{id}/images", method = RequestMethod.GET)
	public @ResponseBody List<Image> showImagesFromProduct(@PathVariable Long id) {
		Product product = productRep.findOne(id);
		return imageRep.findByProduct(product);
	}
	
	@RequestMapping(value = "/products/{id}/childs", method = RequestMethod.GET)
	public @ResponseBody List<Product> showChildsFromProduct(@PathVariable Long id) {
		Product product = productRep.findOne(id);
		return productRep.findChilds(product);
	}

	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public @ResponseBody Product insertProduct(@RequestBody Product product) {
		return productRep.save(product);
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
	public @ResponseBody String updateProduct(@PathVariable Long id, @RequestBody Product product) {
		Product storedProduct = productRep.findOne(id);
		storedProduct.setName(product.getName());
		storedProduct.setDescription(product.getDescription());
		storedProduct.setParent(product.getParent());
		productRep.save(storedProduct);
		
		return "{ 'message' : 'Product updated successfully' }";
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String deleteProduct(@PathVariable Long id) {
		productRep.delete(id);
		return "{ 'message' : 'Product deleted successfully' }";
	}
}
