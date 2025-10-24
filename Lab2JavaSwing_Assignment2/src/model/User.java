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
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String phonenumber;
    private String gender;
    private String continent;
    private String hobbies;
    private Image photo;
    
    
    
// constructor to initialize the variables in private
    public User(String firstname, String lastname, int age,String email, String gender, String phonenumber, String continent, String hobbies, Image photo){
// after initializing call object of the class
    this.firstName = firstname;
    this.lastName = lastname;
    this.age = age;
    this.gender = gender;
    this.email = email;
    this.phonenumber = phonenumber;
    this.continent = continent;
    this.hobbies = hobbies;
    this.photo = photo;
  
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
            
    }

    public String getEmail() {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }
    
    public String getGender() {
        return gender;
    }

    public void setGender (String gender){
        this.gender = gender;
    }
    

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhone(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    
    } 
      public String getHobbies() {
        return hobbies;
    }
    
    public void setHobbies(String hobbies){
        this.hobbies = hobbies;

    }

    public Image getPhoto() {
        return this.photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }
     
     
     
    @Override
    public String toString() {
        return "User Information: \n" +
                "\n FirstName: " + firstName + 
                "\n LastName: " + lastName +
                "\n Age: " + age + 
                "\n Gender: "+ gender + 
                "\n Email: " + email +
                "\n Phonenumber: " + phonenumber + 
                "\n Continent: " + continent +
                "\n Hobbies: " + hobbies;
    }
    

   

 
    
}
