package com.mineme.server.common.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class S3Uploader {
	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.server.local}")
	private String uploadFolder;

	@Value("${cloud.aws.s3.bucket.name}")
	private String bucket;

	@Value("${cloud.aws.s3.bucket.directory}")
	private String bucketDirectory;

	@Value("${cloud.aws.s3.url}")
	private String awsUrl;

	public List<String> uploadFiles(List<MultipartFile> files, String directory) {
		List<String> uploadPaths = new ArrayList<>();

		try {
			for (MultipartFile file : files) {
				File convertFile = convert(file)
					.orElseThrow(() -> new CustomException(ErrorCode.FILE_CONVERT));
				uploadPaths.add(upload(convertFile, directory));
			}
			return uploadPaths;
		} catch (IOException e) {
			throw new CustomException(ErrorCode.FILE_UPLOAD);
		}
	}

	private Optional<File> convert(MultipartFile file) throws IOException {
		String originalFilename = file.getOriginalFilename();
		String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

		String newFilename = UUID.randomUUID().toString() + extension;
		File convertFile = new File(uploadFolder, newFilename);
		InputStream fileStream = file.getInputStream();
		FileUtils.copyInputStreamToFile(fileStream, convertFile);

		try {
			file.transferTo(convertFile);
			return Optional.of(convertFile);
		} catch (IllegalStateException e) {
			throw new CustomException(ErrorCode.FILE_CONVERT);
		}
	}

	private String upload(File uploadFile, String dirName) {
		String fileName = bucketDirectory + "/" + dirName + "/" + uploadFile.getName();
		String uploadImageUrl = putS3(uploadFile, fileName);
		removeNewFile(uploadFile);
		return awsUrl + fileName;
	}

	private String putS3(File uploadFile, String fileName) {
		amazonS3Client.putObject(
			new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
		return amazonS3Client.getUrl(bucket, fileName).toString();
	}

	private void removeNewFile(File targetFile) {
		if (targetFile.delete()) {
			log.debug("file deletion successful.");
		} else {
			log.error("file deletion failed.");
		}
	}
}
