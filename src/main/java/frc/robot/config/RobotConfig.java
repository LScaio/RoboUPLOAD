package frc.robot.config;

import frc.robot.Constants;

/**
 * Entrega os valores de hardware que mudam de um robo para outro.
 *
 * Nenhum subsistema sabe em qual robo esta rodando: ele pergunta aqui e recebe
 * os numeros certos. E o que evita alguem editar constantes para testar em
 * outro chassi e esquecer de desfazer antes da competicao.
 */
public final class RobotConfig {

  private RobotConfig() {}

  /**
   * Hardware da tracao que varia por robo.
   *
   * Geometria e ganhos NAO entram aqui: eles sao do mecanismo e moram em
   * DriveConstants. Aqui fica so o que muda quando o robo muda.
   */
  public record DriveHardware(
      int leftLeaderCanId,
      int leftFollowerCanId,
      int rightLeaderCanId,
      int rightFollowerCanId,
      boolean leftInverted,
      boolean rightInverted,
      double encoderPulsesPerRevolution) {}

  public static DriveHardware getDriveHardware() {
    return switch (Constants.ROBOT_TYPE) {
      case COMPETITION ->
          new DriveHardware(1, 2, 3, 4, false, true, 4096.0);
    };
  }
}
