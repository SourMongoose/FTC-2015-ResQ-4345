package edu.art._4345.resQ.opModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import edu.art._4345.resQ.subsystems.*;

/**
 * Teleop Op mode combing all subsystems.
 */
public class TeleOp extends OpMode {

    private Drivetrain drivetrain;
    private TriggerFlipper leftFlipper, rightFlipper;
    private Plow plow;
    private Pullup pullup;

    private boolean plowIsFront;

    @Override
    public void init() {
        drivetrain = new Drivetrain(hardwareMap.dcMotor.get("left_drive"), hardwareMap.dcMotor.get("right_drive"));
        leftFlipper = new TriggerFlipper(hardwareMap.servo.get("trigger_flipper_left"), true);
        rightFlipper = new TriggerFlipper(hardwareMap.servo.get("trigger_flipper_right"), false);
        plow = new Plow(hardwareMap.servo.get("plow"));
        pullup = new Pullup(hardwareMap.dcMotor.get("puller"), hardwareMap.dcMotor.get("aimer"));

        plowIsFront = false;
    }

    @Override
    public void loop() {
        //driving
        if(!plowIsFront) {
            drivetrain.arcadeDrive(gamepad1.left_stick_y, gamepad1.right_stick_x);
        }
        else {
            drivetrain.arcadeDrive(-1 * gamepad1.left_stick_y, -1 * gamepad1.right_stick_x);
        }

        if(gamepad1.start) {
            plowIsFront = true;
            while(gamepad1.start);
        }

        //trigger flipper control
        if(gamepad1.x) {
            leftFlipper.toggle();
            while(gamepad1.x);
        }
        else if(gamepad1.y) {
            rightFlipper.toggle();
            while(gamepad1.y);
        }

        //plow control
        if(gamepad1.a) {
            plow.toggle();
            while(gamepad1.a);
        }

        //puller control
        if(gamepad1.right_bumper) {
            pullup.pull(-1 * gamepad1.right_trigger);
        }
        else {
            pullup.pull(gamepad1.right_trigger);
        }
        if(gamepad1.left_bumper) {
            pullup.aim(-1 * gamepad1.left_trigger);
        }
        else {
            pullup.aim(gamepad1.left_trigger);
        }

    }
}
