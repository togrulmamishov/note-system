package com.proxyseller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @NotNull
        @Size(min = 5, max = 30)
        String username,

        @NotNull
        @Size(min = 6, max = 50)
        String password,

        @NotNull
        @Size(min = 1)
        String firstName,

        @NotNull
        @Size(min = 1)
        String lastName) {
}
