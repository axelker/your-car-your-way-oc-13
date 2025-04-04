package com.openclassrooms.ycyw.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    CLIENT("CLIENT"),
    AGENT("AGENT"),
    SUPPORT("SUPPORT");

    private final String authority;
}
