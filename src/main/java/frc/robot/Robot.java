package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

/**
 * Ciclo de vida do robo e configuracao do registro (logging).
 *
 * Estende LoggedRobot, e nao TimedRobot: e o que permite reexecutar depois,
 * no computador, exatamente o codigo que rodou na partida (modo REPLAY).
 */
public class Robot extends LoggedRobot {

  private RobotContainer robotContainer;
  private Command autonomousCommand;

  @Override
  public void robotInit() {
    Logger.recordMetadata("Equipe", String.valueOf(Constants.TEAM_NUMBER));
    Logger.recordMetadata("Robo", Constants.ROBOT_TYPE.toString());

    switch (Constants.getMode()) {
      case REAL -> {
        // No robo: grava no pendrive e publica para o dashboard.
        Logger.addDataReceiver(new WPILOGWriter());
        Logger.addDataReceiver(new NT4Publisher());
      }
      case SIM -> Logger.addDataReceiver(new NT4Publisher());
      case REPLAY -> {
        // Reprocessa um log gravado em vez de ler sensores reais.
        setUseTiming(false);
        String log = org.littletonrobotics.junction.LogFileUtil.findReplayLog();
        Logger.setReplaySource(new WPILOGReader(log));
        Logger.addDataReceiver(
            new WPILOGWriter(org.littletonrobotics.junction.LogFileUtil.addPathSuffix(log, "_replay")));
      }
    }

    Logger.start();

    robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    autonomousCommand = robotContainer.getAutonomousCommand();
    if (autonomousCommand != null) {
      autonomousCommand.schedule();
    }
  }

  @Override
  public void teleopInit() {
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
  }

  @Override
public void simulationPeriodic() {
  robotContainer.simulationPeriodic();
}
}
