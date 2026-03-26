package org.yash.quize_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yash.quize_app.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> finByEmail (String email);
}
