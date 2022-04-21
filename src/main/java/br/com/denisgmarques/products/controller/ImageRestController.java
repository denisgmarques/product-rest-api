package br.com.denisgmarques.products.controller;

import br.com.denisgmarques.products.model.Image;
import br.com.denisgmarques.products.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ImageRestController extends BaseRestController {

	@Autowired
	private ImageRepository imageRep;

	@GetMapping(value = "/images")
	public @ResponseBody List<Image> getAllImages() {
		return imageRep.findAll();
	}

	@GetMapping(value = "/images/{id}")
	public @ResponseBody Image showImage(@PathVariable Long id) {
		return imageRep.getOne(id);
	}

	@PostMapping(value = "/images")
	public @ResponseBody Image insertImage(@RequestBody Image Image) {
		return imageRep.save(Image);
	}

	@PutMapping(value = "/images/{id}")
	public @ResponseBody String updateImage(@PathVariable Long id, @RequestBody Image Image) {
		Image storedImage = imageRep.getOne(id);
		storedImage.setType(Image.getType());
		storedImage.setProduct(Image.getProduct());
		imageRep.save(storedImage);
		return "{ 'message' : 'Image updated successfully' }";
	}

	@DeleteMapping(value = "/images/{id}")
	public @ResponseBody String deleteImage(@PathVariable Long id) {
		imageRep.deleteById(id);
		return "{ 'message' : 'Image deleted successfully' }";
	}
}
