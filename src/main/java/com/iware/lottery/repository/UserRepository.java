package com.iware.lottery.repository;

import com.iware.lottery.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by johnma on 2016/11/2.
 */
public interface UserRepository  extends JpaRepository<User, Long>,//
        JpaSpecificationExecutor<User> {
    public User findByName(String username);

    public User findByToken(String token);
}
