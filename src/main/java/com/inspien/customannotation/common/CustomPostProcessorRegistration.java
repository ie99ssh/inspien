package com.inspien.customannotation.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomPostProcessorRegistration {

    public static void registerBeans(CustomBeanFactory beanFactory) {

        Map<String, CustomBeanDefinition> beanDefinitions = beanFactory.getBeanDefinitions();

        for (String key : beanDefinitions.keySet()) {
            CustomBeanDefinition beanDefinition = beanDefinitions.get(key);
            beanFactory.initializeBean(beanDefinition);
        }

        autowiringBeans(beanFactory);
    }

    private static void autowiringBeans(CustomBeanFactory beanFactory) {

        Map<String, CustomBeanDefinition> beanDefinitions = beanFactory.getBeanDefinitions();

        List<Object> objects = new ArrayList<>();
        for (String key : beanDefinitions.keySet()) {

            CustomBeanDefinition beanDefinition = beanDefinitions.get(key);
            for (Field field : beanDefinition.getAutowireFields()) {

                Class<?> type = field.getType();
                Object o = beanFactory.getMergedBeanDefinitions().get(type);

                try {
                    field.setAccessible(true);
                    field.set(beanDefinition.getBean(), o);
                } catch (IllegalAccessException e) {
                    System.out.println("bean initialization failed");
                    e.printStackTrace();
                }
            }

            Object o = beanFactory.getMergedBeanDefinitions().get(beanDefinition.getClazz());
            objects.add(o);
        }
        beanFactory.setSingletonObjects(objects);
    }

}
