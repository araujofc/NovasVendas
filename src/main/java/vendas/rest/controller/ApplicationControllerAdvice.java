package vendas.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import vendas.exception.RegraNegocioException;
import vendas.rest.ApiErrors;
import vendas.service.impl.PedidoNaoEncontradoException;

@ControllerAdvice
public class ApplicationControllerAdvice {
    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ApiErrors handleRegraNegocioException(RegraNegocioException ex){
            String mensagemErro = ex.getMessage();
            return new ApiErrors(mensagemErro);

        }
        @ExceptionHandler(PedidoNaoEncontradoException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public ApiErrors handlePedidosNotFoundException(PedidoNaoEncontradoException ex){
            return new ApiErrors(ex.getMessage());

        }
}
