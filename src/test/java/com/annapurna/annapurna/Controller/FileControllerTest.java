package com.annapurna.annapurna.Controller;

import static org.mockito.Mockito.when;

import com.annapurna.annapurna.Service.FileService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {FileController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class FileControllerTest {
    @Autowired
    private FileController fileController;

    @MockBean
    private FileService fileService;

    /**
     * Method under test: {@link FileController#uploadImage(MultipartFile)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUploadImage() throws IOException {

        // Arrange
        FileController fileController = new FileController();

        // Act
        fileController.uploadImage(new MockMultipartFile("file", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
    }

    /**
     * Method under test: {@link FileController#getFileById(String)}
     */
    @Test
    void testGetFileById() throws Exception {
        // Arrange
        when(fileService.getFileById(Mockito.<String>any())).thenReturn(new ResponseEntity<>(HttpStatusCode.valueOf(200)));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/unsecure/getFileById");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("fileId", String.valueOf(1L));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(fileController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
