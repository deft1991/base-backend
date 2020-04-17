package com.deft.developer.repository;

import com.deft.developer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Created by sgolitsyn on 4/17/20
 */
public interface UserRepository extends JpaRepository<User, String> {
}
