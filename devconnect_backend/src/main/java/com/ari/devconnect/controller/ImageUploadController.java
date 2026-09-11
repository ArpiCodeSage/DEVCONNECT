package com.ari.devconnect.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/uploads")
public class ImageUploadController {

    private final Path uploadDirectory = Paths.get("uploads");

    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(
            @RequestParam("image") MultipartFile image
    ) throws IOException {

        Files.createDirectories(uploadDirectory);

        String originalFileName = image.getOriginalFilename();
        String extension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(
                    originalFileName.lastIndexOf(".")
            );
        }

        String fileName = UUID.randomUUID() + extension;

        Path filePath = uploadDirectory.resolve(fileName);

        Files.copy(image.getInputStream(), filePath);

        return ResponseEntity.ok(
                "/api/uploads/image/" + fileName
        );
    }

    @GetMapping("/image/{fileName}")
    public ResponseEntity<Resource> getImage(
            @PathVariable String fileName
    ) throws MalformedURLException {

        Path filePath = uploadDirectory.resolve(fileName);

        Resource resource = new UrlResource(
                filePath.toUri()
        );

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(
                        "Content-Type",
                        "image/" + getExtension(fileName)
                )
                .body(resource);
    }

    private String getExtension(String fileName) {

        int dotIndex = fileName.lastIndexOf(".");

        if (dotIndex == -1) {
            return "jpeg";
        }

        String extension =
                fileName.substring(dotIndex + 1).toLowerCase();

        if (extension.equals("jpg")) {
            return "jpeg";
        }

        return extension;
    }
}