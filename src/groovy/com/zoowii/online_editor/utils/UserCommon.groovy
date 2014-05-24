package com.zoowii.online_editor.utils

class UserCommon {
    def static encryptPassword(String password, String salt) {
        return Common.md5String(Common.md5String(password?.trim()) + salt?.trim())
    }
}
