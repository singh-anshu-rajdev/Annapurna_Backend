package com.annapurna.annapurna.ServiceImpl;

import com.annapurna.annapurna.Exception.CustomValidationException;
import com.annapurna.annapurna.Exception.ErrorCode;
import com.annapurna.annapurna.Model.File;
import com.annapurna.annapurna.Repository.FileRepository;
import com.annapurna.annapurna.Service.FileService;
import com.annapurna.annapurna.Utils.AP_Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class FileServiceImpl implements FileService {

    /**
     *  Logger instance to log important events and errors in the service.
     */
    public final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    /**
     * The FileRepository
     */
    @Autowired
    private FileRepository fileRepository;

    /**
     *
     * @param file
     * @return
     * @throws IOException
     */
    @Override
    public Long uploadFile(MultipartFile file) throws IOException {
        try{
            File newFile = new File();
            newFile.setFileName(file.getOriginalFilename());
            newFile.setFileType(file.getContentType());
            newFile.setFileData(file.getBytes());
            newFile.setType(AP_Constants.DEFAULT_FILE_TYPE);
            newFile.setCreatedTs(LocalDateTime.now());
            newFile.setCreatedBy(AP_Constants.DEFAULT_USER);
            newFile.setUpdatedBy(AP_Constants.DEFAULT_USER);
            newFile.setUpdatedTs(LocalDateTime.now());
            newFile.setDeletedFlag(false);
            newFile = fileRepository.save(newFile);
            return newFile.getId();
        }catch (Exception e){
            logger.error("Error in uploading file - {}",e.getMessage());
            throw new CustomValidationException(ErrorCode.ERR_AP_2002);
        }

    }

    /**
     *
     * @param fileId
     * @return
     */
    public ResponseEntity<ByteArrayResource> getFileById(Long fileId){
        try{
            File file = fileRepository.findByIdAndDeletedFlagFalse(fileId);
            if(null==file){
                throw new CustomValidationException(ErrorCode.ERR_AP_2004);
            }else{
                ByteArrayResource resource = new ByteArrayResource(file.getFileData());
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"");
                headers.setContentType(MediaType.parseMediaType(file.getFileType()));
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(file.getFileData().length)
                        .body(resource);
            }
        }catch (Exception e){
            logger.error("Error in downloading the file - {}",e.getMessage());
            if(e.getMessage().equals(ErrorCode.ERR_AP_2004.getMessage())){
                throw new CustomValidationException(ErrorCode.ERR_AP_2004);
            }
            throw new CustomValidationException(ErrorCode.ERR_AP_2003);
        }
    }
}
