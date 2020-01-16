package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.spikes2212.command.genericsubsystem.GenericSubsystem;
import com.spikes2212.utils.Namespace;
import com.spikes2212.utils.RootNamespace;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.RobotMap;


public class Gripper extends GenericSubsystem {
    private static final Namespace NAMESPACE = new RootNamespace("Gripper");
    private static Gripper instance;
    private static DigitalInput limit;
    private static SpeedControllerGroup motor;

    private Gripper(SpeedControllerGroup motor, DigitalInput limit) {
        Gripper.motor = motor;
        Gripper.limit = limit;
    }

    public static Gripper getInstance() {
        if (instance == null) {
            motor = new SpeedControllerGroup(new WPI_VictorSPX(RobotMap.PWM.GRIPPER_LEFT), new WPI_VictorSPX(RobotMap.PWM.GRIPPER_RIGHT));
            limit = new DigitalInput(RobotMap.DIO.GRIPPER_LIMIT);
            instance = new Gripper(motor, limit);
        }
        return instance;
    }

    @Override
    public void apply(double speed) {
        motor.set(speed);

    }

    @Override
    public boolean canMove(double speed) {
        return (!(speed < 0) || limit.get()) && ((!(speed > 0)) || !limit.get());
    }

    @Override
    public void stop() {
        motor.stopMotor();
    }

    public void initDashboard() {
        NAMESPACE.putBoolean("Gripper limit", limit.get());
    }
}
