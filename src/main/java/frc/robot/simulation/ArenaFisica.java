package frc.robot.simulation;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import java.util.List;

/** Paredes e obstaculos do campo UPLOAD, usados so na simulacao. */
public class ArenaFisica {

  private static final double RAIO_ROBO_METROS = 0.45;

  private record Caixa(String nome, double xMin, double yMin, double xMax, double yMax) {}

  private final List<Caixa> solidos;

  public ArenaFisica() {
    
    Caixa mesaAzul = new Caixa("MESA Azul", 0.0, 2.50, 0.60, 4.50);
    Caixa docaAzul = new Caixa("DOCA Azul", 1.15, 0.0, 2.35, 0.40);

    solidos = List.of(
        mesaAzul, espelhar(mesaAzul, "MESA Vermelha"),
        docaAzul, espelhar(docaAzul, "DOCA Vermelha"));
  }

  private static Caixa espelhar(Caixa c, String nome) {
    double comprimento = FieldConstants.FIELD_LENGTH_METERS;
    return new Caixa(nome, comprimento - c.xMax(), c.yMin(), comprimento - c.xMin(), c.yMax());
  }

  public Pose2d corrigir(Pose2d pose) {
    double x = MathUtil.clamp(pose.getX(), RAIO_ROBO_METROS,
        FieldConstants.FIELD_LENGTH_METERS - RAIO_ROBO_METROS);
    double y = MathUtil.clamp(pose.getY(), RAIO_ROBO_METROS,
        FieldConstants.FIELD_WIDTH_METERS - RAIO_ROBO_METROS);

    for (Caixa c : solidos) {
      double px = MathUtil.clamp(x, c.xMin(), c.xMax());
      double py = MathUtil.clamp(y, c.yMin(), c.yMax());
      double dx = x - px;
      double dy = y - py;
      double d = Math.hypot(dx, dy);

      if (d < RAIO_ROBO_METROS && d > 1e-6) {
        double empurrao = RAIO_ROBO_METROS - d;
        x += dx / d * empurrao;
        y += dy / d * empurrao;
      }
    }
    return new Pose2d(x, y, pose.getRotation());
  }
}