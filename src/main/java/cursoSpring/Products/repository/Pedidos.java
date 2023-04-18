package cursoSpring.Products.repository;

import cursoSpring.Products.entity.Cliente;
import cursoSpring.Products.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);
}
