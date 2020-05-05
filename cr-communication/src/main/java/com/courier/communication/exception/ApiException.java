package com.courier.communication.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

/**
 * ApiException - класс для упрощения вывода ошибок в CustomRestExceptionHandler
 *
 * @author Сергей Голицын (sgolitsyn)
 * @since 16.04.2018
 */
@Getter
@Setter
@NoArgsConstructor
public class ApiException {
  private HttpStatus status;
  private String message;
  private List<String> errors;

  public ApiException(HttpStatus status, String message, List<String> errors) {
    super();
    this.status = status;
    this.message = message;
    this.errors = errors;
  }

  public ApiException(HttpStatus status, String message, String error) {
    super();
    this.status = status;
    this.message = message;
    errors = Collections.singletonList(error);
  }
}
