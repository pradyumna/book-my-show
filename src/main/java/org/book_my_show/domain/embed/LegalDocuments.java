package org.book_my_show.domain.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class LegalDocuments {
    @Column(name = "business_license_url")
    private String businessLicenseUrl;

    @Column(name = "tax_document_url")
    private String taxDocumentUrl;

    @Column(name = "identity_proof_url")
    private String identityProofUrl;

    @Column(name = "address_proof_url")
    private String addressProofUrl;

    @Column(name = "bank_details_url")
    private String bankDetailsUrl;
}
