package com.shopme.admin.brand;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandRepositoryTests {

	@Autowired
	private BrandRepository repo;
	
	@Test
	public void testCreateBrand1() {
		Category shoes = new Category(1);
		Brand bata = new Brand("Bata");
		bata.getCategories().add(shoes);
		
		Brand saveBrand = repo.save(bata);
		
		assertThat(saveBrand).isNotNull();
		assertThat(saveBrand.getId()).isGreaterThan(0);
			
	}
	@Test
	public void testCreateBrand2() {
		Category heels = new Category(33);
		Category slippers = new Category(35);
		
		Brand USpolo = new Brand("UsPolo");
		
		USpolo.getCategories().add(heels);
		USpolo.getCategories().add(slippers);
		
		Brand saveBrand = repo.save(USpolo);
		assertThat(saveBrand).isNotNull();
		assertThat(saveBrand.getId()).isGreaterThan(0);
   }
	@Test
	public void testCreateBrand3() {
		Brand adidas = new Brand("Adidas");
		
		adidas.getCategories().add(new Category(36));
		adidas.getCategories().add(new Category(38));
		
		Brand saveBrand = repo.save(adidas);
		assertThat(saveBrand).isNotNull();
		assertThat(saveBrand.getId()).isGreaterThan(0);
	}
	
	@Test
	public void findAll() {
		Iterable<Brand> brands = repo.findAll();
		brands.forEach(System.out::println);
		
		assertThat(brands).isNotEmpty();
	}
	
	@Test
	public void testGetById() {
		Brand brand = repo.findById(1).get();
		assertThat(brand.getName()).isEqualTo("Bata");
	}
		@Test
		public void testUpdateName() {
			
			String newName = "adidas Originals";
			Brand adidas = repo.findById(3).get();
			adidas.setName(newName);
			
			Brand savedBrand = repo.save(adidas);
			assertThat(savedBrand.getName()).isEqualTo(newName);
			
		}
		
		@Test
		public void testDelete() {
			Integer id = 2;
			repo.deleteById(id);
			
			Optional<Brand> result = repo.findById(id);
			
			assertThat(result.isEmpty());
		}
}