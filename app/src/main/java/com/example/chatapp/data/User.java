package com.example.chatapp.data;

public class User {

    private String id;
    private String name;
    private String lastName;
    private String age;
    private boolean isOnline;

    public User(String id, String name, String lastName, String age, boolean isOnline) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.isOnline = isOnline;
    }

    public User(){

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAge() {
        return age;
    }

    public boolean isOnline() {
        return isOnline;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", isOnline=" + isOnline +
                '}';
    }
}
