package com.theh.moduleuser.Controller.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import static com.theh.moduleuser.Constant.Constants.FILE_ENDPOINT;

@SecurityRequirement(name = "Bearer Authentication")
public interface FileApi {
    @PostMapping(value=FILE_ENDPOINT+"upload/files/{context}/{id}",consumes=MediaType.MULTIPART_FORM_DATA_VALUE,produces= MediaType.APPLICATION_JSON_VALUE)
    Object savePhoto(@PathVariable("context") String context,@PathVariable("id") Integer id, @RequestPart("file") MultipartFile multipartFile) throws IOException;
    // find by id
    @PostMapping(value=FILE_ENDPOINT+"upload/database/{context}",produces= MediaType.APPLICATION_JSON_VALUE)
    Object uploadDataBase(@RequestParam(value = "context") String context, @RequestPart("file") MultipartFile multipartFile) throws IOException;

}