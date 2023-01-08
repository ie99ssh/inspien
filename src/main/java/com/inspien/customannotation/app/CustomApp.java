package com.inspien.customannotation.app;

import com.inspien.customannotation.annotation.CustomAutowired;
import com.inspien.customannotation.annotation.CustomComponent;
import com.inspien.customannotation.annotation.CustomPostConstruct;

@CustomComponent
public class CustomApp {

    @CustomAutowired
    private TestComponent component;

    @CustomPostConstruct
    public void start() {
        component.print("TEST!!");
    }
}
