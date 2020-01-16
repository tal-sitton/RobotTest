/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.spikes2212.command.drivetrains.TankDrivetrain;
import com.spikes2212.utils.Namespace;
import com.spikes2212.utils.RootNamespace;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotMap;

public class Drivetrain extends TankDrivetrain {
    private static final Namespace NAMESPACE=new RootNamespace("Drivetrain");
    private static Drivetrain instance;
    private static Encoder leftEncoder;
    private static Encoder rightEncoder;
    private static ADXRS450_Gyro gyro;

    private Drivetrain(SpeedController left, SpeedController right, Encoder leftEncoder, Encoder rightEncoder, ADXRS450_Gyro gyro) {
        super(left, right);
        Drivetrain.leftEncoder = leftEncoder;
        Drivetrain.rightEncoder = rightEncoder;
        Drivetrain.gyro = gyro;
    }

    public static Drivetrain getInstance() {
        if (instance == null) {
            WPI_TalonSRX leftTalon = new WPI_TalonSRX(RobotMap.CAN.DRIVETRAIN_TALON_LEFT);
            WPI_VictorSPX leftVictor = new WPI_VictorSPX(RobotMap.CAN.DRIVETRAIN_VICTOR_LEFT);
            leftVictor.follow(leftTalon);
            WPI_TalonSRX rightTalon = new WPI_TalonSRX(RobotMap.CAN.DRIVETRAIN_TALON_RIGHT);
            WPI_VictorSPX rightVictor = new WPI_VictorSPX(RobotMap.CAN.DRIVETRAIN_VICTOR_RIGHT);
            rightVictor.follow(rightTalon);
            leftEncoder = new Encoder(RobotMap.DIO.DRIVETRAIN_ENCODER_LEFT_A, RobotMap.DIO.DRIVETRAIN_ENCODER_LEFT_B);
            rightEncoder = new Encoder(RobotMap.DIO.DRIVETRAIN_ENCODER_RIGHT_A, RobotMap.DIO.DRIVETRAIN_ENCODER_RIGHT_B);
            gyro = new ADXRS450_Gyro();
            instance = new Drivetrain(leftTalon, rightTalon, leftEncoder, rightEncoder, gyro);
        }
        return instance;
    }

    public static Encoder getRightEncoder() {
        return rightEncoder;
    }

    public static Encoder getLeftEncoder() {
        return leftEncoder;
    }

    public static ADXRS450_Gyro getGyro() {
        return gyro;
    }

    public void initDashboard() {
        NAMESPACE.putNumber("Right encoder distance",rightEncoder.getDistance());
        NAMESPACE.putNumber("Left encoder distance",leftEncoder.getDistance());
        NAMESPACE.putNumber("Gyro angle",gyro.getAngle());
        NAMESPACE.putData("reset encoders", new InstantCommand(() -> {
            leftEncoder.reset();
            rightEncoder.reset();
        }, this));
        NAMESPACE.putData("reset gyro", new InstantCommand(() -> {
            gyro.reset();
        }, this));
    }
}
