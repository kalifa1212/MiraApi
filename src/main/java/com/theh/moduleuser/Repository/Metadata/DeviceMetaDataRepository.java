package com.theh.moduleuser.Repository.Metadata;

import com.theh.moduleuser.Model.MetaData.DeviceMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceMetaDataRepository extends JpaRepository<DeviceMetaData, Long> {
    List<DeviceMetaData> findByUserId(Long userId);
}
