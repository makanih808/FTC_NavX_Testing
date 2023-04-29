package org.firstinspires.ftc.teamcode;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.text.DecimalFormat;

public abstract class Robot extends LinearOpMode {

    protected DcMotorEx fl, fr, bl, br;

    protected AHRS ahrs;

    private DecimalFormat df = new DecimalFormat("#.##");

    // Gets the components and also sets its default behavior
    protected void init(HardwareMap hwMap) {
        // Gets the drive motors
        fl = hwMap.get(DcMotorEx.class, Names.Motors.kFrontLeftName);
        fr = hwMap.get(DcMotorEx.class, Names.Motors.kFrontRightName);
        bl = hwMap.get(DcMotorEx.class, Names.Motors.kBackLeftName);
        br = hwMap.get(DcMotorEx.class, Names.Motors.kBackRightName);

        // Sets the motors to stop immediately when not given and input (a.k.a. goes against inertia)
        // I personally recommend the motors to be set at BRAKE because I find it allows the drivers to have better control of the robot.
        // I also find it easier to work with for autonomous.
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // I'm using a GoBilda Strafer Chassis but set the motors to your configurations
        fl.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.FORWARD);
        br.setDirection(DcMotorSimple.Direction.FORWARD);

        // Calls and sets the NavX Micro Sensor
        ahrs = AHRS.getInstance(
                hwMap.get(NavxMicroNavigationSensor.class, Names.Sensors.kNavXName),
                AHRS.DeviceDataType.kProcessedData,
                Constants.NavXConstants.NAVX_DEVICE_UPDATE_RATE_HZ
        );
    }

    private void setPower(//
            double frontLeftPower,//
            double frontRightPower,//
            double backLeftPower,//
            double backRightPower) {//

        double maxSpeed = 1.0;
        maxSpeed = Math.max(maxSpeed, Math.abs(frontLeftPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(frontRightPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(backLeftPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(backRightPower));

        frontLeftPower /= maxSpeed;
        frontRightPower /= maxSpeed;
        backLeftPower /= maxSpeed;
        backRightPower /= maxSpeed;

        fl.setPower(frontLeftPower);
        fr.setPower(frontRightPower);
        bl.setPower(backLeftPower);
        br.setPower(backRightPower);
    }

    public void drive(double forward, double strafe, double rotate) {//
        double frontLeftPower = forward + strafe + rotate;
        double frontRightPower = forward - strafe - rotate;
        double backLeftPower = forward - strafe + rotate;
        double backRightPower = forward + strafe - rotate;

        setPower(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
    }


    // Telemetry
    /****************************************************************************************************************************************************/
    public void telemetry() {//
        MotorTelemetry();
        NavXTelemetry();
        telemetry.update();
    }

    void MotorTelemetry() {//
        telemetry.addData("Front Left Power", df.format(fl.getPower()));
        telemetry.addData("Front Right Power", df.format(fr.getPower()));
        telemetry.addData("Back Left Power", df.format(bl.getPower()));
        telemetry.addData("Back Right Power", df.format(br.getPower()));
        telemetry.addData("Front Left Position", df.format(fl.getCurrentPosition()));
        telemetry.addData("Front Right Position", df.format(fr.getCurrentPosition()));
        telemetry.addData("Back Left Position", df.format(bl.getCurrentPosition()));
        telemetry.addData("Back Right Position", df.format(br.getCurrentPosition()));
    }

    void NavXTelemetry() {//
        telemetry.addData("Yaw", df.format(ahrs.getYaw()));
    }
}
