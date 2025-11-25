/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import java.util.concurrent.CountDownLatch;

/**
 * Multi-threaded music player that coordinates two threads to play musical tones
 * Thread 1: plays do, mi, sol, si, do-octave
 * Thread 2: plays re, fa, la, do-octave
 * 
 * @author Jane
 */
public class MusicThreadPlayer {
    
    private final FilePlayer filePlayer;
    private final Object lock = new Object();
    private int currentNote = 0;
    private CountDownLatch doOctaveLatch;
    
    // Note sequence for the scale
    private final String[] noteSequence = {"do", "re", "mi", "fa", "sol", "la", "si", "do-octave"};
    
    public MusicThreadPlayer() {
        this.filePlayer = new FilePlayer();
    }
    
    /**
     * Plays the musical scale: do, re, mi, fa, sol, la, si, do-octave
     */
    public void playScale() {
        System.out.println("Playing musical scale...\n");
        currentNote = 0;
        doOctaveLatch = new CountDownLatch(2);
        
        Thread thread1 = new Thread(new Thread1Player(), "Thread-1");
        Thread thread2 = new Thread(new Thread2Player(), "Thread-2");
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\nScale completed!");
    }
    
    /**
     * Plays "Twinkle Twinkle Little Star"
     */
    public void playTwinkleTwinkle() {
        System.out.println("\nPlaying Twinkle Twinkle Little Star...\n");
        
        // Song notation
        String song = "do do sol sol la la sol fa fa mi mi re re do " +
                     "sol sol fa fa mi mi re sol sol fa fa mi mi re " +
                     "do do sol sol la la sol fa fa mi mi re re do";
        
        String[] notes = song.split(" ");
        
        Thread thread1 = new Thread(new SongThread1(notes), "Thread-1");
        Thread thread2 = new Thread(new SongThread2(notes), "Thread-2");
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\nSong completed!");
    }
    
    /**
     * Thread 1: Plays do, mi, sol, si, do-octave
     */
    class Thread1Player implements Runnable {
        @Override
        public void run() {
            String[] myNotes = {"do", "mi", "sol", "si", "do-octave"};
            
            for (String note : myNotes) {
                synchronized (lock) {
                    // Wait until it's my turn
                    while (!noteSequence[currentNote].equals(note)) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    // Play the note
                    System.out.println(Thread.currentThread().getName() + " playing: " + note);
                    filePlayer.play(note + ".wav");
                    
                    // Handle do-octave synchronization
                    if (note.equals("do-octave")) {
                        doOctaveLatch.countDown();
                        try {
                            lock.wait(100); // Brief wait to let Thread 2 start
                            doOctaveLatch.await(); // Wait for both threads
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Thread.sleep(800); // Wait for note to play
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    // Move to next note
                    currentNote++;
                    lock.notifyAll();
                }
            }
        }
    }
    
    /**
     * Thread 2: Plays re, fa, la, do-octave
     */
    class Thread2Player implements Runnable {
        @Override
        public void run() {
            String[] myNotes = {"re", "fa", "la", "do-octave"};
            
            for (String note : myNotes) {
                synchronized (lock) {
                    // Wait until it's my turn
                    while (!noteSequence[currentNote].equals(note)) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    // Play the note
                    System.out.println(Thread.currentThread().getName() + " playing: " + note);
                    filePlayer.play(note + ".wav");
                    
                    // Handle do-octave synchronization
                    if (note.equals("do-octave")) {
                        doOctaveLatch.countDown();
                        try {
                            doOctaveLatch.await(); // Wait for both threads
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Thread.sleep(800); // Wait for note to play
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    // Move to next note
                    currentNote++;
                    lock.notifyAll();
                }
            }
        }
    }
    
    /**
     * Thread 1 for playing songs (do, mi, sol, si, do-octave)
     */
    class SongThread1 implements Runnable {
        private String[] notes;
        
        public SongThread1(String[] notes) {
            this.notes = notes;
        }
        
        @Override
        public void run() {
            String[] myNotes = {"do", "mi", "sol", "si"};
            
            synchronized (lock) {
                for (int i = 0; i < notes.length; i++) {
                    // Check if it's my note
                    boolean isMyNote = false;
                    for (String note : myNotes) {
                        if (notes[i].equals(note)) {
                            isMyNote = true;
                            break;
                        }
                    }
                    
                    // Wait for my turn
                    while (currentNote != i) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    if (isMyNote) {
                        System.out.println(Thread.currentThread().getName() + " playing: " + notes[i]);
                        filePlayer.play(notes[i] + ".wav");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    currentNote++;
                    lock.notifyAll();
                }
            }
        }
    }
    
    /**
     * Thread 2 for playing songs (re, fa, la, do-octave)
     */
    class SongThread2 implements Runnable {
        private String[] notes;
        
        public SongThread2(String[] notes) {
            this.notes = notes;
        }
        
        @Override
        public void run() {
            String[] myNotes = {"re", "fa", "la"};
            
            synchronized (lock) {
                for (int i = 0; i < notes.length; i++) {
                    // Check if it's my note
                    boolean isMyNote = false;
                    for (String note : myNotes) {
                        if (notes[i].equals(note)) {
                            isMyNote = true;
                            break;
                        }
                    }
                    
                    // Wait for my turn
                    while (currentNote != i) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    if (isMyNote) {
                        System.out.println(Thread.currentThread().getName() + " playing: " + notes[i]);
                        filePlayer.play(notes[i] + ".wav");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    currentNote++;
                    lock.notifyAll();
                }
            }
        }
    }
    
    /**
     * Main method to demonstrate the multi-threaded music player
     */
    public static void main(String[] args) {
        MusicThreadPlayer player = new MusicThreadPlayer();
        
        // Play the musical scale
        player.playScale();
        
        // Wait a bit between demonstrations
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Play Twinkle Twinkle Little Star (Bonus)
        player.playTwinkleTwinkle();
    }
}