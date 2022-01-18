package com.example.lab4;

public class Person {
    private String username;
    private String name;
    private String age;
    private String tel;
    public Person(){

    }
    public  Person(String username,String name,String age,String tel){
        this.username=username;
        this.name=name;
        this.age=age;
        this.tel=tel;
    }
    public String getUsername() {return this.username;}
    public String getName(){
        return this.name;
    }
    public String getAge() {return this.age;}
    public String getTel(){
        return this.tel;
    }
    public void setUsername(String username) {this.username=username;}
    public void setName(String name){
        this.name=name;
    }
    public void setAge(String age) {this.age=age;}
    public void setTel(String tel){
        this.tel=tel;
    }
}
