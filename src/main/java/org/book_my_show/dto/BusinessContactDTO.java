package org.book_my_show.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessContactDTO {
    //@NotBlank(message = "Owner name is required")
    private String ownerName;

    @Email(message = "Invalid business email")
    //@NotBlank(message = "Business email is required")
    private String businessEmail;

    //@NotBlank(message = "Business phone is required")
    private String businessPhone;

    private String alternatePhone;
    private String supportEmail;
    private String supportPhone;
}
