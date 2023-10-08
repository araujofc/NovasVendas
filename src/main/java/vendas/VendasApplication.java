package vendas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import vendas.domain.entity.Cliente;
import vendas.domain.repository.Clientes;

@SpringBootApplication
public class VendasApplication {
      public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);

    }
}
