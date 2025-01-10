package org.book_my_show.dto;

import lombok.*;
import org.book_my_show.domain.status.TheatreStatus;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TheatreDTO extends BaseDTO{
    private Long theatreId;
    private String name;
    private String licenseNumber;
    private AddressDTO address;
    private Set<ScreenDTO> screens;
    private TheatreStatus status;
}
