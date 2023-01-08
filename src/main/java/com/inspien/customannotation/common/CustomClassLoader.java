package com.inspien.customannotation.common;

import com.inspien.customannotation.annotation.CustomComponent;
import org.reflections.Reflections;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CustomClassLoader extends ClassLoader {

    @Override
    public Class<?> findClass(String name) {
        byte[] bt = loadClassData(name);
        return defineClass(name, bt, 0, bt.length);
    }

    private byte[] loadClassData(String className) {

        InputStream is = getClass().getClassLoader().getResourceAsStream(className.replace(".", "/") + ".class");
        ByteArrayOutputStream byteSt = new ByteArrayOutputStream();

        int len;
        try {
            while ((len = is.read()) != -1) {
                byteSt.write(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteSt.toByteArray();
    }

    public List<String> findAllResources(String classPath) {
        return new Reflections(classPath).getTypesAnnotatedWith(CustomComponent.class).stream().map(Class::getTypeName).collect(toList());
    }

}
