package com.coding.app.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ServerRole {

    ADMIN("ADMIN", "/admin", "/"), CLIENT("CLIENT", "/", "/"), MANAGER("MANAGER", "/shared", "/");

    private final String role;
    private final String privateSpace;
    private final String targetUrl;
}
