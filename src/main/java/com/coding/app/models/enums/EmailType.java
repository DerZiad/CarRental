package com.coding.app.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmailType {

    CONFIRMATION("classpath:templates/confirmation_letter.html"), RESET_PASSWORD("classpath:templates/recover_letter.html"), INFORMATION("classpath:templates/information_letter.html");

    private final String mailTemplate;
}
