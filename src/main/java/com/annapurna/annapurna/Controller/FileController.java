package com.annapurna.annapurna.Controller;

import com.annapurna.annapurna.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class FileController {

    /**
     * The File Service
     */
    @Autowired
    private FileService fileService;

    /**
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/unsecure/uploadImage")
    public ResponseEntity<String> uploadImage( @RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(fileService.uploadFile(file), HttpStatus.OK);
    }

    /**
     *
     * @param fileId
     * @return
     * @throws IOException
     */
    @GetMapping("/unsecure/getFileById")
    public ResponseEntity<ByteArrayResource> getFileById
            (@RequestParam(value = "fileId", required = true) String fileId) throws IOException {
        return fileService.getFileById(fileId);
    }
}
