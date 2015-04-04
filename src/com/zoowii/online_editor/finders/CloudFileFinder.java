package com.zoowii.online_editor.finders;

import com.zoowii.jpa_utils.extension.ExtendFinder;
import com.zoowii.online_editor.models.CloudFileEntity;

/**
 * Created by zoowii on 15/2/10.
 */
public class CloudFileFinder extends ExtendFinder<Long, CloudFileEntity> {
    public CloudFileFinder(Class<?> kCls, Class<?> mCls) {
        super(kCls, mCls);
    }
}
