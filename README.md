# springboot_mongodb_demo


**Step1:** Go to [Spring Initializr](https://start.spring.io/) and create and download the project with following dependencies included: 
- Spring Web
- Spring Data MongoDB
- Spring Boot DevTools (Optional to add)


**Step2:** Extract the downloaded code and import it into IDE (Eclipse).


**Step3:** Launch the application and make sure MongoDB server is running, It will launch the application successfully.


**Step4:** Created Customer model as follows:

```
package com.ssk.springboot_mongodb_demo.model;

import org.springframework.data.annotation.Id;

public class Customer {

	@Id
	public String id;
	public String firstName;
	public String lastName;

	public Customer() {
	}

	public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return String.format("Customer[id=%s, firstName='%s', lastName='%s']", id, firstName, lastName);
	}

}
```

The above model will create a collection name 'customer' in MongoDB, if need to change collection name use @Document("custom_name") at the top of model class as follows:

```
package com.ssk.springboot_mongodb_demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Customer")
public class Customer {

	@Id
	public String id;
	public String firstName;
	public String lastName;

	public Customer() {
	}

	public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return String.format("Customer[id=%s, firstName='%s', lastName='%s']", id, firstName, lastName);
	}

}
```


**Step5:** Created CustomerRepository as follows:

```
package com.ssk.springboot_mongodb_demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssk.springboot_mongodb_demo.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

	public List<Customer> findByLastName(String lastName);

}
```

In the above repository, created one custom method to find customers by their last name.


**Step6:** Implemented CommandLineRunner in main class to test the repository as follows:

```
package com.ssk.springboot_mongodb_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ssk.springboot_mongodb_demo.model.Customer;
import com.ssk.springboot_mongodb_demo.repository.CustomerRepository;

@SpringBootApplication
public class SpringbootMongodbDemoApplication implements CommandLineRunner {

	@Autowired
	private CustomerRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMongodbDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// delete existing records
		repository.deleteAll();

		// save a couple of customers
		repository.save(new Customer("Alice", "Smith"));
		repository.save(new Customer("Bob", "Smith"));
		repository.save(new Customer("Tony", "Stark"));
		repository.save(new Customer("Peter", "Parker"));

		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer : repository.findAll()) {
			System.out.println(customer);
		}
		System.out.println();

		// fetch customers on condition base
		System.out.println("Customers found with findByLastName('Smith'):");
		System.out.println("--------------------------------");
		for (Customer customer : repository.findByLastName("Smith")) {
			System.out.println(customer);
		}

	}

}
```

**Note:** This project is assuming MongoDB is not authenticated. In case of no auth, collection will be created in db name 'test' by default.
 

