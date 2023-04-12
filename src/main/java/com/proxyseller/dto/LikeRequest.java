package com.proxyseller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record LikeRequest(
        @NotNull(message = "action cannot be null")
        @Pattern(regexp = "^(?i)(add|remove)$", message = "Only acceptable words: 'add', 'remove'")
        String action
) {
}
