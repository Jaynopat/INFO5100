/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lunarroverstatemachine;

/**
 *
 * @author Admin
 */     
        /**
 * Lunar Rover Control System - State Machine Implementation in Java
 * Based on UML State Machine diagram
 */

// ============================================================================
// ENUMERATIONS
// ============================================================================

enum PedalInput {
    LEFT_QUICK,      // < 1 second
    LEFT_HOLD_3S,    // >= 3 seconds
    LEFT_HOLD_5S,    // >= 5 seconds
    LEFT_HOLD_10S,   // >= 10 seconds
    LEFT_DOUBLE,     // Two presses within 1 second
    RIGHT_QUICK,     // < 1 second
    RIGHT_HOLD_3S    // >= 3 seconds
}

enum ControlMode {
    MOVEMENT,
    CAMERA_DRILL
}

enum MovementState {
    AT_REST,
    ACCELERATING_FORWARD,
    CONSTANT_SPEED,
    DECELERATING,
    ACCELERATING_BACKWARD,
    DECELERATING_BACKWARD
}

enum CameraDrillState {
    IDLE,
    WAITING_FOR_5S,
    WAITING_FOR_10S,
    WAITING_FOR_DOUBLE,
    COLOR_CAMERA_ACTIVE,
    COLOR_TEMPORIZER,
    MM16_CAMERA_ACTIVE,
    MM16_TEMPORIZER,
    DRILL_OFF,
    DRILL_ON
}

// ============================================================================
// LUNAR ROVER STATE MACHINE
// ============================================================================

public class LunarRoverStateMachine {
    
    // Current states
    private ControlMode controlMode;
    private MovementState movementState;
    private CameraDrillState cameraDrillState;
    
    // Vehicle state variables
    private double speed;
    private boolean drillRunning;
    
    // Constructor
    public LunarRoverStateMachine() {
        this.controlMode = ControlMode.MOVEMENT;
        this.movementState = MovementState.AT_REST;
        this.cameraDrillState = CameraDrillState.IDLE;
        this.speed = 0.0;
        this.drillRunning = false;
        System.out.println("Lunar Rover initialized: Movement Control, At Rest");
    }
    
    // ========================================================================
    // MODE SWITCHING
    // ========================================================================
    
    public void switchMode() {
        if (controlMode == ControlMode.MOVEMENT) {
            controlMode = ControlMode.CAMERA_DRILL;
            movementState = MovementState.AT_REST;
            speed = 0.0;
            cameraDrillState = CameraDrillState.IDLE;
            System.out.println("→ Switched to Camera/Drill Control (rover stopped)");
        } else {
            controlMode = ControlMode.MOVEMENT;
            cameraDrillState = CameraDrillState.IDLE;
            movementState = MovementState.AT_REST;
            drillRunning = false;
            System.out.println("→ Switched to Movement Control (all devices off)");
        }
    }
    
    // ========================================================================
    // MOVEMENT CONTROL STATE MACHINE
    // ========================================================================
    
    private void handleMovementInput(PedalInput input) {
        switch (movementState) {
            case AT_REST:
                if (input == PedalInput.LEFT_QUICK) {
                    movementState = MovementState.ACCELERATING_FORWARD;
                    System.out.println("→ Accelerating Forward");
                } else if (input == PedalInput.LEFT_HOLD_3S) {
                    movementState = MovementState.ACCELERATING_BACKWARD;
                    System.out.println("→ Accelerating Backward");
                }
                break;
                
            case ACCELERATING_FORWARD:
                if (input == PedalInput.RIGHT_QUICK) {
                    movementState = MovementState.DECELERATING;
                    System.out.println("→ Decelerating");
                } else if (input == PedalInput.RIGHT_HOLD_3S) {
                    movementState = MovementState.CONSTANT_SPEED;
                    System.out.println("→ Constant Forward Speed");
                } else if (input == PedalInput.LEFT_QUICK) {
                    System.out.println("  [Left pedal ignored - safety]");
                }
                break;
                
            case CONSTANT_SPEED:
                if (input == PedalInput.RIGHT_QUICK) {
                    movementState = MovementState.DECELERATING;
                    System.out.println("→ Decelerating");
                } else {
                    System.out.println("  [Input ignored in constant speed]");
                }
                break;
                
            case DECELERATING:
                if (speed <= 0) {
                    movementState = MovementState.AT_REST;
                    System.out.println("→ At Rest (speed = 0)");
                } else {
                    System.out.println("  [Input ignored - decelerating]");
                }
                break;
                
            case ACCELERATING_BACKWARD:
                if (input == PedalInput.RIGHT_QUICK) {
                    movementState = MovementState.DECELERATING_BACKWARD;
                    System.out.println("→ Decelerating from Backward");
                } else if (input == PedalInput.LEFT_QUICK) {
                    System.out.println("  [Left pedal ignored while backward]");
                }
                break;
                
            case DECELERATING_BACKWARD:
                if (speed <= 0) {
                    movementState = MovementState.AT_REST;
                    System.out.println("→ At Rest (speed = 0)");
                } else {
                    System.out.println("  [Input ignored - decelerating]");
                }
                break;
        }
    }
    
    // ========================================================================
    // CAMERA/DRILL CONTROL STATE MACHINE
    // ========================================================================
    
    private void handleCameraDrillInput(PedalInput input) {
        switch (cameraDrillState) {
            case IDLE:
                if (input == PedalInput.LEFT_HOLD_5S) {
                    cameraDrillState = CameraDrillState.COLOR_CAMERA_ACTIVE;
                    System.out.println("→ Color Camera Mode (Active)");
                } else if (input == PedalInput.LEFT_HOLD_10S) {
                    cameraDrillState = CameraDrillState.MM16_CAMERA_ACTIVE;
                    System.out.println("→ 16-mm Camera Mode (Active)");
                } else if (input == PedalInput.LEFT_DOUBLE) {
                    cameraDrillState = CameraDrillState.DRILL_OFF;
                    System.out.println("→ Drill Mode (OFF)");
                }
                break;
                
            case COLOR_CAMERA_ACTIVE:
                if (input == PedalInput.LEFT_QUICK) {
                    System.out.println("  [Color Camera: Picture taken!]");
                } else if (input == PedalInput.LEFT_HOLD_5S) {
                    cameraDrillState = CameraDrillState.COLOR_TEMPORIZER;
                    System.out.println("→ Color Temporizer (10s countdown)");
                } else if (input == PedalInput.RIGHT_QUICK) {
                    cameraDrillState = CameraDrillState.IDLE;
                    System.out.println("→ Returned to Idle");
                }
                break;
                
            case COLOR_TEMPORIZER:
                if (input == PedalInput.RIGHT_QUICK) {
                    cameraDrillState = CameraDrillState.IDLE;
                    System.out.println("→ Temporizer cancelled");
                } else {
                    System.out.println("  [Input ignored during temporizer]");
                }
                break;
                
            case MM16_CAMERA_ACTIVE:
                if (input == PedalInput.LEFT_QUICK) {
                    System.out.println("  [16-mm Camera: Picture taken!]");
                } else if (input == PedalInput.LEFT_HOLD_5S) {
                    cameraDrillState = CameraDrillState.MM16_TEMPORIZER;
                    System.out.println("→ 16-mm Temporizer (10s countdown)");
                } else if (input == PedalInput.RIGHT_QUICK) {
                    cameraDrillState = CameraDrillState.IDLE;
                    System.out.println("→ Returned to Idle");
                }
                break;
                
            case MM16_TEMPORIZER:
                if (input == PedalInput.RIGHT_QUICK) {
                    cameraDrillState = CameraDrillState.IDLE;
                    System.out.println("→ Temporizer cancelled");
                } else {
                    System.out.println("  [Input ignored during temporizer]");
                }
                break;
                
            case DRILL_OFF:
                if (input == PedalInput.LEFT_QUICK) {
                    cameraDrillState = CameraDrillState.DRILL_ON;
                    drillRunning = true;
                    System.out.println("→ Drill ON");
                } else if (input == PedalInput.RIGHT_QUICK) {
                    cameraDrillState = CameraDrillState.IDLE;
                    System.out.println("→ Returned to Idle");
                }
                break;
                
            case DRILL_ON:
                if (input == PedalInput.LEFT_QUICK) {
                    cameraDrillState = CameraDrillState.DRILL_OFF;
                    drillRunning = false;
                    System.out.println("→ Drill OFF");
                } else if (input == PedalInput.RIGHT_QUICK) {
                    cameraDrillState = CameraDrillState.IDLE;
                    drillRunning = false;
                    System.out.println("→ Returned to Idle (drill stopped)");
                }
                break;
        }
    }
    
    // ========================================================================
    // MAIN INPUT HANDLER
    // ========================================================================
    
    public void handleInput(PedalInput input) {
        System.out.println("\nInput: " + input);
        
        if (controlMode == ControlMode.MOVEMENT) {
            handleMovementInput(input);
            System.out.println("State: " + movementState);
        } else {
            handleCameraDrillInput(input);
            System.out.println("State: " + cameraDrillState);
        }
    }
    
    // ========================================================================
    // TEMPORIZER COMPLETION
    // ========================================================================
    
    public void completeTemporizer() {
        if (cameraDrillState == CameraDrillState.COLOR_TEMPORIZER) {
            System.out.println("  [Color Camera: Temporizer picture taken!]");
            cameraDrillState = CameraDrillState.COLOR_CAMERA_ACTIVE;
            System.out.println("→ Returned to Color Camera Active");
        } else if (cameraDrillState == CameraDrillState.MM16_TEMPORIZER) {
            System.out.println("  [16-mm Camera: Temporizer picture taken!]");
            cameraDrillState = CameraDrillState.MM16_CAMERA_ACTIVE;
            System.out.println("→ Returned to 16-mm Camera Active");
        }
    }
    
    // ========================================================================
    // GETTERS
    // ========================================================================
    
    public ControlMode getControlMode() { return controlMode; }
    public MovementState getMovementState() { return movementState; }
    public CameraDrillState getCameraDrillState() { return cameraDrillState; }
    public double getSpeed() { return speed; }
    public boolean isDrillRunning() { return drillRunning; }
    
    public void setSpeed(double speed) { this.speed = speed; }
    
    // ========================================================================
    // STATUS DISPLAY
    // ========================================================================
    
    public void printStatus() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("LUNAR ROVER STATUS");
        System.out.println("=".repeat(60));
        System.out.println("Control Mode: " + controlMode);
        if (controlMode == ControlMode.MOVEMENT) {
            System.out.println("Movement State: " + movementState);
            System.out.println("Speed: " + speed + " km/h");
        } else {
            System.out.println("Camera/Drill State: " + cameraDrillState);
            System.out.println("Drill Running: " + drillRunning);
        }
        System.out.println("=".repeat(60) + "\n");
    }
    
    // ========================================================================
    // MAIN - DEMONSTRATION
    // ========================================================================
    
    public static void main(String[] args) {
        LunarRoverStateMachine rover = new LunarRoverStateMachine();
        rover.printStatus();
        
        System.out.println("=".repeat(60));
        System.out.println("SCENARIO 1: Movement Control");
        System.out.println("=".repeat(60));
        rover.handleInput(PedalInput.LEFT_QUICK);        // Accelerate forward
        rover.handleInput(PedalInput.RIGHT_HOLD_3S);     // Constant speed
        rover.handleInput(PedalInput.RIGHT_QUICK);       // Decelerate
        rover.setSpeed(0);                               // Simulate deceleration
        rover.handleInput(PedalInput.LEFT_QUICK);        // Check transition to rest
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("SCENARIO 2: Switch to Camera/Drill Control");
        System.out.println("=".repeat(60));
        rover.switchMode();
        rover.printStatus();
        
        System.out.println("=".repeat(60));
        System.out.println("SCENARIO 3: Color Camera Operation");
        System.out.println("=".repeat(60));
        rover.handleInput(PedalInput.LEFT_HOLD_5S);      // Activate color camera
        rover.handleInput(PedalInput.LEFT_QUICK);        // Take picture
        rover.handleInput(PedalInput.LEFT_HOLD_5S);      // Start temporizer
        rover.completeTemporizer();                      // Timer completes
        rover.handleInput(PedalInput.RIGHT_QUICK);       // Return to idle
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("SCENARIO 4: Drill Operation");
        System.out.println("=".repeat(60));
        rover.handleInput(PedalInput.LEFT_DOUBLE);       // Enter drill mode
        rover.handleInput(PedalInput.LEFT_QUICK);        // Turn drill ON
        rover.handleInput(PedalInput.LEFT_QUICK);        // Turn drill OFF
        rover.handleInput(PedalInput.RIGHT_QUICK);       // Return to idle
        
        rover.printStatus();
    }
}
    
    

