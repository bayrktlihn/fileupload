package io.bayrktlihn.fileuploadproject;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {


    @Value("${default-base-upload-folder}")
    public String defaultBaseUploadFolder;


    public String uploadFile(MultipartFile multipartFile) {
        String createdRelativePathAccordingToDefaultBaseUploadFolder = MultiPartFileUtil.uploadFile(multipartFile);
        return createdRelativePathAccordingToDefaultBaseUploadFolder;
    }

    public String uploadFile(String base64EncodedContent) {
        String createdRelativePathAccordingToDefaultBaseUploadFolder = MultiPartFileUtil.uploadFile(base64EncodedContent);
        return createdRelativePathAccordingToDefaultBaseUploadFolder;
    }


}
