package org.firstinspires.ftc.teamcode.Hardware;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants.Constants;
import org.firstinspires.ftc.teamcode.Constants.Names;

import java.text.DecimalFormat;

public abstract class Robot extends LinearOpMode {

    protected DcMotorEx fl, fr, bl, br;

    protected AHRS ahrs;

    private DecimalFormat df = new DecimalFormat("#.##");

    // Gets the components and also sets its default behavior
    protected void init(HardwareMap hwMap) {

        // Sets up the drive motors
        fl = hwMap.get(DcMotorEx.class, Names.Motors.kFrontLeftName);
        fr = hwMap.get(DcMotorEx.class, Names.Motors.kFrontRightName);
        bl = hwMap.get(DcMotorEx.class, Names.Motors.kBackLeftName);
        br = hwMap.get(DcMotorEx.class, Names.Motors.kBackRightName);

        fl.setDirection(DcMotorEx.Direction.FORWARD);
        fr.setDirection(DcMotorEx.Direction.REVERSE);
        bl.setDirection(DcMotorEx.Direction.FORWARD);
        br.setDirection(DcMotorEx.Direction.FORWARD);

        fl.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);


        // Calls and sets the NavX Micro Sensor
        ahrs = AHRS.getInstance(
                hwMap.get(NavxMicroNavigationSensor.class, Names.Sensors.kNavXName),
                AHRS.DeviceDataType.kProcessedData,
                Constants.NavXConstants.NAVX_DEVICE_UPDATE_RATE_HZ
        );
    }

    // Telemetry
    /****************************************************************************************************************************************************/
        public void MotorTelemetry() {//
        // Telemetry for motor powers
        telemetry.addLine("Drive Motor Power");
        telemetry.addData("Front Left Power", df.format(fl.getPower()));
        telemetry.addData("Front Right Power", df.format(fr.getPower()));
        telemetry.addData("Back Left Power", df.format(bl.getPower()));
        telemetry.addData("Back Right Power", df.format(br.getPower()) + "\n");

        //Telemetry for motor positions
        telemetry.addLine("Drive Motor Positions \n");
        telemetry.addData("Front Left Position", df.format(fl.getCurrentPosition()));
        telemetry.addData("Front Right Position", df.format(fr.getCurrentPosition()));
        telemetry.addData("Back Left Position", df.format(bl.getCurrentPosition()));
        telemetry.addData("Back Right Position", df.format(br.getCurrentPosition()) + "\n");
    }
}
