package com.jwt_revision.Test_jwt_methods.controller;


import com.jwt_revision.Test_jwt_methods.service.ExcelOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController

@RequestMapping("/excel-operation")
public class ExcelController {

    @Autowired
    ExcelOperation excelOperationService;

    @GetMapping("/dump-excel-data")
    public Map<String, List<Map<String, String>>> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return excelOperationService.getExcelData(file);

    }
}
