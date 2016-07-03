package com.example.shtb.feigua;

/**
 * Created by shtb on 16-1-17.
 */
public class list_answer {
    private String answer,user,id;
    public list_answer(String an,String us,String i)
    {
        this.answer=an;
        this.user=us;
        this.id=i;
    }


    public String getAnswer() {
        return answer;
    }

    public String getUser() {
        return user;
    }
    public String getId() {
        return id;
    }

}
