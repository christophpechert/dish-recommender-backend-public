package com.project.dishrecommender.menu;

import com.project.dishrecommender.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByUserGroupIdOrderByName(Long userGroupId);
}
