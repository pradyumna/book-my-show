package org.book_my_show.domain.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class BusinessContact {
    private String ownerName;

    @Email
    private String businessEmail;

    private String businessPhone;

    private String alternatePhone;

    @Column(name = "support_email")
    private String supportEmail;

    @Column(name = "support_phone")
    private String supportPhone;
}
