package Essence;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Person {

    private Date date = new Date();
    private Integer id = 0;
    private String name = null;
    private String surnem = null;
    private String mail = null;
    private String numb = null;

    public Person(String name, String surnem,String mail, String numb) {
        this.name = name;
        this.surnem = surnem;
        this.mail = mail;
        this.numb = numb;
    }

    public Person(Integer id , String name, String surnem,String mail, String numb) {
        this.id = id;
        this.name = name;
        this.surnem = surnem;
        this.mail = mail;
        this.numb = numb;
    }

    //    Getters Setter
    public void setDate() {this.date = date;}
    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
        return formatter.format(date);
    }
    public void setId(Integer id) {this.id = id;}
    public Integer getId() {return this.id;}

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public void setSurnem(String surnem){
        this.surnem = surnem;
    }
    public String getSurnem(){
        return this.surnem;
    }

    public void setMail(String mail){
        this.mail = mail;
    }
    public String getMail(){
        return this.mail;
    }

    public void setNumb(String numb){this.numb = numb;}
    public String getNumb(){
        return this.numb;
    }
}

