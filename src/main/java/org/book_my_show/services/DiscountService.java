package org.book_my_show.services;

import lombok.RequiredArgsConstructor;
import org.book_my_show.domain.discount.Discount;
import org.book_my_show.domain.show.ShowSeat;
import org.book_my_show.dto.DiscountValidationResult;
import org.book_my_show.repo.DiscountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiscountService {
    private final DiscountRepository discountRepository;

    /*public DiscountValidationResult validateDiscount(String discountCode,
                                                     BigDecimal originalPrice,
                                                     ShowSeat showSeat) {
        return discountRepository.findByCode(discountCode)
                .filter(discount -> !discount.isExpired())
                .filter(discount -> discount.isApplicable(showSeat.getSeat().getSeatType()))
                .map(discount -> calculateDiscount(discount, originalPrice))
                .orElseThrow(() -> new InvalidDiscountException("Invalid or expired discount code"));
    }*/

    /*private DiscountValidationResult calculateDiscount(Discount discount, BigDecimal originalPrice) {
        BigDecimal discountAmount;
        if (discount.isPercentageBased()) {
            discountAmount = originalPrice.multiply(discount.getPercentage())
                    .divide(BigDecimal.valueOf(100));
        } else {
            discountAmount = discount.getFixedAmount();
        }

        return new DiscountValidationResult(discount.getCode(), discount.getDescription(), originalPrice,
                discountAmount,originalPrice.subtract(discountAmount));
    }*/
}
