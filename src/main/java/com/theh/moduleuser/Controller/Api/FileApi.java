package com.theh.moduleuser.Controller.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import static com.theh.moduleuser.Constant.Constants.FILE_ENDPOINT;
import static com.theh.moduleuser.Constant.Constants.IMAGE_ENDPOINT;
import static org.springframework.http.MediaType.IMAGE_PNG;

@CrossOrigin(origins = "*")
@SecurityRequirement(name = "Bearer Authentication")
public interface FileApi {

    @Operation(summary = "Enregistrer ",description = "Save Image context(user,imam,mosque,preche) and the id of element")
    @PostMapping(value=FILE_ENDPOINT+"upload/files/{context}/{id}",consumes=MediaType.MULTIPART_FORM_DATA_VALUE,produces= MediaType.APPLICATION_JSON_VALUE)
    Object savePhoto(@PathVariable("context") String context,@PathVariable("id") Integer id, @RequestPart("file") MultipartFile multipartFile) throws IOException;
    // find by id
    @PostMapping(value=FILE_ENDPOINT+"upload/database/{context}",produces= MediaType.APPLICATION_JSON_VALUE)
    Object uploadDataBase(@RequestParam(value = "context") String context, @RequestPart("file") MultipartFile multipartFile) throws IOException;

    @Operation(summary = "Display ",description = "Display Image context(user,imam,mosque,preche) and the id of element")
    @GetMapping(value = IMAGE_ENDPOINT+"display/{id}/{context}", produces= MediaType.IMAGE_JPEG_VALUE)
    ResponseEntity getFile(@PathVariable( "id") Integer id,@PathVariable("context")String context);
}