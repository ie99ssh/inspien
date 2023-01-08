package com.inspien.customannotation.common;

import java.lang.reflect.Field;
import java.util.List;

interface BeanDefinition {
    int getAutowiredMode();

    void setAutowiredMode(int autowiredMode);

    Class<?> getClazz();

    void setClazz(Class<?> clazz);

    List<Field> getAutowireFields();

    void setAutowireFields(List<Field> autowireFields);

    Object getBean();

    void setBean(Object bean);
}
