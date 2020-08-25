package modelo.excecoes;

import java.util.HashMap;
import java.util.Map;

public class ValidacaoException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    private Map<String, String> erros = new HashMap<>();

    public ValidacaoException(String msg) {
        super(msg);
    }

    public Map<String, String> obtemErros() {
        return erros;
    }

    public void adicionaErro(String fieldName, String errorMessage) {
        erros.put(fieldName, errorMessage);
    }

}
