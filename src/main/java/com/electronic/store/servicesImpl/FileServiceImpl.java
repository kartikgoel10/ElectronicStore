package com.electronic.store.servicesImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.electronic.store.exceptions.BadApiRequestException;
import com.electronic.store.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

	@Override
	public String uploadFile(MultipartFile file, String path) throws IOException {
		String originalFilename = file.getOriginalFilename();
		logger.info("Filename : {}", originalFilename);
		String encryptedFileName = UUID.randomUUID().toString();
		String fileExtention = originalFilename.substring(originalFilename.lastIndexOf("."));
		String fileNameWithExtention = encryptedFileName + fileExtention;
		String fullPathWithFileName = path + fileNameWithExtention;
		
		logger.info("full image path : {}" , fullPathWithFileName);

		if (fileExtention.equalsIgnoreCase(".jpg") || fileExtention.equalsIgnoreCase(".png")
				|| fileExtention.equalsIgnoreCase(".jpeg")) {
			
			logger.info("file extention : {}" , fileExtention);
			File folder = new File(path);
			if (!folder.exists()) {
				// create folder
				folder.mkdirs();
			}
			// upload
			Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
			return fileNameWithExtention;
		} else {
			throw new BadApiRequestException("File name with " + fileExtention + " not allowed ! ");
		}
	}

	@Override
	public InputStream getResource(String path, String name) throws FileNotFoundException {
		String fullPath = path + File.separator + name;
		InputStream inputStream = new FileInputStream(fullPath);
		return inputStream;
	}

}
