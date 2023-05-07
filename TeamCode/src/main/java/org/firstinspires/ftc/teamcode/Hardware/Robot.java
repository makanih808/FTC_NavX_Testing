package org.firstinspires.ftc.teamcode.Hardware;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants.Constants;
import org.firstinspires.ftc.teamcode.Constants.Names;

import java.text.DecimalFormat;

public abstract class Robot extends LinearOpMode {

    protected DcMotorEx fl, fr, bl, br;

    protected AHRS ahrs;

    private DecimalFormat df = new DecimalFormat("#.##");

    private InitSetup initSetup = new InitSetup();

    // Gets the components and also sets its default behavior
    protected void init(HardwareMap hwMap) {

        // Sets up the drive motors
        initSetup.DcMotorExInit(
                fl,
                hwMap,
                Names.Motors.kFrontRightName,
                DcMotorEx.ZeroPowerBehavior.BRAKE,
                DcMotorEx.Direction.FORWARD);
        initSetup.DcMotorExInit(
                fr,
                hwMap,
                Names.Motors.kFrontRightName,
                DcMotorEx.ZeroPowerBehavior.BRAKE,
                DcMotorEx.Direction.REVERSE);
        initSetup.DcMotorExInit(
                bl,
                hwMap,
                Names.Motors.kBackLeftName,
                DcMotorEx.ZeroPowerBehavior.BRAKE,
                DcMotorEx.Direction.FORWARD);
        initSetup.DcMotorExInit(
                br,
                hwMap,
                Names.Motors.kBackRightName,
                DcMotorEx.ZeroPowerBehavior.BRAKE,
                DcMotorEx.Direction.FORWARD);

        // Calls and sets the NavX Micro Sensor
        ahrs = AHRS.getInstance(
                hwMap.get(NavxMicroNavigationSensor.class, Names.Sensors.kNavXName),
                AHRS.DeviceDataType.kProcessedData,
                Constants.NavXConstants.NAVX_DEVICE_UPDATE_RATE_HZ
        );
    }

    /****************************************************************************************************************************************************/

    // Telemetry
    /****************************************************************************************************************************************************/
        public void MotorTelemetry() {//
        // Telemetry for motor powers
        telemetry.addLine("Drive Motor Power \n");
        telemetry.addData("Front Left Power", df.format(fl.getPower()));
        telemetry.addData("Front Right Power", df.format(fr.getPower()));
        telemetry.addData("Back Left Power", df.format(bl.getPower()));
        telemetry.addData("Back Right Power", df.format(br.getPower()));

        //Telemetry for motor positions
        telemetry.addLine("Drive Motor Positions \n");
        telemetry.addData("Front Left Position", df.format(fl.getCurrentPosition()));
        telemetry.addData("Front Right Position", df.format(fr.getCurrentPosition()));
        telemetry.addData("Back Left Position", df.format(bl.getCurrentPosition()));
        telemetry.addData("Back Right Position", df.format(br.getCurrentPosition()));
    }
}
