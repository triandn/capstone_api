package com.example.capstone_be.util.common;

import lombok.Data;

@Data
public class DeleteResponse {

    private String message;

    public DeleteResponse(String message) {
        this.message = message;
    }
}
