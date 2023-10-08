package vendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vendas.domain.entity.Cliente;

import java.util.List;

@Repository
public interface Clientes extends JpaRepository<Cliente, Integer> {

    List<Cliente>findByNomeLike(String nome);
    List<Cliente>findByNomeOrIdOrderById(String nome, Integer id);
    boolean existsByNome(String nome);
    @Query("select c from Cliente c left join fetch c.pedidos p where c.id  =:id  ")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);


}
