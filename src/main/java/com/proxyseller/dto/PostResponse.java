package com.proxyseller.dto;

import java.time.LocalDateTime;

public record PostResponse(String text,
                           int numberOfLikes,
                           LocalDateTime dateAdded) {
}
