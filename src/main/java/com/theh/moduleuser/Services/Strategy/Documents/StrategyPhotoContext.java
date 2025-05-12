package com.theh.moduleuser.Services.Strategy.Documents;

import com.theh.moduleuser.Exceptions.ErrorCodes;
import com.theh.moduleuser.Exceptions.InvalidEntityException;
import com.theh.moduleuser.Model.Context;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class StrategyPhotoContext {

    private BeanFactory beanFactory;
    private StrategyPhoto strategyPhoto;
    @Setter
    private String context;

    @Autowired
    public StrategyPhotoContext(BeanFactory beanFactory){
        this.beanFactory=beanFactory;
    }

    public Object savePhoto(Context context, Integer id, MultipartFile multipartFile) throws IOException {
        determinContext(context.toString());
        return strategyPhoto.savePhoto(id,multipartFile);
    }
    public Object displayPhoto(Integer id,String context){
        determinContext(context);
        return strategyPhoto.downloadPhoto(id);
    }

    private void determinContext(String Context){
        final String beanName=Context+"Strategy";
        switch (Context){
            case "imam":
                strategyPhoto = beanFactory.getBean(beanName,SaveUserPhoto.class);
                break;
            case "user":
                strategyPhoto = beanFactory.getBean(beanName,SaveUserPhoto.class);
                break;
            case "mosque":
                strategyPhoto = beanFactory.getBean(beanName,SaveMosquePhoto.class);
                break;
            case "sermont":
                strategyPhoto = beanFactory.getBean(beanName,SavePreche.class);
                break;
            case "preche":
                strategyPhoto = beanFactory.getBean(beanName,SavePreche.class);
                break;
            default: throw new InvalidEntityException("Context inconnue pour l'enregistrement de la photo", ErrorCodes.UNKNOW_CONTEXT);
        }
    }
}