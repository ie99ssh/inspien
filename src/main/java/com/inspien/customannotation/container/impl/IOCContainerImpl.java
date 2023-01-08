package com.inspien.customannotation.container.impl;

import com.inspien.customannotation.annotation.CustomPostConstruct;
import com.inspien.customannotation.common.CustomBeanFactory;
import com.inspien.customannotation.common.CustomClassLoader;
import com.inspien.customannotation.common.CustomPostProcessorRegistration;
import com.inspien.customannotation.container.IOCContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class IOCContainerImpl implements IOCContainer {

    private final CustomBeanFactory beanFactory = new CustomBeanFactory();

    public void initialize(String basePackage) {
        invokeBeanFactoryPostProcessors(basePackage);
        finishBeanFactoryInitialization(beanFactory);
        init(beanFactory);
    }

    private void finishBeanFactoryInitialization(CustomBeanFactory beanFactory) {
        CustomPostProcessorRegistration.registerBeans(beanFactory);
    }

    private void invokeBeanFactoryPostProcessors(String basePackage) {

        CustomClassLoader classLoader = new CustomClassLoader();

        List<String> resources = classLoader.findAllResources(basePackage);
        beanFactory.setResources(resources);
    }

    private static void init(CustomBeanFactory beanFactory) {

        List<Object> list = beanFactory.getSingletonObjects();

        for (Object obj : list) {
            Method[] methods = obj.getClass().getDeclaredMethods();
            for (Method method : methods) {

                Annotation[] annotations = method.getDeclaredAnnotations();

                try {
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof CustomPostConstruct) {
                            method.invoke(obj);
                            break;
                        }
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
