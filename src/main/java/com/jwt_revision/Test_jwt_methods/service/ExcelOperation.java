package com.jwt_revision.Test_jwt_methods.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ExcelOperation {
    Map<String, List<Map<String, String>>> getExcelData(MultipartFile file) throws IOException;
}
