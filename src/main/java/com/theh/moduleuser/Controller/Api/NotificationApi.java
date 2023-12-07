package com.theh.moduleuser.Controller.Api;
import com.theh.moduleuser.Dto.NotificationDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import static com.theh.moduleuser.Constant.Constants.APP_ROOT;
import static com.theh.moduleuser.Constant.Constants.NOTIFICATION_ENDPOINT;


@CrossOrigin(origins = "*")
@RequestMapping(NOTIFICATION_ENDPOINT)
public interface NotificationApi {


    @GetMapping(value=APP_ROOT+"find/all")
    List<NotificationDto> findAllNotification();

    @GetMapping(value=APP_ROOT+"find/{id}")
    List<NotificationDto> findByUtilisateur(@PathVariable("id") Integer id);

    @GetMapping(value=APP_ROOT+"find/type/{type}")
    List<NotificationDto> findByType(@PathVariable("type") String type);
}
