/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package view;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main Application Class
 * Entry point for JavaFX application
 */
public class FXMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("Loading FXML...");
            
            // Load FXML file
            Parent root = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));
            
            System.out.println("FXML loaded successfully");
            
            // Create scene
            Scene scene = new Scene(root, 600, 400);
            
            // Setup stage
            primaryStage.setTitle("Registration Form - JavaFX");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            
            System.out.println("Application started successfully");
            
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Main method - Entry point
     */
    public static void main(String[] args) {
        launch(args);
    }
}