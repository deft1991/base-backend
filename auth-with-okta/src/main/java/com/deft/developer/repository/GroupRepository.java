package com.deft.developer.repository;

import com.deft.developer.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * Created by sgolitsyn on 4/17/20
 */
public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByName(String name);

    List<Group> findAllByUserId(String id);
}
