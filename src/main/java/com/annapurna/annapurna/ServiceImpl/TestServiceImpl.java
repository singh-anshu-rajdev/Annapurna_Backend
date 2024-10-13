package com.annapurna.annapurna.ServiceImpl;

import com.annapurna.annapurna.DTO.UserRegistrationDTO;
import com.annapurna.annapurna.Exception.CustomValidationException;
import com.annapurna.annapurna.Exception.ErrorCode;
import com.annapurna.annapurna.Service.TestService;
import com.annapurna.annapurna.Utils.AP_Constants;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.AbstractDocument;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService {

    public static  final Logger LOGGER =  LoggerFactory.getLogger(TestServiceImpl.class);
    @Override
    public void downLoadExcelSheet(HttpServletResponse response) {
        try{
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Bulk users upload data template");
            Row headerRow = sheet.createRow(0);
            int cell = 0;
            for(String header : AP_Constants.HEADER_LIST_FOR_BULK_UPLOAD){
                headerRow.createCell(cell++, CellType.STRING).setCellValue(header);
            }
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "attachment; filename=\"Template Data.xlsx\"");
            workbook.write(response.getOutputStream());
        } catch (Exception ex) {
            LOGGER.error("test excel sheet fail");
        }
    }

    @Override
    public void uploadExcelSheet(MultipartFile file) {
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            List<UserRegistrationDTO> userRegistrationDTOList = new ArrayList<>();
            Sheet sheet = workbook.getSheetAt(0);
            Row rowD = sheet.getRow(0);
            Map<String, Integer> headerMap = new HashMap<>();
            for (Cell cell : rowD) {
                headerMap.put(cell.getStringCellValue(), cell.getColumnIndex());
            }
            for (Row row : sheet) {
                if(row.getRowNum()==0){
                    continue;
                }

                userRegistrationDTOList.add(
                        UserRegistrationDTO.builder()
                                .name(getCellValue(row, headerMap, "Full Name")) // Retrieves name
                                .userName(getCellValue(row, headerMap, "User Name")) // Retrieves user name
                                .emailId(getCellValue(row, headerMap, "EmailId")) // Retrieves email ID
                                .phoneNumber(getCellValue(row, headerMap, "Phone Number")) // Retrieves phone number
                                .password(getCellValue(row, headerMap, "Password")) // Retrieves password
                                .build());

            }

            LOGGER.error("List of data " + userRegistrationDTOList.toString());
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getCellValue(Row row, Map<String, Integer> headerMap, String headerName) {
        Integer columnIndex = headerMap.get(headerName);
        if (columnIndex != null) {
            Cell cell = row.getCell(columnIndex);
            if (cell != null) {
                switch (cell.getCellType()) {
                    case STRING:
                        return cell.getStringCellValue().trim();
                    case NUMERIC:
                        // Handle phone number as a string
                        return String.valueOf((long) cell.getNumericCellValue()).trim();
                    case BOOLEAN:
                        return String.valueOf(cell.getBooleanCellValue()).trim();
                    default:
                        return null;
                }
            }
        }
        return null; // Return null if the cell is not found or empty
    }
}
