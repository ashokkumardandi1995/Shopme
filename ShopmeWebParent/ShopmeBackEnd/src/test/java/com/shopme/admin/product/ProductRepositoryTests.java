package com.shopme.admin.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateProduct() {
		Brand brand = entityManager.find(Brand.class, 1);
		Category category = entityManager.find(Category.class, 1);
		
		Product product = new Product();
		product.setName("SHOES FOR MEN");
		product.setAlias("SANDAK BLACK FORMAL_SHOES FOR MEN");
		product.setShortDescription("ARTICLE NUMBER : 8526");
		product.setFullDescription("MARKETED BY: Bata India Limited 27B, Camac Street, First Floor, Kolkata 700016 West Bengal");
		
		product.setBrand(brand);
		product.setCategory(category);
		
		product.setPrice(499);
		product.setCost(400);
		product.setEnabled(true);
		product.setInStock(true);
		product.setCreatedTime(new Date());
		product.setUpdatedTime(new Date());
		
		Product savedProduct = repo.save(product);
		
		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct.getId()).isGreaterThan(0);
		
	}
	@Test
	public void testListAllProducts() {
		Iterable<Product> iterableProducts = repo.findAll();
		
		iterableProducts.forEach(System.out::println);
	}
	@Test
	public void testGetProduct() {
		Integer id = 2;
		Product product = repo.findById(id).get();
		System.out.println(product);
		
		assertThat(product).isNotNull();
		}
	
	@Test
	public void testUpdateProduct() {
		Integer id = 1;
		Product product = repo.findById(id).get();
		product.setPrice(1999);
		
		repo.save(product);
		
		Product updatedProduct = entityManager.find(Product.class, id);	
		
		assertThat(updatedProduct.getPrice()).isEqualTo(1999);
	}
	

	@Test
	public void testDeleteProduct() {


		Integer id = 3;
		repo.deleteById(id);

		Optional<Product> result = repo.findById(id);

		assertThat(!result.isPresent());
	}
	@Test
	public void testSaveProductWithImages() {
		Integer productId = 1;
		Product product = repo.findById(productId).get();
		
		product.setMainImage("main image.jpg");
		product.addExtraImage("extra image 1.png");
		product.addExtraImage("extra_image_2.png");
		product.addExtraImage("extra-image3.png");
		
		Product savedProduct = repo.save(product);
		assertThat(savedProduct.getImages().size()).isEqualTo(3);
	}
	@Test
	public void testSaveProductWithDetails() {
		Integer productId = 1;
		Product product = repo.findById(productId).get();
		
		product.addDetails("Brand", "Bata"); 
		product.addDetails("Upper Material", "Fabric");
		product.addDetails("Colour", "Peacoat-Grey");
		
		Product savedProduct = repo.save(product);
		assertThat(savedProduct.getDetails()).isNotEmpty();
	}
	
}