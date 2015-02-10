package com.zoowii.online_editor.finders;

import com.zoowii.jpa_utils.query.Finder;
import com.zoowii.online_editor.models.CloudFileEntity;

/**
 * Created by zoowii on 15/2/10.
 */
public class CloudFileFinder extends Finder<Long, CloudFileEntity> {
    public CloudFileFinder(Class<?> kCls, Class<?> mCls) {
        super(kCls, mCls);
    }
}
