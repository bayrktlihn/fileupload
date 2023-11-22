package io.bayrktlihn.fileuploadproject;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("files")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("upload")
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        return fileUploadService.uploadFile(file);
    }

    @PostMapping("base64-encoded-file-upload")
    public String fileUpload(@RequestBody Map<String, Object> content) {
        return fileUploadService.uploadFile((String) content.get("base64EncodedFileContent"));
    }


}
