package ru.neoflex.deal.exception;

public record ErrorMessage(
        String fieldName,
        String message) {
}
