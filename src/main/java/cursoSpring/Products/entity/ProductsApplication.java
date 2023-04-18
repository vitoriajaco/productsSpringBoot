package cursoSpring.Products.entity;

import cursoSpring.Products.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductsApplication {

	@Bean
	public CommandLineRunner commandLineRunner(@Autowired ClientesRepository clientes){
		return args -> {
			Cliente c = new Cliente(null, "Fulano");
			clientes.save(c);
		};

	}

	public static void main(String[] args) {
		SpringApplication.run(ProductsApplication.class, args);
	}

}
