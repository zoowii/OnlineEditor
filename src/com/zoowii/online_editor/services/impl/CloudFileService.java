package com.zoowii.online_editor.services.impl;

import com.zoowii.online_editor.models.CloudFileEntity;
import com.zoowii.online_editor.models.FileLogEntity;
import com.zoowii.online_editor.services.ICloudFileService;
import com.zoowii.playmore.annotation.Service;

/**
 * Created by zoowii on 15/4/4.
 */
@Service
public class CloudFileService implements ICloudFileService {
    @Override
    public void addFileChangeLog(CloudFileEntity cloudFileEntity) {
        FileLogEntity fileLogEntity = new FileLogEntity();
        fileLogEntity.setContent(cloudFileEntity.getContent());
        fileLogEntity.setDescription(cloudFileEntity.getDescription());
        fileLogEntity.setEncoding(cloudFileEntity.getEncoding());
        fileLogEntity.setFileVersion(cloudFileEntity.getFileVersion());
        fileLogEntity.setIsPrivate(cloudFileEntity.getIsPrivate());
        fileLogEntity.setLastUpdatedTime(cloudFileEntity.getLastUpdatedTime());
        fileLogEntity.setMimeType(cloudFileEntity.getMimeType());
        fileLogEntity.setName(cloudFileEntity.getName());
        fileLogEntity.setVersion(cloudFileEntity.getVersion());
        fileLogEntity.save();
    }
}
