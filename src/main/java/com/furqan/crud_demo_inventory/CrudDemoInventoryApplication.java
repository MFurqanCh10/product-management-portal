package com.furqan.crud_demo_inventory;

import com.furqan.crud_demo_inventory.dao.ProductDao;
import com.furqan.crud_demo_inventory.entity.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CrudDemoInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudDemoInventoryApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ProductDao productDao) {
		return runner ->
		{

			//createProduct(productDao);

			//createMultipleProducts(productDao);

			//readProduct(productDao);

			//queryForProducts(productDao);

			//queryForProductsByItem_Vendor_Number(productDao);

			//updateProduct(productDao);

			//deleteProduct(productDao);

			//deleteAllProducts(productDao);

		};
	}

	private void deleteAllProducts(ProductDao productDao) {
		System.out.println("deleting all products :");
		int numRowsDeleted = productDao.deleteAll();
		System.out.println("deleted row count :" + numRowsDeleted);
	}

	private void deleteProduct(ProductDao productDao) {
		int ProductId = 3;
		System.out.println("deleting product id " + ProductId);
		productDao.delete(ProductId);
	}

	private void updateProduct(ProductDao productDao) {
		//retrieve product based on id; primary key
		int productId = 1;
		System.out.println("Getting product with id: " + productId);
		Product myproduct =  productDao.findByID(productId);

		//change first name to scooby
		System.out.println("update product .....");
		myproduct.setItem_Name("Headphone");

		//update the product
		productDao.update(myproduct);

		//display the update product
		System.out.println("updated product: " + myproduct);
	}

	private void queryForProductsByItem_Vendor_Number(ProductDao productDao) {
		//get a list of products
		List<Product> theProducts = productDao.findByItem_Vendor_Number("4067500");

		//display list of products
		for (Product tempProduct : theProducts){
			System.out.println(tempProduct);
		}
	}

	private void queryForProducts(ProductDao productDao) {
//get list of products
		List<Product> theProducts = productDao.findAll();

		//display list of products
		for(Product tempProduct : theProducts){
			System.out.println(tempProduct);
		}
	}

	private void readProduct(ProductDao productDao) {

		//create product object
		System.out.println("Creating a new product:");
		Product tempProduct = new Product("HandBag", "5THK6GH", "657456",
				"https://picsum.photos/200/300", "https://picsum.photos/seed/picsum/200/300");

		//save product
		System.out.println("saving product:");
		productDao.save(tempProduct);

		//display id of saved product
		int theId = tempProduct.getId();
		System.out.println("Saved Product. Generated id:" +theId);

		//RETRIEVE STUDENT BASED ON ID
		System.out.println("Retrieving product with id:" + theId);
		Product myProduct = productDao.findByID(theId);

		//display products
		System.out.println("found the product: " + myProduct);
	}

	private void createMultipleProducts(ProductDao productDao) {
// create multiple students

		System.out.println("Creating new Product object:");
		Product tempProduct1 = new Product(
				"Sprite", "6DF6@G454G", "55465",
				"https://picsum.photos/200/300",
				"https://picsum.photos/seed/picsum/200/300");
		Product tempProduct2 = new Product(
				"LemonMalt", "4fdfFD34", "4067500",
				"https://picsum.photos/200/300?grayscale",
				"https://picsum.photos/200/300/?blur=2");
		Product tempProduct3 = new Product(
				"PeachMalt", "45fgFG4fdf", "4534545",
				"https://picsum.photos/200/300.webp",
				"https://picsum.photos/id/237/200/300");

		// SAVE THE PRODUCTS
		System.out.println("saving the products:");

		productDao.save(tempProduct1);
		productDao.save(tempProduct2);
		productDao.save(tempProduct3);
	}

	private void createProduct(ProductDao productDao) {

		// create the product object
		System.out.println("Creating new Product object:");
		Product tempProduct = new Product(
				"CokaCola", "1Q2W3E4R", "56748",
				"https://www.kasandbox.org/programming-images/avatars/leaf-blue.png",
				"https://www.kasandbox.org/programming-images/avatars/cs-hopper-cool.png");

		//save product
		System.out.println("Saving product :");
		productDao.save(tempProduct);


		//display product
		System.out.println("saved product. Generated id: " + tempProduct.getId());


	}
}
















