package vendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vendas.domain.entity.Itempedido;

public interface Itemspedidos extends JpaRepository<Itempedido,Integer> {
}
