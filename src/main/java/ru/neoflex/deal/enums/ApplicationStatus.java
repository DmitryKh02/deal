package ru.neoflex.deal.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ApplicationStatus {
    PREAPPROVAL("Preapproval"),
    APPROVED("Approved"),
    CC_DENIED("CCDenied"),
    CC_APPROVED("CCApproved"),
    PREPARE_DOCUMENT("PrepareDocument"),
    DOCUMENT_CREATED("DocumentCreated"),
    CLIENT_DENIED("ClientDenied"),
    DOCUMENT_SIGNED("DocumentSigned"),
    CREDIT_ISSUED("CreditIssued");

    private final String status;
}
