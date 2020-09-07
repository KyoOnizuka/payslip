package com.wata.payslip.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wata.payslip.service.Interface.IFileService;

@RestController
@RequestMapping(value = "/api/file")
public class FileController {

	@Autowired
	public IFileService iFileService;

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> getImage(@PathVariable("name") String name) throws IOException {
		return iFileService.getImage(name);

	}

	@RequestMapping(value = "/{idEmployee}", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	@ResponseBody
	public ResponseEntity<?> saveavatar(@PathVariable("idEmployee") Integer id,
			@RequestParam("uploadfile") MultipartFile file) throws IOException {
		return iFileService.uploadImages(id, file);
	}

	@GetMapping(value = "/all/{idEmployee}")
	public ResponseEntity<?> getAllFile(@PathVariable("idEmployee") Integer id) {
		return iFileService.getEmployeeAllFile(id);
	}

	@DeleteMapping(value = "/deleteall/{idEmployee}")
	@ResponseBody
	public ResponseEntity<?> deleteallfile(@PathVariable("idEmployee") Integer id) {
		return iFileService.deleteAllFile(id);
	}

	@DeleteMapping(value = "/{name}")
	@ResponseBody
	public ResponseEntity<?> deleteonefile(@PathVariable("name") String file) {
		return iFileService.deleteOneFile(file);
	}
}
