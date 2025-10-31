/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.sql.*;
import java.util.ArrayList;
import model.Student;

/**
 *
 * @author darhlilove
 */
public class DatabaseConnector {

    // DB_URL = "jdbc:<database>://ip_address:port/database_name";
    static final String DB_URL = "jdbc:mysql://localhost:3306/userdb";
    static final String DB_USERNAME = "root";
    static final String DB_PASSWORD = "my-secret-pw";

    // Create a connection
    // CRUD Operations
    // CRUD Operations on Database
    // C - Create: Inserting data into the database
    public static void addUser(Student user) throws SQLException {
        String query = "INSERT INTO student(full_name, age, college, phone, continent, experience) VALUES (?, ?, ?, ?, ?, ?) ";
        
        try ( // Create a connection
                Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setString(1, user.getName());
            stmt.setInt(2, user.getAge());
            stmt.setString(3, user.getCollege());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getContinent());
            stmt.setString(6, user.getExperience());
           
            // After this query will look like:
            // "INSERT INTO patient(name, age, college, phone, continent, experience) VALUES ('Nana', 27, 'College of Engineering (COE)', '437-345-3434', 'Africa', 'Football, Dancing') "
            int rows = stmt.executeUpdate();
            System.out.println("Rows inserted: " + rows);
            // Close the connection
            conn.close();
        }
    }

    // R - Read: Getting all the values from the database
    public static ArrayList<Student> getStudents() throws SQLException {
        ArrayList<Student> users;
        
    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {      
        users = new ArrayList();
       
        String query = "SELECT * FROM student";
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
            
        System.out.println("Result:" + rs);
            while (rs.next()) {
                Student user = new Student();
                        user.setId(rs.getInt("student_id"));
                        user.setName(rs.getString("full_name"));
                        user.setAge(rs.getInt("age"));
                        user.setCollege(rs.getString("college"));
                        user.setPhone(rs.getString("phone"));
                        user.setContinent(rs.getString("continent"));
                        user.setExperience(rs.getString("experience"));
                        user.add(user);
            } 
            
            rs.close();
            
        }

        return users;
    }

    // U - Update: Changing a value in the database
    public static void updateUser(Student oldUser, Student updatedStudent) throws SQLException {
        String query = "UPDATE student SET full_name = ?,  age = ?, college = ?, phone = ?, continent = ?, experience = ? WHERE student_id = ? ";

        try ( // Update the query
                Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setString(1, updatedStudent.getName());
            stmt.setInt(2, updatedStudent.getAge());
            stmt.setString(3, updatedStudent.getCollege());
            stmt.setString(4, updatedStudent.getPhone());
            stmt.setString(5, updatedStudent.getContinent());
            stmt.setString(6, updatedStudent.getExperience());
            stmt.setInt(7, oldUser.getId());
            
            int rows = stmt.executeUpdate();
            
            System.out.println("Rows updated: " + rows);
        }

    }

    // D - Delete 
    public static void deleteStudent(Student student) throws SQLException {
        String query = "DELETE FROM student WHERE student_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setInt(1, student.getId());
            
            int rows = stmt.executeUpdate();
            
            System.out.println("Rows deleted: " + rows);
        }

    }
}
