package ru.neoflex.deal.exception;

import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.Optional;

@Getter
public class BadRequestToService extends RuntimeException{
    private byte[] response;

    public BadRequestToService(Optional<ByteBuffer> response){
        super();
        response.ifPresent(byteBuffer -> this.response = byteBuffer.array());
    }
}
