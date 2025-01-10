package org.book_my_show.domain.status;

public enum OnboardingStatus {
    INITIATED("Initiated the process"),
    PENDING("Pending Review"),
    DOCUMENT_VERIFICATION("Document Verification"),
    PHYSICAL_VERIFICATION("Physical Verification"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    AGREEMENT_PENDING("Agreement Pending"),
    ACTIVE("Active");

    private final String description;

    OnboardingStatus(String description) {
        this.description = description;
    }
}
