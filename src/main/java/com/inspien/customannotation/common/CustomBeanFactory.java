package com.inspien.customannotation.common;

import com.inspien.customannotation.annotation.CustomAutowired;
import com.inspien.customannotation.annotation.CustomComponent;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CustomBeanFactory {

    private Map<String, CustomBeanDefinition> beanDefinitions = new ConcurrentHashMap<>(64);
    private Map<Type, Object> mergedBeanDefinitions = new ConcurrentHashMap<>(64);
    private List<Object> singletonObjects = new ArrayList<>();
    private List<String> beanNames = new ArrayList<>();

    public void setResources(List<String> resources) {

        for (String resource : resources) {
            setResources(resource);
        }
    }

    private void setResources(String resource) {

        CustomBeanDefinition beanDefinition = new CustomBeanDefinition();

        Class<?> clazz;
        try {
            clazz = Class.forName(resource);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        if (isNotCandidate(clazz)) {
            return;
        }

        int autowiredCnt = 0;
        List<Field> autowiredFields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getAnnotation(CustomAutowired.class) != null) {
                autowiredCnt++;
                autowiredFields.add(field);
            }
        }

        String beanName = getBeanNameByDefaultStrategy(clazz);

        beanNames.add(beanName);
        beanDefinition.setAutowiredMode(autowiredCnt);
        beanDefinition.setClazz(clazz);
        beanDefinition.setAutowireFields(autowiredFields);
        beanDefinitions.put(beanName, beanDefinition);
    }

    private boolean isNotCandidate(Class<?> clazz) {
        if (clazz.isInterface()) {
            return true;
        }

        return !clazz.isAnnotationPresent(CustomComponent.class);
    }

    // -> utils?
    private String getBeanNameByDefaultStrategy(Class<?> clazz) {

        String clazzName = clazz.getSimpleName();
        String s1 = clazzName.substring(0, 1).toLowerCase();

        return s1 + clazzName.substring(1);
    }

    public void initializeBean(CustomBeanDefinition beanDefinition) {

        Object instance = null;
        try {
            instance = beanDefinition.getClazz().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        mergedBeanDefinitions.put(beanDefinition.getClazz(), instance);
        beanDefinition.setBean(instance);
    }

    public Optional<Object> getBean(String beanName) {
        return singletonObjects.stream().filter(item -> getBeanNameByDefaultStrategy(item.getClass()).equals(beanName)).findFirst();
    }

    public Map<String, CustomBeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }

    public void setBeanDefinitions(Map<String, CustomBeanDefinition> beanDefinitions) {
        this.beanDefinitions = beanDefinitions;
    }

    public Map<Type, Object> getMergedBeanDefinitions() {
        return mergedBeanDefinitions;
    }

    public void setMergedBeanDefinitions(Map<Type, Object> mergedBeanDefinitions) {
        this.mergedBeanDefinitions = mergedBeanDefinitions;
    }

    public List<Object> getSingletonObjects() {
        return singletonObjects;
    }

    public void setSingletonObjects(List<Object> singletonObjects) {
        this.singletonObjects = singletonObjects;
    }

    public List<String> getBeanNames() {
        return beanNames;
    }

    public void setBeanNames(List<String> beanNames) {
        this.beanNames = beanNames;
    }

}
