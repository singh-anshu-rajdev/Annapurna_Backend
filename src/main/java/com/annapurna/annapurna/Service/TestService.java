package com.annapurna.annapurna.Service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface TestService {

    void downLoadExcelSheet(HttpServletResponse response);
    void uploadExcelSheet(MultipartFile file);
}
