package com.reservation.ticket.infrastructure.exception;

import com.reservation.ticket.interfaces.controller.dto.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GlobalControllerAdvice {

    /**
     * 커스텈 에러 처리 (HttpStatus 200)
     */
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handleApplicationException(ApplicationException ex) {
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus())
                .body(Response.error(ex.getMessage()));
    }

    /**
     * 장애 처리 (HttpStatus 500)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        log.error("ERROR {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.error(ex.getMessage()));
    }

}
