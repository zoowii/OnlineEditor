package com.zoowii.online_editor.utils

import org.apache.commons.codec.binary.Base64

import java.nio.charset.Charset
import java.security.MessageDigest

class Common {
    def static randomString(int n) {
        def str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        def random = new Random()
        def buf = new StringBuilder()
        for (def i = 0; i < n; i++) {
            int num = random.nextInt(str.length())
            buf.append(str.charAt(num))
        }
        return buf.toString()
    }

    def static base64Encode(byte[] source) {
        return Base64.encodeBase64String(source)
    }

    def static base64Encode(String source) {
        return base64Encode(source.getBytes('UTF-8'))
    }

    def static md5(byte[] source) {
        def digest = MessageDigest.getInstance("MD5")
        digest.update(source)
        return digest.digest()
    }

    def static md5(String source) {
        return md5(source.getBytes('UTF-8'))
    }

    def static md5String(String source) {
        return base64Encode(md5(source))
    }

    def static findClassMethodByName(Class cls, String name) {
        if (cls == null) {
            return null
        }
        def methods = cls.getDeclaredMethods()
        for (def method : methods) {
            if (method.name.equals(name)) {
                return method
            }
        }
        return null
    }

    def static String getPackageOfClass(Class cls) {
        return cls.getPackage().name
    }

    def static Class findClassUnderPackage(String packageName, String clsName) {
        def fullClsName = "${packageName}.${clsName}"
        println(fullClsName)
        try {
            return Class.forName(fullClsName)
        } catch (Exception e) {
            return null
        }
    }

    def static Class findControllerClassUnderPackage(String packageName, String controllerName) {
        def names = [controllerName, ('' + controllerName.charAt(0)).toUpperCase() + controllerName.substring(1), controllerName + 'Controller', ('' + controllerName.charAt(0)).toUpperCase() + controllerName.substring(1) + 'Controller']
        for (def name : names) {
            def cls = findClassUnderPackage(packageName, name)
            if (cls) {
                return cls
            }
        }
        return null
    }

}
