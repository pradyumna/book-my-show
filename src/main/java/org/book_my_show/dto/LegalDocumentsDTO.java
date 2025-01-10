package org.book_my_show.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LegalDocumentsDTO {
    private String businessLicenseUrl;
    private String taxDocumentUrl;
    private String identityProofUrl;
    private String addressProofUrl;
    private String bankDetailsUrl;
}
