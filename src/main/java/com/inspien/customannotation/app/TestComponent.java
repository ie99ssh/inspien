package com.inspien.customannotation.app;

import com.inspien.customannotation.annotation.CustomComponent;
import com.inspien.customannotation.annotation.CustomPostConstruct;

@CustomComponent
public class TestComponent {

    @CustomPostConstruct
    public void start() {
        System.out.println("TestComponent Start!!!");
    }

    public void print(String text) {
        System.out.println("text : " + text);
    }
}
