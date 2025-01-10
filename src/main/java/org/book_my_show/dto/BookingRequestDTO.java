package org.book_my_show.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDTO {
    @NotNull(message = "Show ID is required")
    private Long showId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotEmpty(message = "At least one seat must be selected")
    private Set<BookingSeatRequestDTO> seats;
}