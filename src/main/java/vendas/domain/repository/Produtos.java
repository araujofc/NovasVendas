package vendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vendas.domain.entity.Produto;

public interface Produtos extends JpaRepository<Produto,Integer> {

}
