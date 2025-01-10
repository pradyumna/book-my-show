package org.book_my_show.repo;

import org.book_my_show.domain.discount.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Discount findByCode(String code);
}
