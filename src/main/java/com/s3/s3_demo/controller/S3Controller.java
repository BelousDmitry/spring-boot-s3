package com.s3.s3_demo.controller;

import com.s3.s3_demo.service.S3Service;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @GetMapping("/{key}")
    public InputStreamResource getObject(@PathVariable String key) {
        return new InputStreamResource(this.s3Service.getObject(key));
    }

    @PostMapping()
    public String putObject(@RequestParam("file") MultipartFile file) {
        return this.s3Service.putObject(file, file.getOriginalFilename());
    }

    @DeleteMapping("/{key}")
    public void deleteObject(@PathVariable String key){
        this.s3Service.deleteObject(key);
    }

}
