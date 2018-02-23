package br.com.acme.products.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.acme.products.model.Image;
import br.com.acme.products.repository.ImageRepository;

@RestController
public class ImageRestController extends BaseRestController {

	@Autowired
	private ImageRepository imageRep;

	@RequestMapping(value = "/images", method = RequestMethod.GET)
	public @ResponseBody List<Image> getAllImages() {
		return imageRep.findAll();
	}

	@RequestMapping(value = "/images/{id}", method = RequestMethod.GET)
	public @ResponseBody Image showImage(@PathVariable Long id) {
		return imageRep.findOne(id);
	}

	@RequestMapping(value = "/images", method = RequestMethod.POST)
	public @ResponseBody Image insertImage(@RequestBody Image Image) {
		return imageRep.save(Image);
	}

	@RequestMapping(value = "/images/{id}", method = RequestMethod.PUT)
	public @ResponseBody String updateImage(@PathVariable Long id, @RequestBody Image Image) {
		Image storedImage = imageRep.findOne(id);
		storedImage.setType(Image.getType());
		storedImage.setProduct(Image.getProduct());
		imageRep.save(storedImage);
		return "{ 'message' : 'Image updated successfully' }";
	}

	@RequestMapping(value = "/images/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String deleteImage(@PathVariable Long id) {
		imageRep.delete(id);
		return "{ 'message' : 'Image deleted successfully' }";
	}
}
