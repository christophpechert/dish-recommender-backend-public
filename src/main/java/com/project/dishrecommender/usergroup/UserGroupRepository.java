package com.project.dishrecommender.usergroup;

import com.project.dishrecommender.usergroup.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    List<UserGroup> findAllByNameIgnoreCaseContainingOrderByName(String name);
}
