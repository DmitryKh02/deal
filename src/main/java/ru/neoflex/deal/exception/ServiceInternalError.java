package ru.neoflex.deal.exception;

import lombok.Getter;

@Getter
public class ServiceInternalError extends RuntimeException {
    private final ErrorMessage errorMessage;

    public ServiceInternalError(ErrorMessage errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }

}
