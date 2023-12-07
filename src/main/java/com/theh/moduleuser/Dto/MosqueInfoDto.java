package com.theh.moduleuser.Dto;

import com.theh.moduleuser.Model.Mosque;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class MosqueInfoDto {
    private List<Mosque> mosqueAfterCreationDate;
    private List<Mosque> mosqueAfterLastModifiedDate;
    private long mosqueCreationDate;
    private long mosqueLastModified; 
}