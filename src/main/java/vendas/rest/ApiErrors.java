package vendas.rest;

import lombok.Data;
import lombok.Getter;
import org.apache.catalina.LifecycleState;

import java.util.Arrays;
import java.util.List;


public class ApiErrors {
    @Getter
    private List<String> errors;

    public ApiErrors(String mensagemErro){
        this.errors = Arrays.asList(mensagemErro);
    }

}
