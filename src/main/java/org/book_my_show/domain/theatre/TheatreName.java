package org.book_my_show.domain.theatre;

import jakarta.persistence.Embeddable;
import lombok.Value;

@Embeddable
@Value
public class TheatreName {
    String theatreName;

    public TheatreName(){this.theatreName = null;}

    public TheatreName(String theatreName) {
        if (theatreName == null || theatreName.trim().isEmpty()) {
            throw new IllegalArgumentException("Theatre name cannot be empty");
        }
        if (theatreName.length() > 100) {  // assuming max length
            throw new IllegalArgumentException("Theatre name too long");
        }
        this.theatreName = theatreName.trim();
    }
}
