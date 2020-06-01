package com.caisw.algorithm.base;

public class BaseSubject extends BaseOperation {

    private String title;

    public BaseSubject(String title) {
        this.title = title;
    }

    public void runTest(){

    }

    public String getTitle() {
        return title;
    }
}
