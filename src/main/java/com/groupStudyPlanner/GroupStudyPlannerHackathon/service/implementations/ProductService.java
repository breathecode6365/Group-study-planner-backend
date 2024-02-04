package com.groupStudyPlanner.GroupStudyPlannerHackathon.service.implementations;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.UserDAO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ProductService {
    private static final String COLLECTION_NAME = "products";

    private final Storage storage;
    private final String bucketName = "hackathon-ccca9.appspot.com";

    public ProductService(Storage storage) {
        this.storage = storage;
    }

    public String uploadFile(MultipartFile pdfFile, UserDAO userDAO) throws IOException {
        String fileName = generateUniqueFileName(pdfFile.getOriginalFilename());
        Bucket bucket = storage.get(bucketName);

        bucket.create(fileNameCreator(fileName,  userDAO), pdfFile.getBytes(), pdfFile.getContentType());

        return getDownloadUrl(fileName);
    }
    public String fileNameCreator(String fileName, UserDAO userDao) {
        return userDao.getName() + fileName;
    }

    private String generateUniqueFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "_" + originalFileName;
    }

    private String getDownloadUrl(String fileName) {
        return "https://storage.googleapis.com/" + bucketName + "/" + fileName;
    }
}
