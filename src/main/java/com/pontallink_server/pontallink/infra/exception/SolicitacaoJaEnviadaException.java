package com.pontallink_server.pontallink.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class SolicitacaoJaEnviadaException extends RuntimeException {

    public SolicitacaoJaEnviadaException(String mensagem) {
        super(mensagem);
    }
}
