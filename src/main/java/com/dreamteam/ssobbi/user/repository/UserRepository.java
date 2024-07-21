package com.dreamteam.ssobbi.user.repository;

import com.dreamteam.ssobbi.user.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByKakaoId(Long kakaoId);

    @NotNull
    Optional<User> findById(@NotNull Long id);
}
