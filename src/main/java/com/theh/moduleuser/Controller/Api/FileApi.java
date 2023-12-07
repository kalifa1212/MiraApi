package com.theh.moduleuser.Controller.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import static com.theh.moduleuser.Constant.Constants.FILE_ENDPOINT;
public interface FileApi {
    @PostMapping(value=FILE_ENDPOINT+"upload/files/{context}/{id}",produces= MediaType.APPLICATION_JSON_VALUE)
    Object savePhoto(String context,Integer id, @RequestPart("file") MultipartFile multipartFile) throws IOException;
    // find by id
    @PostMapping(value=FILE_ENDPOINT+"upload/database/{context}",produces= MediaType.APPLICATION_JSON_VALUE)
    Object uploadDataBase(@RequestParam(value = "context") String context, @RequestPart("file") MultipartFile multipartFile) throws IOException;

}