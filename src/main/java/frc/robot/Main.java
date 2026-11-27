package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * Ponto de entrada exigido pela WPILib. Nao altere este arquivo.
 */
public final class Main {

  private Main() {}

  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}
