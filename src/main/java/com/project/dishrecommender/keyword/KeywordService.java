package com.project.dishrecommender.keyword;

import com.project.dishrecommender.exception.KeywordAlreadyExistException;
import com.project.dishrecommender.exception.KeywordNotFoundException;
import com.project.dishrecommender.keyword.entity.Keyword;
import com.project.dishrecommender.usergroup.UserGroupService;
import com.project.dishrecommender.usergroup.entity.UserGroup;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;
    private final UserGroupService userGroupService;
    
    public Keyword addOne(Keyword keyword) {
        UserGroup userGroup = userGroupService.retrieveFromUser();
        return add(userGroup, keyword);
    }
    
    public List<Keyword> addMany(List<Keyword> keywords) {
        UserGroup userGroup = userGroupService.retrieveFromUser();
        
        return keywords.stream()
                .map(keyword -> {
                    try {
                        return Optional.of(add(userGroup, keyword));
                    } catch (KeywordAlreadyExistException e) {
                        e.printStackTrace();
                        return Optional.<Keyword>empty();
                    }
                })
                .flatMap(Optional::stream)
                .toList();
    }
    
    public Keyword retrieveById(Long keywordId) {
        return keywordRepository.findById(keywordId)
                .orElseThrow(() -> new KeywordNotFoundException(keywordId));
    }

    public List<Keyword> retrieveAllFromUserGroup() {
        UserGroup userGroup = userGroupService.retrieveFromUser();
        return keywordRepository.findByUserGroupIdOrderByName(userGroup.getId());
    }

    public Keyword update(Long keywordId, Keyword keyword) {
        List<Keyword> keywords = retrieveAllFromUserGroup();

        Keyword existingKeyword = keywords.stream()
                .filter(e -> e.getId().equals(keywordId))
                .findFirst()
                .orElseThrow(() -> new KeywordNotFoundException(keywordId));

        if (!hasChanges(existingKeyword, keyword)) {
            return existingKeyword;
        }

        keywords.stream()
                .filter(e -> e.getName().equalsIgnoreCase(keyword.getName()) && !e.getId().equals(keywordId))
                .findAny()
                .ifPresent(e -> {
                    throw new KeywordAlreadyExistException(keyword);
                });

        existingKeyword.setName(keyword.getName());
        existingKeyword.setLastChange(LocalDateTime.now());

        return save(existingKeyword);
    }

    public void deleteById(Long keywordId) {
        retrieveById(keywordId).getDishes()
                .forEach(dish -> dish.getKeywords()
                        .removeIf(keyword -> keyword.getId().equals(keywordId)));

        keywordRepository.deleteById(keywordId);
    }
    
    private Keyword add(UserGroup userGroup, Keyword keyword) throws KeywordAlreadyExistException {
        userGroup.getKeywords()
                .stream()
                .filter(e -> e.getName().equalsIgnoreCase(keyword.getName()))
                .findAny()
                .ifPresent(e -> {
                    throw new KeywordAlreadyExistException(keyword);
                });

        keyword.setCreated(LocalDateTime.now());
        keyword.setUserGroup(userGroup);
        return save(keyword);
    }

    private boolean hasChanges(Keyword origin, Keyword changed) {
        return !origin.getName().equals(changed.getName());
    }

    private Keyword save(Keyword keyword) {
        return keywordRepository.save(keyword);
    }
}
