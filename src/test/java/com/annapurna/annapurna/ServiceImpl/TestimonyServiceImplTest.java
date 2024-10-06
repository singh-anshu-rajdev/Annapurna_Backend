package com.annapurna.annapurna.ServiceImpl;

import com.annapurna.annapurna.DTO.*;
import com.annapurna.annapurna.Exception.CustomValidationException;
import com.annapurna.annapurna.Model.Testimonial;
import com.annapurna.annapurna.Model.User;
import com.annapurna.annapurna.Repository.TestimonialRepository;
import com.annapurna.annapurna.Repository.UserRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@ContextConfiguration(classes = {TestimonyServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
public class TestimonyServiceImplTest {

    @Autowired
    TestimonyServiceImpl testimonyService;

    @MockBean
    TestimonialRepository testimonialRepository;

    @MockBean
    UserRepository userRepository;

    /**
     * Method Under Test {@link TestimonyServiceImpl#getAllTestimony(TestimoniesRequestDTO)}
     *
     * @throws Exception
     */
    @Test
    void TestGetAllTestimony() throws Exception{

        // Expected Results
        List<TestimonyResponseDTO> testimonyResponseDTO = List.of(TestimonyResponseDTO.builder()
                .testimonyId(1L)
                .userName("Anshu")
                .image("abc-def-ghij-klmn")
                .rating(4D)
                .message("Good Food, Good Taste")
                .build());
        TestimoniesResponseDTO testimoniesResponseDTO = TestimoniesResponseDTO.builder()
                .totalrecords(1)
                .pageNumber(0)
                .data(testimonyResponseDTO)
                .build();

        List<Testimonial> testimonialList = List.of(Testimonial.builder()
                .id(1L)
                .userId(1L)
                .message("Good Food, Good Taste")
                .rating(4D)
                .build());

        // Generating request and Mocking Data
        User user = User.builder().name("Anshu").id(1L).imageUniqueId("abc-def-ghij-klmn").build();
        when(testimonialRepository.getAllTestimonies(Mockito.any(Pageable.class))).thenReturn(testimonialList);
        when(testimonialRepository.countAllTestimonies()).thenReturn(1);
        when(userRepository.getUserByIds(Mockito.any(List.class))).thenReturn(List.of(user));
        TestimoniesRequestDTO requestDTO = TestimoniesRequestDTO.builder().pageNumber(0).records(10).build();

        // Testing testimonial data
        TestimoniesResponseDTO responseDTO = testimonyService.getAllTestimony(requestDTO);
        assertNotNull("Testimony is not null",responseDTO);
        assertEquals("Testimonies fetched Successfully",new Gson().toJson(testimoniesResponseDTO),new Gson().toJson(responseDTO));
    }

    /**
     * Method Under Test {@link TestimonyServiceImpl#saveOrEditTestimony(TestimonyRequestDTO, UserCacheDTO)}
     *
     * @throws Exception
     */
    @Test
    void TestSaveOrEditTestimony(){

        TestimonyResponseDTO testimonySaveResponseDTO = TestimonyResponseDTO.builder()
                .testimonyId(1L)
                .userName("Anshu")
                .image("abc-def-ghij-klmn")
                .rating(4D)
                .message("Good Food, Good Taste")
                .build();

        TestimonyRequestDTO testimonySaveRequestDTO = TestimonyRequestDTO.builder()
                .userId(1L)
                .message("Good Food, Good Taster")
                .rating(4D).build();
        User user = User.builder().name("Anshu").id(1L).imageUniqueId("abc-def-ghij-klmn").build();

        Testimonial testimonial = Testimonial.builder().id(1L).rating(4D).userId(1L).message("Good Food, Good Taste").build();
        Testimonial testimonialEdit = Testimonial.builder().id(1L).rating(4D).userId(1L).message("Good Food at best Prices").build();

        UserCacheDTO userCacheDTO = UserCacheDTO.builder()
                .userId("1")
                .roleId(1L)
                .clientId(1)
                .userName("Anshu")
                .name("Anshu").build();

        UserCacheDTO failureUserCacheDTO = UserCacheDTO.builder()
                .userId("2")
                .roleId(1L)
                .clientId(1)
                .userName("Anshu")
                .name("Anshu").build();

        // Custom Exception
        assertThrows(CustomValidationException.class,()-> {
            testimonyService.saveOrEditTestimony(testimonySaveRequestDTO, failureUserCacheDTO);
        });

        when(userRepository.getUserById(Mockito.any(Long.class))).thenReturn(user);
        when(testimonialRepository.save(Mockito.any(Testimonial.class))).thenReturn(testimonial);
        when(testimonialRepository.getTestimonyByIdAndUserId(Mockito.any(Long.class), Mockito.any(Long.class))).thenReturn(null);
        TestimonyResponseDTO responseSaveDTO = testimonyService.saveOrEditTestimony(testimonySaveRequestDTO,userCacheDTO);

        // Creating new Testimonial Data
        assertNotNull("Data is not Null",responseSaveDTO);
        assertEquals("Data saved Successfully",new Gson().toJson(testimonySaveResponseDTO), new Gson().toJson(responseSaveDTO));


        TestimonyRequestDTO testimonyEditRequestDTO = TestimonyRequestDTO.builder()
                .testimonyId(1L)
                .userId(1L)
                .message("Good Food at best Prices")
                .rating(4D).build();

        TestimonyResponseDTO testimonyEditResponseDTO = TestimonyResponseDTO.builder()
                .testimonyId(1L)
                .userName("Anshu")
                .image("abc-def-ghij-klmn")
                .rating(4D)
                .message("Good Food at best Prices")
                .build();

        when(testimonialRepository.getTestimonyByIdAndUserId(Mockito.any(Long.class), Mockito.any(Long.class))).thenReturn(testimonial);
        when(testimonialRepository.save(Mockito.any(Testimonial.class))).thenReturn(testimonialEdit);
        TestimonyResponseDTO responseEditDTO = testimonyService.saveOrEditTestimony(testimonyEditRequestDTO,userCacheDTO);

        // Editing Testimonial Data
        assertNotNull("Data is not Null",responseEditDTO);
        assertEquals("Data saved Successfully",new Gson().toJson(testimonyEditResponseDTO), new Gson().toJson(responseEditDTO));

        // User Not found Custom Exception
        when(userRepository.getUserById(Mockito.any(Long.class))).thenReturn(null);
        assertThrows(CustomValidationException.class,()-> {
            testimonyService.saveOrEditTestimony(testimonyEditRequestDTO, userCacheDTO);
        });

    }
}
