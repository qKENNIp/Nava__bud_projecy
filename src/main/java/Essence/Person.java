package Essence;

public class Person {

    private String name = null;
    private String surnem = null;
    private String mail = null;
    private Integer numb = null;

    public Person(String name , String surnem,String mail, Integer numb) {
        this.name = name;
        this.surnem = surnem;
        this.mail = mail;
        this.numb = numb;
    }
    public Person(String name ,String surnem ,String  mail) {
        this.name = name;
        this.surnem = surnem;
        this.mail = mail;
    }
    public Person(String name ,String surnem ,Integer numb) {
        this.name = name;
        this.surnem = surnem;
        this.numb = numb;
    }

    //    Getters Setter
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

    public void setNumb(Integer numb){
        this.numb = numb;
    }
    public Integer getNumb(){
        return this.numb;
    }
}

