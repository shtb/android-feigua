package com.example.shtb.feigua;

/**
 * Created by shtb on 15-4-9.
 */
public class got_class {
private String lesson,address,intr,time,teacher;
    public got_class(String l,String a,String c,String t,String tea)
    {
        this.lesson=l;
        this.address=a;
        this.intr=c;
        this.time=t;
        this.teacher=tea;
    }


    public String getLesson() {
        return lesson;
    }

    public String getAddress() {
        return address;
    }

    public String getIntr() {
        return intr;
    }

    public String getTime() {
        return time;
    }

    public String getTeacher() {
        return teacher;
    }
}
