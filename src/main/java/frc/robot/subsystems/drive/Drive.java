package frc.robot.subsystems.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.FieldConstants;

import java.util.function.DoubleSupplier;
import org.littletonrobotics.junction.Logger;

/**
 * A logica da tracao.
 *
 * Nao sabe qual hardware esta embaixo dela: recebe um DriveIO pronto e conversa
 * so com o contrato. Trocar SPARK MAX por simulacao e trocar uma linha no
 * RobotContainer, sem tocar neste arquivo.
 *
 * Os comandos SIMPLES do mecanismo ficam aqui. Os compostos ficam em
 * DriveCommands, ao lado.
 */
public class Drive extends SubsystemBase {

  private final DriveIO io;
  private final DriveIOInputsAutoLogged inputs = new DriveIOInputsAutoLogged();
  private final DifferentialDriveOdometry odometry =
    new DifferentialDriveOdometry(new Rotation2d(), 0.0, 0.0, FieldConstants.ROBOT_START_POSE);

  private final SimpleMotorFeedforward feedforward =
      new SimpleMotorFeedforward(
          DriveConstants.FEEDFORWARD_KS_VOLTS,
          DriveConstants.FEEDFORWARD_KV_VOLTS_PER_METER_PER_SECOND);

  public Drive(DriveIO io) {
    this.io = io;
  }

  @Override
  public void periodic() {
    // 1. le o hardware   2. grava no log   3. decide o que fazer
    io.updateInputs(inputs);
    Logger.processInputs("Drive", inputs);

    odometry.update(getYaw(), inputs.leftPositionMeters, inputs.rightPositionMeters);
Logger.recordOutput("Drive/Pose", getPose());
  }
    public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

 
  // --- Acoes ---------------------------------------------------------------

  /** Direcao por joystick: um eixo para a frente, outro para girar. */
  public void driveArcade(double forwardPercent, double rotationPercent) {
    double forward = MathUtil.clamp(forwardPercent, -1.0, 1.0);
    double rotation = MathUtil.clamp(rotationPercent, -1.0, 1.0);

    io.setVoltage(
        (forward + rotation) * DriveConstants.MAX_VOLTS,
        (forward - rotation) * DriveConstants.MAX_VOLTS);
  }

  /** Direcao por velocidade, em metros por segundo de cada lado. */
  public void driveVelocity(
      double leftMetersPerSecond, double rightMetersPerSecond) {
    io.setVoltage(
        feedforward.calculate(leftMetersPerSecond),
        feedforward.calculate(rightMetersPerSecond));
  }

  public void stop() {
    io.setVoltage(0.0, 0.0);
  }

  public void resetEncoders() {
  io.resetEncoders();
  odometry.resetPosition(getYaw(), 0.0, 0.0, getPose());
}

  public void setBrakeMode(boolean enabled) {
    io.setBrakeMode(enabled);
  }

  // --- Leituras ------------------------------------------------------------

  public double getLeftPositionMeters() {
    return inputs.leftPositionMeters;
  }

  public double getRightPositionMeters() {
    return inputs.rightPositionMeters;
  }

  public double getAveragePositionMeters() {
    return (inputs.leftPositionMeters + inputs.rightPositionMeters) / 2.0;
  }

  public Rotation2d getYaw() {
    return new Rotation2d(inputs.gyroYawRadians);
  }

  // --- Comandos simples ----------------------------------------------------

  /** Comando padrao do mecanismo: dirigir pelo joystick enquanto nada mais pedir a tracao. */
  public Command arcadeDriveCommand(DoubleSupplier forward, DoubleSupplier rotation) {
    return run(() -> driveArcade(forward.getAsDouble(), rotation.getAsDouble()))
        .withName("ArcadeDrive");
  }

  public Command stopCommand() {
    return runOnce(this::stop).withName("Stop");
  }
}
