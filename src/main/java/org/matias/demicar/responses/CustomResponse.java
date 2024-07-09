package org.matias.demicar.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse<T> {
    private HttpStatus status;
    private String message;
    private T data;
    private List<String> errors;

    public CustomResponse(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.errors = new ArrayList<>();
    }

    public CustomResponse(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.data = null;
        this.errors = errors;
    }
}

