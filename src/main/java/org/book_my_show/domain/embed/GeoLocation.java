package org.book_my_show.domain.embed;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class GeoLocation {
    private Double latitude;
    private Double longitude;
}
