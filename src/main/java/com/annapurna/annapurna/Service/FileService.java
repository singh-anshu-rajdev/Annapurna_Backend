package com.annapurna.annapurna.Service;

import com.annapurna.annapurna.Model.File;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface FileService {

    /**
     *
     * @param file
     * @return
     * @throws IOException
     */
    Long uploadFile(MultipartFile file) throws IOException;

    /**
     *
     * @param fileId
     * @return
     */
    ResponseEntity<ByteArrayResource> getFileById(Long fileId);
}
