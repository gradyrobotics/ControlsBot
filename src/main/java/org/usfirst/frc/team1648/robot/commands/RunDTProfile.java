package org.usfirst.frc.team1648.robot.commands;

import java.io.FileNotFoundException;

import org.usfirst.frc.team1648.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1648.utilities.G3Command;

/**
 * A G3Command that runs the specified motion profile on the drivetrain
 * 
 * @author Swag31415
 */
public class RunDTProfile implements G3Command {

    // Declaring the SubSystem we'll be using
    DriveTrain driveTrain;

    // The parameter for the command, the motion profile that gets run
    int profileNumber;

    // A boolean that holds the state of this command
    boolean hasRun;

    /**
     * Makes a RunDTProfile command with profileNumber as a parameter and a driveTrain
     * as the system being modified
     * 
     * @param driveTrain  A 4 wheel driveTrain with encoders and a gyro
     * @param profileNumber The motion profile that gets run
     */
    public RunDTProfile(DriveTrain driveTrain, int profileNumber) {
        this.driveTrain = driveTrain;
        this.profileNumber = profileNumber;
    }

    /**
     * Gets the number of the motion profile being run
     * 
     * @return Returns the profile number
     */
    public int getProfileNumber() {
        return profileNumber;
    }

    /**
     * Sets the motion profile to be used
     * 
     * @param profileNumber The number of the motion profile to be used
     */
    public void setProfileNumber(int profileNumber) {
        this.profileNumber = profileNumber;
    }

    @Override
    public void intitialize() {
        // Resetting DT parameters
        driveTrain.resetDistance();
        // Intilializaing the state of the command
        hasRun = false;
    }

    @Override
    public void run() {
        try {
            if (!hasRun && driveTrain.runProfile(profileNumber)) {
                hasRun = true;
            }
        } catch (FileNotFoundException e) {
            //TODO: handle exception
        }
    }

    @Override
    public boolean isDone() {
        // if the command has run then it is done
        if (hasRun) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void close() {
        // Stops the DriveTrain
        driveTrain.setPercentOutput(0, 0);
        hasRun = true;
    }
}
