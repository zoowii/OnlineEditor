package com.zoowii.online_editor.utils;

import java.util.UUID;

/**
 * Created by zoowii on 15/2/10.
 */
public class IdGenerator {
    public static String nextId() {
        return UUID.randomUUID().toString();
    }
}
