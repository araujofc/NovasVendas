package vendas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
@AllArgsConstructor
@Builder
public class InformacoesPedidoDTO {
    private Integer codigo;
    private String dataPedido;
    private String cpf;
    private String nomeCliente;
    private BigDecimal total;
    private String status;
    private List<InformacaoItemPedidoDTO> items;
}
