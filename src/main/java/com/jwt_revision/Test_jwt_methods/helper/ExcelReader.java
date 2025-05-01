package com.jwt_revision.Test_jwt_methods.helper;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ExcelReader {


    public boolean isFileExcel(MultipartFile excelSheet){

        String contentType= excelSheet.getContentType();
        if(contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) return true;
        return false;
    }

    public Map<String, List<Map<String,String>>> returnExcelData(MultipartFile file) throws IOException {

        Map<String, List<Map<String,String>>> excelData= new HashMap<>();

        try(InputStream inputStream= file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);

            for(Sheet sheet : workbook){
                String sheetName= sheet.getSheetName();
                List<Map<String, String>> sheetData = new ArrayList<>();

                int startRow = findDataStartRow(sheet);  // this will return the row num where data is starting
                Row headerRow = sheet.getRow(startRow); // this will return the array of header at row startRow
                if (headerRow == null) continue;

                for(int i=startRow+1;i<=sheet.getLastRowNum();i++){

                    Row row= sheet.getRow(i);   // this will return row data at ith position
                    if(row == null) continue;

                    Map<String, String> rowData = new HashMap<>();
                    // now iterate over the columns of the ith row
                    for(int j=0;j<headerRow.getLastCellNum();j++){
                        // A    B    C
                        // 1    2    3
                        // 4    5    6

//                    headerRow => [A, B, C]
//                    headerRow.getCell(0) => A
//                    headerRow.getCell(1) => B
//                    row => [1, 2, 3]
//                    row.getCell(0); => 1
//                    row.getCell(2); =? 3
                        Cell headerCell= headerRow.getCell(j); // this will return the data of jth column headerRow
                        Cell dataCell = row.getCell(j);
                        if(headerCell != null && dataCell != null) rowData.put(headerCell.getStringCellValue(), dataCell.toString());

                    }
                    sheetData.add(rowData);

                }

                excelData.put(sheetName, sheetData);
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return excelData;




    }

    private static int findDataStartRow(Sheet sheet) {

        for(int i=0;i<sheet.getLastRowNum();i++){
            Row row= sheet.getRow(i);
            if(row == null) return 0;
            for (int j=0;j<row.getLastCellNum();j++){
                Cell cell= row.getCell(j);
                if(cell != null) return i;
            }
        }
        return 0;
    }

}
