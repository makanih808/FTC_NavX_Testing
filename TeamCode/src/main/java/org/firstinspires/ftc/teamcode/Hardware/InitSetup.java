package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

// Separate class for getting the motor components and setting its default behavior
public class InitSetup {

    // Motor Setup
    public void DcMotorExInit(DcMotorEx motor, HardwareMap hwMap, String name, DcMotorEx.ZeroPowerBehavior behavior, DcMotorEx.Direction direction) {

        motor = hwMap.get(DcMotorEx.class, name);

        // Sets the motors to stop immediately when not given and input (a.k.a. goes against inertia)
        // I personally recommend the motors to be set at BRAKE because I find it allows the drivers to have better control of the robot but you can set it to FLOAT.
        // I also find it easier to work with for autonomous.
        motor.setZeroPowerBehavior(behavior);

        // I'm using a GoBilda Strafer Chassis but set the motors to your configurations
        motor.setDirection(direction);
    }

    public void CRServoInit(CRServo servo, HardwareMap hwMap, String name, DcMotorEx.Direction direction) {

        servo = hwMap.get(CRServo.class, name);

        servo.setDirection(direction);
    }

    public void ServoInit(Servo servo, HardwareMap hwMap, String name) {
        servo = hwMap.get(Servo.class, name);
    }
}
