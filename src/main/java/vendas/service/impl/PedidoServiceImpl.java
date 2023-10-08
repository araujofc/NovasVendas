package vendas.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vendas.domain.entity.Cliente;
import vendas.domain.entity.Itempedido;
import vendas.domain.entity.Pedido;
import vendas.domain.entity.Produto;
import vendas.domain.enums.StatusPedido;
import vendas.domain.repository.Clientes;
import vendas.domain.repository.Itemspedidos;
import vendas.domain.repository.Pedidos;
import vendas.domain.repository.Produtos;
import vendas.exception.RegraNegocioException;
import vendas.rest.dto.ItemPedidoDTO;
import vendas.rest.dto.PedidoDTO;
import vendas.service.PedidoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clienteRepository;
    private final Produtos produtoRepository;
    private final Itemspedidos itemsPedidosRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow(()-> new RegraNegocioException("Código de Cliente Invalido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<Itempedido>itemsPedidos = converterItems(pedido,dto.getItems());
        repository.save(pedido);
        itemsPedidosRepository.saveAll(itemsPedidos);
        pedido.setItems(itemsPedidos);
        return pedido;
    }

    @Override
    public Optional< Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItems(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository.findById(id)
                .map(pedido -> {
                    pedido.setStatus((statusPedido));
                    return repository.save(pedido);
                }).orElseThrow(()-> new PedidoNaoEncontradoException());

    }
    private List<Itempedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if (items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem itens");
        }
        return items
                .stream()
                .map(dto -> {
                                Integer idProduto = dto.getProduto();
                                Produto produto = produtoRepository
                                        .findById(idProduto)
                                        .orElseThrow(
                                                ()-> new RegraNegocioException(
                                                        "Código de produto Inválido." + idProduto
                                                ));
                                Itempedido itempedido = new Itempedido();
                                itempedido.setQuantidade(dto.getQuantidade());
                                itempedido.setPedido(pedido);
                                itempedido.setProduto(produto);
                                return itempedido;
                }).collect(Collectors.toList());
    }
}
