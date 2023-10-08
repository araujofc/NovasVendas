package vendas.service.impl;

public class PedidoNaoEncontradoException extends RuntimeException{
    public PedidoNaoEncontradoException(){
        super("pedido não encontrado.");
    }

}
