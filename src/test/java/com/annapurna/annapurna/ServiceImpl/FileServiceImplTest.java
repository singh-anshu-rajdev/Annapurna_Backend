package com.annapurna.annapurna.ServiceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.annapurna.annapurna.Exception.CustomValidationException;
import com.annapurna.annapurna.Exception.ErrorCode;
import com.annapurna.annapurna.Model.File;
import com.annapurna.annapurna.Repository.FileRepository;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FileServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class FileServiceImplTest {
    @MockBean
    private FileRepository fileRepository;

    @Autowired
    private FileServiceImpl fileServiceImpl;

    /**
     * Method under test: {@link FileServiceImpl#getFileById(Long)}
     */
    @Test
    void testGetFileById() throws UnsupportedEncodingException {
        // Arrange
        File file = new File();
        file.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        file.setCreatedTs(LocalDate.of(1970, 1, 1).atStartOfDay());
        file.setDeletedFlag(true);
        file.setFileData("AXAXAXAX".getBytes("UTF-8"));
        file.setFileName("foo.txt");
        file.setFileType("File Type");
        file.setId(1L);
        file.setType("Type");
        file.setUpdatedBy("2020-03-01");
        file.setUpdatedTs(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(fileRepository.findByIdAndDeletedFlagFalse(Mockito.<Long>any())).thenReturn(file);

        // Act and Assert
        assertThrows(CustomValidationException.class, () -> fileServiceImpl.getFileById(1L));
        verify(fileRepository).findByIdAndDeletedFlagFalse(eq(1L));
    }

    /**
     * Method under test: {@link FileServiceImpl#getFileById(Long)}
     */
    @Test
    void testGetFileById2() throws UnsupportedEncodingException {
        // Arrange
        File file = mock(File.class);
        when(file.getFileName()).thenThrow(new CustomValidationException(ErrorCode.ERR_AP_2000));
        when(file.getFileData()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
        doNothing().when(file).setCreatedBy(Mockito.<String>any());
        doNothing().when(file).setCreatedTs(Mockito.<LocalDateTime>any());
        doNothing().when(file).setDeletedFlag(Mockito.<Boolean>any());
        doNothing().when(file).setFileData(Mockito.<byte[]>any());
        doNothing().when(file).setFileName(Mockito.<String>any());
        doNothing().when(file).setFileType(Mockito.<String>any());
        doNothing().when(file).setId(Mockito.<Long>any());
        doNothing().when(file).setType(Mockito.<String>any());
        doNothing().when(file).setUpdatedBy(Mockito.<String>any());
        doNothing().when(file).setUpdatedTs(Mockito.<LocalDateTime>any());
        file.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        file.setCreatedTs(LocalDate.of(1970, 1, 1).atStartOfDay());
        file.setDeletedFlag(true);
        file.setFileData("AXAXAXAX".getBytes("UTF-8"));
        file.setFileName("foo.txt");
        file.setFileType("File Type");
        file.setId(1L);
        file.setType("Type");
        file.setUpdatedBy("2020-03-01");
        file.setUpdatedTs(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(fileRepository.findByIdAndDeletedFlagFalse(Mockito.<Long>any())).thenReturn(file);

        // Act and Assert
        assertThrows(CustomValidationException.class, () -> fileServiceImpl.getFileById(1L));
        verify(file).getFileData();
        verify(file).getFileName();
        verify(file).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(file).setCreatedTs(isA(LocalDateTime.class));
        verify(file).setDeletedFlag(eq(true));
        verify(file).setFileData(isA(byte[].class));
        verify(file).setFileName(eq("foo.txt"));
        verify(file).setFileType(eq("File Type"));
        verify(file).setId(eq(1L));
        verify(file).setType(eq("Type"));
        verify(file).setUpdatedBy(eq("2020-03-01"));
        verify(file).setUpdatedTs(isA(LocalDateTime.class));
        verify(fileRepository).findByIdAndDeletedFlagFalse(eq(1L));
    }

    /**
     * Method under test: {@link FileServiceImpl#getFileById(Long)}
     */
    @Test
    void testGetFileById3() throws UnsupportedEncodingException {
        // Arrange
        File file = mock(File.class);
        when(file.getFileName()).thenThrow(new CustomValidationException(ErrorCode.ERR_AP_2004));
        when(file.getFileData()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
        doNothing().when(file).setCreatedBy(Mockito.<String>any());
        doNothing().when(file).setCreatedTs(Mockito.<LocalDateTime>any());
        doNothing().when(file).setDeletedFlag(Mockito.<Boolean>any());
        doNothing().when(file).setFileData(Mockito.<byte[]>any());
        doNothing().when(file).setFileName(Mockito.<String>any());
        doNothing().when(file).setFileType(Mockito.<String>any());
        doNothing().when(file).setId(Mockito.<Long>any());
        doNothing().when(file).setType(Mockito.<String>any());
        doNothing().when(file).setUpdatedBy(Mockito.<String>any());
        doNothing().when(file).setUpdatedTs(Mockito.<LocalDateTime>any());
        file.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        file.setCreatedTs(LocalDate.of(1970, 1, 1).atStartOfDay());
        file.setDeletedFlag(true);
        file.setFileData("AXAXAXAX".getBytes("UTF-8"));
        file.setFileName("foo.txt");
        file.setFileType("File Type");
        file.setId(1L);
        file.setType("Type");
        file.setUpdatedBy("2020-03-01");
        file.setUpdatedTs(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(fileRepository.findByIdAndDeletedFlagFalse(Mockito.<Long>any())).thenReturn(file);

        // Act and Assert
        assertThrows(CustomValidationException.class, () -> fileServiceImpl.getFileById(1L));
        verify(file).getFileData();
        verify(file).getFileName();
        verify(file).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(file).setCreatedTs(isA(LocalDateTime.class));
        verify(file).setDeletedFlag(eq(true));
        verify(file).setFileData(isA(byte[].class));
        verify(file).setFileName(eq("foo.txt"));
        verify(file).setFileType(eq("File Type"));
        verify(file).setId(eq(1L));
        verify(file).setType(eq("Type"));
        verify(file).setUpdatedBy(eq("2020-03-01"));
        verify(file).setUpdatedTs(isA(LocalDateTime.class));
        verify(fileRepository).findByIdAndDeletedFlagFalse(eq(1L));
    }
}
