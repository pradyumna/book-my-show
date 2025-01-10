package org.book_my_show.dto;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO{
    private String street;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private GeoLocationDTO location;
}
