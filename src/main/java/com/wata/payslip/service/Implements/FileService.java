package com.wata.payslip.service.Implements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wata.payslip.model.entity.EmployeeEntity;
import com.wata.payslip.model.entity.FileEntity;
import com.wata.payslip.model.entity.FileManagerEntity;
import com.wata.payslip.repository.EmployeeRepository;
import com.wata.payslip.repository.FileManagerRepository;
import com.wata.payslip.repository.FileRepository;
import com.wata.payslip.service.Interface.IFileService;

@Service
public class FileService implements IFileService {
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private FileManagerRepository fileManagerRepository;

	@Override
	public ResponseEntity<?> uploadImages(Integer id, MultipartFile file) throws IOException {

		EmployeeEntity employee = employeeRepository.findById(id).get();
		String extension = file.getOriginalFilename().split("\\.")[1];
		String folder = (extension.equals("jpg")) ? "image" : "document";
		// Check file exist or not----------------
		FileManagerEntity fileManager = (fileManagerRepository.findByIdEmployeeAndDescription(employee.getId(),
				folder) != null) ? fileManagerRepository.findByIdEmployeeAndDescription(employee.getId(), folder)
						: new FileManagerEntity();
		FileEntity entity = (fileManagerRepository.findByIdEmployeeAndDescription(employee.getId(), folder) != null)
				? fileManager.getIdFile()
				: new FileEntity();
		// Set unique File Name-----------------------------------------
		String avatar = Long.toString(employee.getJoinDay().getTime()) + Integer.toString(employee.getId()) + "."
				+ file.getOriginalFilename().split("\\.")[1];
		// Get path in local
		Path path = Paths.get(folder);
		// Path name
		String pathname = folder + "/" + avatar;
		// Save Model fileEntity
		entity.setPathFile(pathname);
		entity.setNameFile(file.getOriginalFilename());
		fileRepository.save(entity);
		// Save into file manager
		fileManager.setIdFile(entity);
		fileManager.setIdEmployee(employee);
		fileManager.setDescription(folder);
		fileManagerRepository.save(fileManager);
		// Add contrains
		Set<FileManagerEntity> uploadFile = employee.getFile();
		uploadFile.add(fileManager);
		employee.setFile(uploadFile);
		employeeRepository.save(employee);

		InputStream inputStream = file.getInputStream();
		// ----------------------Lưu file
		Files.copy(inputStream, path.resolve(avatar), StandardCopyOption.REPLACE_EXISTING);
		HashMap<String, Object> response = new HashMap<>();
		response.put("avatar", entity.getPathFile());
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<ByteArrayResource> getImage(String name) throws IOException {
		// Get file from localhost
		String[] extension = name.split("\\.");

		String folder = (extension[1].equals("jpg")) ? "image" : "document";
		Path filename = Paths.get(folder, name);

		HttpHeaders headers = new HttpHeaders();
		byte[] buffer = Files.readAllBytes(filename);
		MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
		String mineType = servletContext.getMimeType(name);

		if (mineType != null) {
			mediaType = MediaType.parseMediaType(mineType);
		}

		ByteArrayResource ByteArrayResource = new ByteArrayResource(buffer);
		headers.add("Content-Disposition", "inline; filename=" + name);
		// return file
		return ResponseEntity.ok().contentLength(buffer.length)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + name).contentType(mediaType)
				.body(ByteArrayResource);

	}

	@Override
	public ResponseEntity<?> deleteAllFile(Integer id) {
		// Get file list
		EmployeeEntity employee = employeeRepository.findById(id).get();
		Set<FileManagerEntity> listfile = employee.getFile();

		// delete file from localhost
		for (FileManagerEntity entity : listfile) {

			// delete contrains
			entity.setIdEmployee(null);
			fileManagerRepository.save(entity);

			FileEntity filedelete = entity.getIdFile();
			String fileName = entity.getIdFile().getPathFile();

			// delete contrains
			fileManagerRepository.delete(entity);
			fileRepository.delete(filedelete);

			File file = new File(fileName);
			try {

				// delete file
				file.delete();
			} catch (Exception e) {
				return new ResponseEntity(null, HttpStatus.METHOD_FAILURE);
			}
		}

		// delete contrains
		employee.setFile(null);
		employeeRepository.save(employee);

		return ResponseEntity.ok(null);

	}

	@Override
	public ResponseEntity<?> deleteOneFile(String name) {
		String extension = name.split("\\.")[1];
		String folder = (extension.equals("jpg")) ? "image" : "document";
		FileEntity filedelete = fileRepository.findByPathFile(folder + "/" + name).get();
		// Get data
		FileManagerEntity fileManagerEntity = filedelete.getFileManager();

		// delete contrains
		fileManagerEntity.setIdEmployee(null);
		fileManagerRepository.save(fileManagerEntity);

		// delete contrains
		String fileName = filedelete.getPathFile();
		fileManagerRepository.delete(fileManagerEntity);
		fileRepository.delete(filedelete);

		// delete

		File file = new File(fileName);
		try {
			// delete file
			file.delete();
		} catch (Exception e) {
			return new ResponseEntity(null, HttpStatus.METHOD_FAILURE);
		}
		return null;
	}

	@Override
	public ResponseEntity<?> getEmployeeAllFile(Integer id) {
		// TODO Auto-generated method stub
		HashMap<String, String> response = new HashMap<>();
		EmployeeEntity employee = employeeRepository.findById(id).get();
		Set<FileManagerEntity> listfile = employee.getFile();
		for (FileManagerEntity entity : listfile) {
			FileEntity file = entity.getIdFile();
			response.put(file.getNameFile(), file.getPathFile().split("/")[1]);
		}
		return ResponseEntity.ok(response);
	}
}
