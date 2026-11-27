package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

/**
 * Dimensoes e pontos do campo da temporada.
 *
 * Mora no codigo do ano (robot/) porque o campo muda todo mes de janeiro.
 * Nada daqui pode ser movido para lib/.
 */
public final class FieldConstants {

  private FieldConstants() {}

  public static final double FIELD_LENGTH_METERS = 7.0;
  public static final double FIELD_WIDTH_METERS = 7.0;

  public static final Pose2d ROBOT_START_POSE = new Pose2d(1.5, 3.5, new Rotation2d());

  // Pontos de interesse do jogo entram aqui, sempre em metros e radianos.
}
