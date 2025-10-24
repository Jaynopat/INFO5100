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
public class Student {
    private String name;
    private int age;
    private String college;
    private String phone;
    private String continent;
    private String experience;
    private Image photo;
    
    
    
// constructor to initialize the variables in private
    public Student(String name,int age, String college, String phone, String continent, String experience, Image photo){
// after initializing call object of the class
    this.name = name;
    this.age = age;
    this.college = college;
    this.phone = phone;
    this.continent = continent;
    this.experience = experience;
    this.photo = photo;
  
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    
    } 
        public String getexperience(){
            return experience;
    }
        
     public void setexperience(String experience) {
         this.experience = experience;
     }

    public Image getPhoto() {
        return this.photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }
     
     
     
    @Override
    public String toString() {
        return "Student Information: \n" +
                "\n Name: " + name + 
                "\n Age: " + age + 
                "\n College: "+ college + 
                "\n Phone: " + phone + 
                "\n Continent: " + continent +
                "\n experience: " + experience;
    }
    

   

 
    
}
