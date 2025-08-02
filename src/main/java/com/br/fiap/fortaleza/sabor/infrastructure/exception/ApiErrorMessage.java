package com.br.fiap.fortaleza.sabor.infrastructure.exception;

import org.springframework.http.HttpStatus;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiErrorMessage {

    private final HttpStatus status;
    private final Map<String, List<String>> errors;

    public ApiErrorMessage(HttpStatus status, Map<String, List<String>> errors) {
        this.status = status;
        this.errors = errors != null ? errors : Collections.emptyMap();
    }

    public ApiErrorMessage(HttpStatus status, String field, String error) {
        this.status = status;
        this.errors = new HashMap<>();
        this.errors.put(field, Collections.singletonList(error));
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}