package vendas.rest.controller;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import vendas.domain.entity.Itempedido;
import vendas.domain.entity.Pedido;
import vendas.domain.enums.StatusPedido;
import vendas.rest.dto.AtualizacaoStatusPedidoDTO;
import vendas.rest.dto.InformacaoItemPedidoDTO;
import vendas.rest.dto.InformacoesPedidoDTO;
import vendas.rest.dto.PedidoDTO;
import vendas.service.PedidoService;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")

public class PedidoController {
    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }
    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody PedidoDTO dto){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();

    }
    @GetMapping("{id}")
    public InformacoesPedidoDTO getBYId(@PathVariable Integer id ){
        return service
                .obterPedidoCompleto(id)
                .map(p -> converter(p) )
                .orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND,"Pedido não encontrado"));
    }
    private InformacoesPedidoDTO converter(Pedido pedido){
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .total(pedido.getTotal())
                .nomeCliente(pedido.getCliente().getNome())
                .status(pedido.getStatus().name())
                .items(converter(pedido.getItems()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> converter(List<Itempedido> items){
        if (CollectionUtils.isEmpty(items)){
            return Collections.emptyList();
        }
        return items.stream().map(item ->   InformacaoItemPedidoDTO
                                            .builder().descricaoProduto(item.getProduto().getDescricao())
                                            .precoUnitario(item.getProduto().getPreco())
                                            .quantidade(item.getQuantidade())
                                            .build()
        ).collect(Collectors.toList());
    }
@PatchMapping("{id}")
@ResponseStatus(NO_CONTENT)
    public void updateStatus(    @PathVariable Integer id,
                            @RequestBody  AtualizacaoStatusPedidoDTO  dto){
        String novoStatus = dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));




    }

}

