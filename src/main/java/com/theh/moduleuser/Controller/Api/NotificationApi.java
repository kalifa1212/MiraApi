package com.theh.moduleuser.Controller.Api;
import com.theh.moduleuser.Dto.MosqueDto;
import com.theh.moduleuser.Dto.NotificationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.theh.moduleuser.Constant.Constants.APP_ROOT;
import static com.theh.moduleuser.Constant.Constants.NOTIFICATION_ENDPOINT;


@CrossOrigin(origins = "*")
@RequestMapping(NOTIFICATION_ENDPOINT)
@SecurityRequirement(name = "Bearer Authentication")
public interface NotificationApi {

    @Operation(summary = "Notification ",description = "Emettre une notification ")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",description = "Enregistrer"),
            @ApiResponse(responseCode = "401",description = "Notificatin non Autoriser",content = @Content),
            @ApiResponse(responseCode = "400",description = "Notification Invalide",content = @Content),
            @ApiResponse(responseCode = "403",description = "Connection requise",content = @Content)

    })
    @PostMapping(value = NOTIFICATION_ENDPOINT+"nouveau/",consumes= MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<NotificationDto> save(@RequestBody NotificationDto  dto);

    @GetMapping(value=NOTIFICATION_ENDPOINT+"find/all")
    Page<NotificationDto>  findAllNotification(@RequestParam(required = false,defaultValue = "creationDate") String sortColumn,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int taille,
                                               @RequestParam(defaultValue = "desc") String sortDirection);

    @GetMapping(value=NOTIFICATION_ENDPOINT+"find/{id}")
    List<NotificationDto> findById(@PathVariable("id") Integer id);



    @GetMapping(value=NOTIFICATION_ENDPOINT+"find/type/{type}")
    Page<NotificationDto> findByType(@PathVariable("type") String type,
                                     @RequestParam(required = false,defaultValue = "creationDate") String sortColumn,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "5") int taille,
                                     @RequestParam(defaultValue = "desc") String sortDirection);
}
