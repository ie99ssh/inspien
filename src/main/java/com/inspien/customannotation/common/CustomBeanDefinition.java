package com.inspien.customannotation.common;

import java.lang.reflect.Field;
import java.util.List;

public class CustomBeanDefinition implements BeanDefinition {
    private Class<?> clazz;
    private Object bean;
    private List<Field> autowireFields;
    private int autowiredMode = 0;

    public int getAutowiredMode() {
        return autowiredMode;
    }

    public void setAutowiredMode(int autowiredMode) {
        this.autowiredMode = autowiredMode;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public List<Field> getAutowireFields() {
        return autowireFields;
    }

    public void setAutowireFields(List<Field> autowireFields) {
        this.autowireFields = autowireFields;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
