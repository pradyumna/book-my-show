package org.book_my_show.repo;

import org.book_my_show.domain.user.Email;
import org.book_my_show.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    boolean existsByEmail(Email email);
    boolean existsByPhone(String phone);
}
