package org.usfirst.frc.team1648.utilities;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

/**
 * A Modified VictorSPX with more convenient constructors
 * 
 * @author Swag31415
 */
public class G3Victor extends VictorSPX {

    /**
     * Creates a VictorSPX using the provided device number
     * @param deviceNumber The Victor's deviceId on the CAN bus
     * @param isInverted  Whether to invert the victor or not
     */
    public G3Victor(int deviceNumber, boolean isInverted) {
        super(deviceNumber);
        setInverted(isInverted);
    }

    /**
     * Creates a follower VictorSPX using the provided device number
     * @param deviceNumber The Victor's deviceId on the CAN bus
     * @param isInverted  Whether to invert the victor or not
     * @param masterTalon The talon this victor should follow
     */
    public G3Victor(int deviceNumber, boolean isInverted, TalonSRX masterTalon) {
        super(deviceNumber);
        setInverted(isInverted);
        follow(masterTalon);
    }
}