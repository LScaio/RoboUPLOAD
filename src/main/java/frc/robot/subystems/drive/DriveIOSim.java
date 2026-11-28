package frc.robot.subystems.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import frc.robot.simulation.ArenaFisica;
import frc.robot.simulation.FieldConstants;

/** Tracao simulada: mesmo contrato (DriveIO) do SPARK MAX, so que com fisica. */
public class DriveIOSim implements DriveIO {

  private final DifferentialDrivetrainSim sim = new DifferentialDrivetrainSim(
      DCMotor.getCIM(2),                       // 2 CIMs de cada lado
      DriveConstants.REDUCTION,
      DriveConstants.MOI_KG_M2,
      DriveConstants.MASS_ROBOt_KG,
      DriveConstants.WHEEL_RADIUS_METERS,
      DriveConstants.TRACK_WIDTH_METERS,
      null);                                   // sem ruido nos sensores

  private final ArenaFisica arena = new ArenaFisica();

  private final StructPublisher<Pose2d> poseRealPub = NetworkTableInstance.getDefault()
      .getStructTopic("Simulacao/PoseReal", Pose2d.struct).publish();

  private double voltsLeft = 0.0;
  private double voltsRight = 0.0;
  private double offsetLeft = 0.0;
  private double offsetRight = 0.0;

  public DriveIOSim() {
    sim.setPose(FieldConstants.ROBOT_START_POSE);
  }

  
  private static double comAtrito(double volts) {
    return Math.signum(volts) * Math.max(Math.abs(volts) - DriveConstants.KS, 0.0);
  }

  @Override
  public void updateInputs(DriveIOInputs inputs) {
    sim.setInputs(comAtrito(voltsLeft), comAtrito(voltsRight));
    sim.update(0.02);

    
    Pose2d desejada = sim.getPose();
    Pose2d permitida = arena.corrigir(desejada);
    if (!permitida.equals(desejada)) {
      offsetLeft += sim.getLeftPositionMeters();
      offsetRight += sim.getRightPositionMeters();
      sim.setState(VecBuilder.fill(
          permitida.getX(), permitida.getY(), permitida.getRotation().getRadians(),
          0.0, 0.0, 0.0, 0.0));
    }
    poseRealPub.set(sim.getPose());

    inputs.positionLeft = offsetLeft + sim.getLeftPositionMeters();
    inputs.positonRight = offsetRight + sim.getRightPositionMeters();
    inputs.velocityLeft = sim.getLeftVelocityMetersPerSecond();
    inputs.velocityRight = sim.getRightVelocityMetersPerSecond();
    inputs.voltsLeft = voltsLeft;
    inputs.voltsRight = voltsRight;
  }

  @Override
  public void setVolts(double voltsLeft, double voltsRight) {
    this.voltsLeft = MathUtil.clamp(voltsLeft, -12.0, 12.0);
    this.voltsRight = MathUtil.clamp(voltsRight, -12.0, 12.0);
  }

  
  public Rotation2d getAnguloReal() {
    return sim.getHeading();
  }
}