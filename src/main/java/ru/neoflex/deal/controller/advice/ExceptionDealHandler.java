package ru.neoflex.deal.controller.advice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.neoflex.deal.exception.ServiceInternalError;
import ru.neoflex.deal.exception.BadRequestToService;
import ru.neoflex.deal.exception.ErrorMessage;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Slf4j
@ControllerAdvice
public class ExceptionDealHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public String onInvalidApplicationId(EntityNotFoundException ex) {
        log.warn("Cannot found application by id {}", ex.getLocalizedMessage());
        return ex.getLocalizedMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceInternalError.class)
    public ErrorMessage badRequestToAnotherServer(ServiceInternalError ex) {
        return ex.getErrorMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestToService.class)
    public JsonNode onOtherServerException(BadRequestToService ex) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(ex.getResponse());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    public ErrorMessage onInvalidFormatException(InvalidFormatException e) {
        return new ErrorMessage(e.getTargetType().toString(), e.getMessage());
    }
}
