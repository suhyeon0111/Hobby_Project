package com.hoppy.app.login.api.repository.user;

import com.hoppy.app.login.api.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(String userId);
}
