package com.wata.payslip.service.Interface;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

	ResponseEntity<?> uploadImages(Integer id, MultipartFile file) throws IOException;

	ResponseEntity<ByteArrayResource> getImage(String name) throws IOException;

	ResponseEntity<?> deleteAllFile(Integer id);

	ResponseEntity<?> deleteOneFile(String file);

	ResponseEntity<?> getEmployeeAllFile(Integer id);

}
