package com.example.firestore_quick_search;

public class Name {
    String name;
    String sname;


    public Name(String name, String sname) {
        this.name = name;
        this.sname = sname;
    }

    public Name() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
