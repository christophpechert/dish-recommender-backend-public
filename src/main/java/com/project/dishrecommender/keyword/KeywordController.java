package com.project.dishrecommender.keyword;

import com.project.dishrecommender.keyword.entity.Keyword;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/keyword-management")
@AllArgsConstructor
public class KeywordController {
    private final KeywordService keywordService;

    @PreAuthorize("hasAuthority('ROLE_CHEF')")
    @PostMapping("/keyword")
    public ResponseEntity<Keyword> add(@RequestBody Keyword keyword) {
        return new ResponseEntity<>(keywordService.addOne(keyword), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    @GetMapping("/keyword")
    public ResponseEntity<List<Keyword>> retrieveAllFromUserGroup() {
        return new ResponseEntity<>(keywordService.retrieveAllFromUserGroup(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_WAITER')")
    @GetMapping("/keyword/{keywordId}")
    public ResponseEntity<Keyword> retrieveById(@PathVariable Long keywordId) {
        return new ResponseEntity<>(keywordService.retrieveById(keywordId), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_CHEF')")
    @PutMapping("/keyword/{keywordId}")
    public ResponseEntity<Keyword> update(@PathVariable Long keywordId, @RequestBody Keyword keyword) {
        return new ResponseEntity<>(keywordService.update(keywordId, keyword), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @DeleteMapping("/keyword/{keywordId}")
    public ResponseEntity<Void> delete(@PathVariable Long keywordId) {
        keywordService.deleteById(keywordId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
