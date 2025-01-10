package org.book_my_show.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingSeatRequestDTO {
    @NotNull(message = "Seat ID is required")
    private String showSeatId;
    private String discountCode;
}
