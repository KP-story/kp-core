package com.kp.gennerator;

import com.kp.generator.spring.GenAngularModelClass;

public class GenClass {
    public static void main(String[] args) throws Exception {
        GenAngularModelClass genAngularModelClass= new GenAngularModelClass();
        genAngularModelClass.genClass("com.kp.gennerator","/home/khanh/Downloads");
    }
}
