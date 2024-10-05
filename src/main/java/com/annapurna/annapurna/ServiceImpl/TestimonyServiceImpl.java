package com.annapurna.annapurna.ServiceImpl;

import com.annapurna.annapurna.DTO.*;
import com.annapurna.annapurna.Exception.CustomValidationException;
import com.annapurna.annapurna.Exception.ErrorCode;
import com.annapurna.annapurna.Model.Testimonial;
import com.annapurna.annapurna.Model.User;
import com.annapurna.annapurna.Repository.TestimonialRepository;
import com.annapurna.annapurna.Repository.UserRepository;
import com.annapurna.annapurna.Service.TestimonialService;
import com.annapurna.annapurna.Utils.AP_Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TestimonyServiceImpl implements TestimonialService {

    /**
     *  Logger instance to log important events and errors in the service.
     */
    private static final Logger logger = LoggerFactory.getLogger(TestimonialService.class);

    /**
     * The ERROR_FETCHING_DATA_MESSAGE of type String
     */
    private static final String ERROR_FETCHING_DATA_TESTIMONIALS = "Error in fetching the testimonials data...{}";

    /**
     * The ERROR_IN_SAVING_TESTIMONIAL of type String
     */
    private static final String ERROR_IN_SAVING_TESTIMONIAL = "Error in saving the testimonials data...{}";

    /**
     * The testimonialRepository of type TestimonialRepository
     */
    @Autowired
    TestimonialRepository testimonialRepository;

    /**
     * The userRepository of type UserRepository
     */
    @Autowired
    UserRepository userRepository;

    /**
     *
     * @param testimoniesRequestDTO
     * @return
     */
    @Override
    public TestimoniesResponseDTO getAllTestimony(TestimoniesRequestDTO testimoniesRequestDTO) {
        try{

            TestimoniesResponseDTO testimoniesResponseDTO = new TestimoniesResponseDTO();
            // set pageable
            Pageable pageable = PageRequest.of(testimoniesRequestDTO.getPageNumber(),testimoniesRequestDTO.getRecords());

            // get all testimony based on pageable
            List<Testimonial> testimonialList = testimonialRepository.getAllTestimonies(pageable);
            Integer count = testimonialRepository.countAllTestimonies();

            // Map the user with its image based on tm userId
            List<User> users = userRepository.getUserByIds(testimonialList.parallelStream().map(Testimonial::getUserId).toList());
            Map<Long,String> imageMap = users.stream().filter(u->null!=u.getImageUniqueId()).collect(Collectors.toMap(
                    User::getId,
                    User::getImageUniqueId,
                    (existing, replacement) -> existing,
                    HashMap::new
            ));

            // Get all the testimony details
            List<TestimonyResponseDTO> data = testimonialList.parallelStream().map(tm ->{
                return TestimonyResponseDTO.builder()
                        .testimonyId(tm.getId())
                        .message(tm.getMessage())
                        .image(imageMap.containsKey(tm.getId())?imageMap.get(tm.getUserId()):null)
                        .rating(tm.getRating()).build();
            }).toList();

            // Return the data
            testimoniesResponseDTO.setData(data);
            testimoniesResponseDTO.setTotalrecords(count);
            testimoniesResponseDTO.setPageNumber(testimoniesRequestDTO.getPageNumber());
            return testimoniesResponseDTO;
        } catch (Exception ex) {
            logger.error(ERROR_FETCHING_DATA_TESTIMONIALS,ex.getMessage());
            throw new CustomValidationException(ErrorCode.ERR_AP_2017);
        }

    }

    /**
     *
     * @param testimonyRequestDTO
     * @param userCacheDTO
     * @return
     */
    @Override
    public TestimonyResponseDTO saveOrEditTestimony(TestimonyRequestDTO testimonyRequestDTO, UserCacheDTO userCacheDTO) {
        try{
            // authentication token and user check
            if(userCacheDTO.getUserId().equals(testimonyRequestDTO.getUserId().toString())) {
                TestimonyResponseDTO testimonyResponseDTO;

                // Fetching the existing user
                User user = userRepository.getUserById(testimonyRequestDTO.getUserId());
                Testimonial testimonial = testimonialRepository.getTestimonyByIdAndUserId(testimonyRequestDTO.getTestimonyId(),testimonyRequestDTO.getUserId());
                if (null == testimonial) {

                    if(null!=testimonyRequestDTO.getTestimonyId()){
                        throw new CustomValidationException(ErrorCode.ERR_AP_2019);
                    }

                    // create new testimonial
                    Testimonial newTestimonial = Testimonial.builder()
                            .userId(testimonyRequestDTO.getUserId())
                            .message(testimonyRequestDTO.getMessage())
                            .rating(testimonyRequestDTO.getRating())
                            .createdTs(LocalDateTime.now())
                            .createdBy(userCacheDTO.getUserName())
                            .updatedTs(LocalDateTime.now())
                            .updatedBy(userCacheDTO.getUserName())
                            .deletedFlag(AP_Constants.FALSE)
                            .build();
                    newTestimonial = testimonialRepository.save(newTestimonial);

                    // giving response
                    testimonyResponseDTO = TestimonyResponseDTO.builder()
                            .testimonyId(newTestimonial.getId())
                            .image(user.getImageUniqueId())
                            .rating(newTestimonial.getRating()).
                            message(newTestimonial.getMessage()).build();
                } else {

                    // editing existing testimonial
                    testimonial.setMessage(testimonyRequestDTO.getMessage());
                    testimonial.setRating(testimonyRequestDTO.getRating());
                    testimonial.setUpdatedTs(LocalDateTime.now());
                    testimonial.setUpdatedBy(userCacheDTO.getUserName());
                    testimonialRepository.save(testimonial);

                    // response
                    testimonyResponseDTO = TestimonyResponseDTO.builder()
                            .testimonyId(testimonial.getId())
                            .image(user.getImageUniqueId())
                            .rating(testimonial.getRating()).
                            message(testimonial.getMessage()).build();
                }
                return testimonyResponseDTO;
            }else{
                throw new CustomValidationException(ErrorCode.ERR_AP_2006);
            }
        }catch (CustomValidationException ex) {
            logger.error(ERROR_IN_SAVING_TESTIMONIAL, ex.getMessage());
            throw ex;
        }catch (Exception ex) {
            logger.error(ERROR_IN_SAVING_TESTIMONIAL, ex.getMessage());
            throw new CustomValidationException(ErrorCode.ERR_AP_2018);
        }

    }
}
