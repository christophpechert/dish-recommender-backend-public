package com.project.dishrecommender.imageData;

import com.project.dishrecommender.imageData.entity.ImageData;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("image-data-management")
public class ImageDataController {
    private final ImageDataService imageDataService;

    @PreAuthorize("hasAuthority('ROLE_CHEF')")
    @DeleteMapping("image/{imageDataId}")
    ResponseEntity<Void> delete(@PathVariable Long imageDataId) {
        imageDataService.delete(imageDataId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('ROLE_CHEF')")
    @PostMapping("/dish/{dishId}")
    ResponseEntity<ImageData> uploadFile(@PathVariable Long dishId, @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(imageDataService.add(dishId, file), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    @GetMapping("/dish/{dishId}")
    ResponseEntity<List<ImageData>> retrieveAllFromDish(@PathVariable Long dishId) {
        return new ResponseEntity<>(imageDataService.retrieveAllFromDish(dishId), HttpStatus.OK);
    }
}
