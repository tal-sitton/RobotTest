package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class OI /*GEVALD */ {

    private static Joystick leftJoystick = new Joystick(0);
    private static Joystick rightJoystick = new Joystick(1);
    private static XboxController controller= new XboxController(2);

    public OI() {

    }

    public static boolean getXButton() {return controller.getXButton();}

    public static double getLeftX() { return leftJoystick.getX(); }

    public static double getLeftY() {
        return -leftJoystick.getY();
    }

    public static double getRightX() {
        return rightJoystick.getX();
    }

    public static double getRightY() {
        return -rightJoystick.getY();
    }

}
