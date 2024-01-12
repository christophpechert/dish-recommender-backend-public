package com.project.dishrecommender.usergroup.dto;

import com.project.dishrecommender.keyword.entity.Keyword;
import com.project.dishrecommender.menu.entity.Menu;

import java.util.List;

public record UserGroupWithMenusAndKeywordsDTO(
        Long id,
        String name,
        List<Menu> menus,
        List<Keyword> keywords) {
}
