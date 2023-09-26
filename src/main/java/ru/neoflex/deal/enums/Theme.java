package ru.neoflex.deal.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Theme {
    FINISH_REGISTRATION("Finish_registration"),
    CREATE_DOCUMENTS("Create_documents"),
    SEND_DOCUMENTS("Send_documents"),
    SEND_SES("Send_ses"),
    CREDIT_ISSUED("Credit_issued"),
    APPLICATION_DENIED("Application_denied");

    private final String themeName;

}
