package com.proxyseller.dto;

import jakarta.validation.constraints.NotNull;

public record PostRequest(@NotNull String text) {
}
