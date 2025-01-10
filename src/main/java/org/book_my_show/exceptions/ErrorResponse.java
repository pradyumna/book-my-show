package org.book_my_show.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    private List<String> errors;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
