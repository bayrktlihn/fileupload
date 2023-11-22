package io.bayrktlihn.fileuploadproject;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class MultiPartFileUtil {

    private static String DEFAULT_UPLOAD_BASE_FOLDER_PATH = "C:\\FileUploads";
    private static String DEFAULT_RELATIVE_FOLDER_PATH_ACCORDING_TO_DEFAULT_UPLOAD_BASE_FOLDER = "";


    public static String uploadFile(String defaultUploadBaseFolder, String relativeFolderPathAccordingToDefaultUploadBaseFolder, String base64EncodedContent) {

        //TODO: Think this. Png defualt? What if it is not a png extension file?
        String fileNameWithExtension = "base64.png";

        String toBeGeneratedFullPathFile = toBeGeneratedFullPathFile(fileNameWithExtension, defaultUploadBaseFolder, relativeFolderPathAccordingToDefaultUploadBaseFolder);
        Path pathOfToBeGeneratedFullPathFile = Paths.get(toBeGeneratedFullPathFile);

        try {
            Path file = Files.createFile(pathOfToBeGeneratedFullPathFile);
            try (OutputStream createdFileOutputStream = Files.newOutputStream(file)) {
                //TODO: Optimize this
                byte[] fileContent = Base64.getDecoder().decode(base64EncodedContent);
                createdFileOutputStream.write(fileContent);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return toBeGeneratedFullPathFile.substring((defaultUploadBaseFolder + File.separator).length());
    }

    public static String uploadFile(String defaultUploadBaseFolder, String base64EncodedFileContent) {
        return uploadFile(defaultUploadBaseFolder, DEFAULT_RELATIVE_FOLDER_PATH_ACCORDING_TO_DEFAULT_UPLOAD_BASE_FOLDER, base64EncodedFileContent);
    }

    public static String uploadFile(String base64EncodedFileContent) {
        return uploadFile(DEFAULT_UPLOAD_BASE_FOLDER_PATH, base64EncodedFileContent);
    }

    public static String uploadFile(String defaultUploadBaseFolder, String relativeFolderPathAccordingToDefaultUploadBaseFolder, MultipartFile multipartFile) {

        String toBeGeneratedFullPathFile = toBeGeneratedFullPathFile(multipartFile.getOriginalFilename(), defaultUploadBaseFolder, relativeFolderPathAccordingToDefaultUploadBaseFolder);
        Path pathOfToBeGeneratedFullPathFile = Paths.get(toBeGeneratedFullPathFile);

        try {
            Path file = Files.createFile(pathOfToBeGeneratedFullPathFile);
            try (OutputStream createdFileOutputStream = Files.newOutputStream(file)) {
                try (InputStream inputStream = multipartFile.getInputStream()) {
                    byte[] buffer = new byte[1024];
                    int readedSize = inputStream.read(buffer);
                    while (readedSize != -1) {
                        createdFileOutputStream.write(buffer, 0, readedSize);
                        readedSize = inputStream.read(buffer);
                    }

                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return toBeGeneratedFullPathFile.substring((defaultUploadBaseFolder + File.separator).length());
    }


    public static String uploadFile(String defaultUploadBaseFolder, MultipartFile multipartFile) {
        return uploadFile(defaultUploadBaseFolder, DEFAULT_RELATIVE_FOLDER_PATH_ACCORDING_TO_DEFAULT_UPLOAD_BASE_FOLDER, multipartFile);
    }

    public static String uploadFile(MultipartFile multipartFile) {
        return uploadFile(DEFAULT_UPLOAD_BASE_FOLDER_PATH, multipartFile);
    }

    private static String toBeGeneratedFullPathFile(String fileNameWithExtension, String defaultUploadBaseFolder, String relativeFolderPathAccordingToDefaultUploadBaseFolder) {
        String relativeFolderPath = relativeFolderPathAccordingToDefaultUploadBaseFolder == null || relativeFolderPathAccordingToDefaultUploadBaseFolder.isEmpty() ? "" : relativeFolderPathAccordingToDefaultUploadBaseFolder + File.separator;

        String fileName = Randomizer.generateByDateTime() + fileNameWithExtension;
        String fullPath = defaultUploadBaseFolder + File.separator + relativeFolderPath + fileName;

        while (Files.exists(Paths.get(fullPath))) {
            fileName = Randomizer.generateByDateTime() + fileNameWithExtension;
            fullPath = defaultUploadBaseFolder + File.separator + relativeFolderPath + fileName;
        }
        return fullPath;
    }

}
