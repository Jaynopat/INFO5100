/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * JUnit test class for MusicThreadPlayer
 * Tests thread synchronization and note sequence
 * 
 * @author Jane
 */
public class MusicThreadPlayerTest {
    
    private MusicThreadPlayer player;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    
    @Before
    public void setUp() {
        player = new MusicThreadPlayer();
        System.setOut(new PrintStream(outContent));
    }
    
    @Test
    public void testMusicThreadPlayerCreation() {
        assertNotNull("MusicThreadPlayer should be created", player);
    }
    
    @Test
    public void testPlayScaleExecutes() {
        try {
            player.playScale();
            String output = outContent.toString();
            
            // Verify that scale execution started
            assertTrue("Output should contain 'Playing musical scale'", 
                      output.contains("Playing musical scale"));
            assertTrue("Output should contain 'Scale completed'", 
                      output.contains("Scale completed"));
            
        } catch (Exception e) {
            fail("playScale should execute without exceptions: " + e.getMessage());
        }
    }
    
    @Test
    public void testScaleNoteSequence() {
        player.playScale();
        String output = outContent.toString();
        
        // Expected notes in order
        String[] expectedNotes = {"do", "re", "mi", "fa", "sol", "la", "si", "do-octave"};
        
        // Check each note appears in output
        for (String note : expectedNotes) {
            assertTrue("Output should contain note: " + note, 
                      output.contains("playing: " + note));
        }
    }
    
    @Test
    public void testBothThreadsExecute() {
        player.playScale();
        String output = outContent.toString();
        
        // Verify both threads executed
        assertTrue("Thread-1 should execute", output.contains("Thread-1"));
        assertTrue("Thread-2 should execute", output.contains("Thread-2"));
    }
    
    @Test
    public void testThread1Notes() {
        player.playScale();
        String output = outContent.toString();
        
        // Thread 1 should play: do, mi, sol, si, do-octave
        String[] thread1Notes = {"do", "mi", "sol", "si", "do-octave"};
        
        for (String note : thread1Notes) {
            assertTrue("Thread-1 should play " + note, 
                      output.contains("Thread-1 playing: " + note));
        }
    }
    
    @Test
    public void testThread2Notes() {
        player.playScale();
        String output = outContent.toString();
        
        // Thread 2 should play: re, fa, la, do-octave
        String[] thread2Notes = {"re", "fa", "la", "do-octave"};
        
        for (String note : thread2Notes) {
            assertTrue("Thread-2 should play " + note, 
                      output.contains("Thread-2 playing: " + note));
        }
    }
    
    @Test
    public void testBothThreadsPlayDoOctave() {
        player.playScale();
        String output = outContent.toString();
        
        // Both threads should play do-octave
        assertTrue("Thread-1 should play do-octave", 
                  output.contains("Thread-1 playing: do-octave"));
        assertTrue("Thread-2 should play do-octave", 
                  output.contains("Thread-2 playing: do-octave"));
    }
    
    @Test
    public void testTwinkleTwinkleExecutes() {
        try {
            player.playTwinkleTwinkle();
            String output = outContent.toString();
            
            assertTrue("Output should contain 'Playing Twinkle Twinkle'", 
                      output.contains("Playing Twinkle Twinkle"));
            assertTrue("Output should contain 'Song completed'", 
                      output.contains("Song completed"));
            
        } catch (Exception e) {
            fail("playTwinkleTwinkle should execute without exceptions: " + e.getMessage());
        }
    }
    
    @Test
    public void testTwinkleTwinkleHasCorrectNotes() {
        player.playTwinkleTwinkle();
        String output = outContent.toString();
        
        // Verify song contains the main notes
        String[] songNotes = {"do", "sol", "la", "fa", "mi", "re"};
        
        for (String note : songNotes) {
            assertTrue("Song should contain note: " + note, 
                      output.contains("playing: " + note));
        }
    }
    
    @Test
    public void testThreadSynchronization() {
        player.playScale();
        String output = outContent.toString();
        
        // Get positions of each note in output
        int doPos = output.indexOf("playing: do\n");
        int rePos = output.indexOf("playing: re");
        int miPos = output.indexOf("playing: mi");
        
        // Verify sequential order (do comes before re, re before mi)
        assertTrue("Notes should play in sequence: do before re", 
                  doPos < rePos && doPos != -1 && rePos != -1);
        assertTrue("Notes should play in sequence: re before mi", 
                  rePos < miPos && rePos != -1 && miPos != -1);
    }
    
    @Test
    public void testNoDeadlock() {
        long startTime = System.currentTimeMillis();
        player.playScale();
        long endTime = System.currentTimeMillis();
        
        // Should complete within reasonable time (10 seconds)
        long duration = endTime - startTime;
        assertTrue("Scale should complete without deadlock (within 10 seconds)", 
                  duration < 10000);
    }
    
    @Test
    public void testMultipleExecutions() {
        // Test that player can be used multiple times
        try {
            player.playScale();
            outContent.reset(); // Clear output
            player.playScale();
            
            String output = outContent.toString();
            assertTrue("Second execution should work", 
                      output.contains("Playing musical scale"));
            
        } catch (Exception e) {
            fail("Multiple executions should work: " + e.getMessage());
        }
    }
    
    /**
     * Restore System.out after tests
     */
    @org.junit.After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}