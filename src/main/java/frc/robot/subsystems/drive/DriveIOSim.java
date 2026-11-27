package frc.robot.subsystems.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import frc.robot.Constants;
import frc.robot.FieldConstants;
import frc.robot.simulation.ArenaFisica;
import org.littletonrobotics.junction.Logger;

/**
 * Implementacao simulada da tracao.
 *
 * Cumpre o mesmo contrato do SPARK MAX, so que alimentando um modelo fisico em
 * vez de motores reais. Para a classe Drive as duas sao indistinguiveis, e e
 * por isso que da para programar e testar sem o robo montado.
 */
public class DriveIOSim implements DriveIO {

  private final ArenaFisica arena = new ArenaFisica();
private double leftOffsetMeters = 0.0;
private double rightOffsetMeters = 0.0;

public DriveIOSim() {
  sim.setPose(FieldConstants.ROBOT_START_POSE);
}

  private final DifferentialDrivetrainSim sim =
      new DifferentialDrivetrainSim(
          DCMotor.getCIM(2),
          DriveConstants.GEAR_RATIO,
          DriveConstants.MOMENT_OF_INERTIA_KILOGRAM_SQUARE_METERS,
          DriveConstants.ROBOT_MASS_KILOGRAMS,
          DriveConstants.WHEEL_RADIUS_METERS,
          DriveConstants.TRACK_WIDTH_METERS,
          null);

  private double leftAppliedVolts = 0.0;
  private double rightAppliedVolts = 0.0;

  @Override
  public void updateInputs(DriveIOInputs inputs) {
    sim.update(Constants.LOOP_PERIOD_SECONDS);

    
    Pose2d desejada = sim.getPose();
    Pose2d permitida = arena.corrigir(desejada);
    if (!permitida.equals(desejada)) {
  
  leftOffsetMeters += sim.getLeftPositionMeters();
  rightOffsetMeters += sim.getRightPositionMeters();
  
  sim.setState(VecBuilder.fill(
      permitida.getX(),                        
      permitida.getY(),                        
      permitida.getRotation().getRadians(),    
      0.0,                                     
      0.0,                                     
      0.0,                                     
      0.0));                                   
}
    Logger.recordOutput("Simulacao/PoseReal", sim.getPose());

    inputs.leftConnected = true;
    inputs.rightConnected = true;

    inputs.leftPositionMeters = leftOffsetMeters + sim.getLeftPositionMeters();
    inputs.rightPositionMeters = rightOffsetMeters + sim.getRightPositionMeters();

    inputs.leftVelocityMetersPerSecond = sim.getLeftVelocityMetersPerSecond();
    inputs.rightVelocityMetersPerSecond = sim.getRightVelocityMetersPerSecond();

    inputs.leftAppliedVolts = leftAppliedVolts;
    inputs.rightAppliedVolts = rightAppliedVolts;

    inputs.leftCurrentAmps = sim.getLeftCurrentDrawAmps();
    inputs.rightCurrentAmps = sim.getRightCurrentDrawAmps();

    inputs.gyroYawRadians = sim.getHeading().getRadians();
  }

  @Override
  public void setVoltage(double leftVolts, double rightVolts) {
    leftAppliedVolts = MathUtil.clamp(leftVolts, -DriveConstants.MAX_VOLTS, DriveConstants.MAX_VOLTS);
    rightAppliedVolts =
        MathUtil.clamp(rightVolts, -DriveConstants.MAX_VOLTS, DriveConstants.MAX_VOLTS);
    sim.setInputs(leftAppliedVolts, rightAppliedVolts);
  }

    @Override
  public void resetEncoders() {
    leftOffsetMeters = -sim.getLeftPositionMeters();
    rightOffsetMeters = -sim.getRightPositionMeters();
  }
}
