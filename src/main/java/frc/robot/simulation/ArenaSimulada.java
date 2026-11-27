package frc.robot.simulation;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import frc.robot.FieldConstants;
import java.util.ArrayList;
import java.util.List;
import org.littletonrobotics.junction.Logger;


public class ArenaSimulada {

  private static final double ALTURA_BLOCO_METROS = 0.127;

  private final List<Pose3d> blocos = new ArrayList<>();

  public ArenaSimulada() {
    
    noChao(FieldConstants.FIELD_LENGTH_METERS / 2, 2.0, 0.0);
  }

  private void noChao(double x, double y, double yaw) {
    blocos.add(new Pose3d(x, y, ALTURA_BLOCO_METROS / 2, new Rotation3d(0, 0, yaw)));
  }

  public void atualizar() {
    Logger.recordOutput("Simulacao/Blocos", blocos.toArray(new Pose3d[0]));
  }
}