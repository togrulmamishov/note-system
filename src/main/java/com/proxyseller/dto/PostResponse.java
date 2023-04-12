package com.proxyseller.dto;

import java.time.LocalDateTime;

public record PostResponse(String id,
                           String text,
                           int numberOfLikes,
                           LocalDateTime dateAdded,
                           String author) {
}
