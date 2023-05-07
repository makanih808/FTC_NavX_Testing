package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Hardware.Robot;

@TeleOp(name = "Op Mode Testing")
public class OpMode extends Robot {

    double reset_angle = 0;

    Gamepad driver, system;

    @Override
    public void runOpMode() throws InterruptedException {
        init(hardwareMap);

        driver = gamepad1;
        system = gamepad2;

        waitForStart();
        if (opModeIsActive() && !isStopRequested()) {
            while (opModeIsActive() && !isStopRequested()) {
                driver(driver);

                // Telemetry
                MotorTelemetry();
                telemetry.update();
            }
        }
    }

    private void driver(Gamepad driver) {
        double rotate = driver.right_stick_x * .5;
        double stickLimit = Math.sqrt(Math.pow(1-Math.abs(rotate), 2)/2);
        double forward = -driver.left_stick_y * stickLimit;
        double strafe = driver.left_stick_x * stickLimit;

        double gyroAngle = heading();

        if (driver.a) {
            reset_angle = gyroAngle + reset_angle;
        }

        if (gyroAngle <= 0 || (0 < gyroAngle && gyroAngle < Math.PI / 2)) {
            gyroAngle += (Math.PI / 2);
        } else if (Math.PI / 2 <= gyroAngle) {
            gyroAngle -= (3 * Math.PI / 2);
        }
        gyroAngle *= -1;

        if(gamepad1.right_bumper){ //Disables gyro, sets to -Math.PI/2 so front is defined correctly.
            gyroAngle = -Math.PI/2;
        }

        double theta = Math.atan2(forward, strafe) - gyroAngle - (Math.PI / 2);
        double magnitude = Math.hypot(forward, strafe);
        double Px = magnitude * Math.sin(theta + Math.PI / 4);
        double Py = magnitude * Math.sin(theta - Math.PI / 4);

        fl.setPower(Py - rotate);
        bl.setPower(Px - rotate);
        br.setPower(Py + rotate);
        fr.setPower(Px + rotate);
    }

    private double heading() {
        double yaw = Math.toRadians(ahrs.getYaw());

        if (yaw < -Math.PI) {
            yaw += (2 * Math.PI);
        } else if (yaw > Math.PI) {
            yaw -= (2 * Math.PI);
        }

        yaw -= reset_angle;
        return yaw;
    }
}
