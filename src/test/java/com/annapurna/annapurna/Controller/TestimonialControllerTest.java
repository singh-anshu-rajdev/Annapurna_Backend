package com.annapurna.annapurna.Controller;

import com.annapurna.annapurna.DTO.*;
import com.annapurna.annapurna.Service.TestimonialService;
import com.annapurna.annapurna.Utils.GeneralFunctions;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TestimonialController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
public class TestimonialControllerTest {

    @Autowired
    private TestimonialController testimonialController;

    @MockBean
    private TestimonialService testimonialService;

    @MockBean
    GeneralFunctions generalFunctions;

    /**
     * Method Under Test: {@link TestimonialController#getAllTestimony(TestimoniesRequestDTO)}
     *
     * @throws Exception
     */
    @Test
    void testGetAllTestimony() throws Exception {

        // Expected Data
        TestimoniesResponseDTO testimoniesResponseDTO = TestimoniesResponseDTO.builder()
                .totalrecords(1)
                .pageNumber(0)
                .data(List.of(TestimonyResponseDTO.builder()
                        .testimonyId(1L)
                        .userName("Anshu")
                        .image("abc-def-ghij-klmn")
                        .rating(4D)
                        .message("Good Food, Good Taste")
                        .build()))
                .build();

        // Mocking Bean
        when(testimonialService.getAllTestimony(Mockito.any(TestimoniesRequestDTO.class))).thenReturn(testimoniesResponseDTO);

        // Request Builder
        TestimoniesRequestDTO requestDTO = TestimoniesRequestDTO.builder().pageNumber(0).records(10).build();
        String content = (new ObjectMapper()).writeValueAsString(requestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/unsecure/getAllTestimony")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

        // Test Case
        MockMvcBuilders.standaloneSetup(testimonialController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(new Gson().toJson(testimoniesResponseDTO)));
    }

    /**
     * Method Under Test: {@link TestimonialController#saveOrEditTestimony(TestimonyRequestDTO, HttpServletRequest)}
     * 
     * @throws Exception
     */
    @Test
    void testSaveOrEditTestimony() throws Exception{

        // Expected Response
        TestimonyResponseDTO testimonyResponseDTO = TestimonyResponseDTO.builder()
                .testimonyId(1L)
                .userName("Anshu")
                .image("abc-def-ghij-klmn")
                .rating(4D)
                .message("Good Food, Good Taste")
                .build();

        UserCacheDTO userCacheDTO = UserCacheDTO.builder()
                .userId("1")
                .userName("Anshu")
                .name("Anshu Singh")
                .roleId(1L)
                .clientId(1).
                build();

        // Mocking the data
        when(generalFunctions.getUserCache(Mockito.any(HttpServletRequest.class))).thenReturn(userCacheDTO);
        when(testimonialService.saveOrEditTestimony(Mockito.any(TestimonyRequestDTO.class), Mockito.any(UserCacheDTO.class))).thenReturn(testimonyResponseDTO);

        // Request for saving the testimonials
        TestimonyRequestDTO requestSaveDTO = TestimonyRequestDTO.builder()
                .userId(1L)
                .rating(4D)
                .message("Good Food, Good Taste")
                .build();
        String contentSave = (new ObjectMapper()).writeValueAsString(requestSaveDTO);

        MockHttpServletRequestBuilder requestSaveBuilders = MockMvcRequestBuilders
                .post("/api/saveOrEditTestimony")
                .content(contentSave)
                .contentType(MediaType.APPLICATION_JSON);

        // Testing the save testimonial
        MockMvcBuilders
                .standaloneSetup(testimonialController)
                .build()
                .perform(requestSaveBuilders)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(new Gson().toJson(testimonyResponseDTO)));

        // Request for editing the testimonials
        TestimonyRequestDTO requestEditDTO = TestimonyRequestDTO.builder()
                .userId(1L)
                .testimonyId(1L)
                .rating(4D)
                .message("Good Food, Good Taste")
                .build();
        String contentEdit = (new ObjectMapper()).writeValueAsString(requestEditDTO);

        MockHttpServletRequestBuilder requestEditBuilders = MockMvcRequestBuilders
                .post("/api/saveOrEditTestimony")
                .content(contentEdit)
                .contentType(MediaType.APPLICATION_JSON);

        // Testing the edit testimonial
        MockMvcBuilders
                .standaloneSetup(testimonialController)
                .build()
                .perform(requestEditBuilders)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(new Gson().toJson(testimonyResponseDTO)));
    }
}
