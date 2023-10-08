package vendas.service;

import vendas.domain.entity.Pedido;
import vendas.domain.enums.StatusPedido;
import vendas.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar (PedidoDTO dto);
    Optional<Pedido> obterPedidoCompleto(Integer ID);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
