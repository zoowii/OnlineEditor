package com.zoowii.online_editor.services;

import com.zoowii.online_editor.models.CloudFileEntity;

/**
 * Created by zoowii on 15/4/4.
 */
public interface ICloudFileService {
    void addFileChangeLog(CloudFileEntity cloudFileEntity);
}
