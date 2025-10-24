/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.awt.Image;

/**
 *
 * @author Admin
 */
public class User {
    
    private String firstname;
    private String lastname;
    private int age;
    private String email;
    private String phone;
    private String continent;
    private String hobby;
    private Image photo;
    
    
    
// constructor to initialize the variables in private
    public User(String firstname, String lastname, int age, String email, String phone, String continent, String hobby1, Image photo){

// after initializing call object of the class
    this.firstname = firstname;
    this.lastname = lastname;
    this.age = age;
    this.email = email;
    this.phone = phone;
    this.continent = continent;
    this.hobby = hobby;
    this.photo = photo;
  
    }

   public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    
    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
            
    }

    public String getEmail() {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }
    

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContinent(){
        return continent;
    }
    
    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
    
    
    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

     
     
    @Override
    public String toString() {
        return "User Information: \n" +
                "\n FirstName: " + firstname + 
                "\n LastName: " + lastname +
                "\n Age: " + age + 
                "\n Email: " + email +
                "\n Phone: " + phone + 
                "\n Continent: " + continent +
                "\n Hobby: " + hobby;
    }
    

   

 
    
}
