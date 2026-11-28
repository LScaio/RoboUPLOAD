package frc.robot.subystems.drive;

import edu.wpi.first.math.geometry.Rotation2d;


public class GyroIOSim implements GyroIO {

  private final DriveIOSim driveSim;
  private double zeroGraus = 0.0;
  private double anteriorGraus = 0.0;

  public GyroIOSim(DriveIOSim driveSim) {
    this.driveSim = driveSim;
  }

  @Override
  public void updateInputs(GyroIOInputs inputs) {
    
    double graus = driveSim.getAnguloReal().getDegrees();
    inputs.conectado = true;
    inputs.angulo = Rotation2d.fromDegrees(graus - zeroGraus);
    inputs.velocityGrausPorSeg = (graus - anteriorGraus) / 0.02;
    anteriorGraus = graus;
  }

  @Override
  public void zerar() {
    zeroGraus = driveSim.getAnguloReal().getDegrees();
  }
}
