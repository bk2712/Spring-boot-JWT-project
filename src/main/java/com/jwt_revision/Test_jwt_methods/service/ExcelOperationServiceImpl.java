package com.jwt_revision.Test_jwt_methods.service;

import com.jwt_revision.Test_jwt_methods.helper.ExcelReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ExcelOperationServiceImpl implements ExcelOperation{

    @Autowired
    ExcelReader excelReader;

    @Override
    public Map<String, List<Map<String, String>>> getExcelData(MultipartFile file) throws IOException {
        if(!excelReader.isFileExcel(file)) return null;
        return excelReader.returnExcelData(file);
    }
}
