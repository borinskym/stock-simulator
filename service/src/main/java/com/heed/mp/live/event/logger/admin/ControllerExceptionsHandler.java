package com.heed.mp.live.event.logger.admin;

import com.heed.mp.live.event.logger.LiveDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ControllerExceptionsHandler {

    @ExceptionHandler(value = {LiveDataRepository.EventNotFound.class})
    public ResponseEntity<?> handleInvalidRequest(HttpServletRequest request, Throwable ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
