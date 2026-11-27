package frc.robot.subsystems.drive;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

/**
 * Comandos compostos da tracao: os que sao longos demais para caber dentro de
 * Drive, mas que ainda usam SO este mecanismo.
 *
 * Comandos que coordenam mais de um mecanismo nao moram aqui: vao para o pacote
 * robot/commands.
 */
public final class DriveCommands {

  private DriveCommands() {}

  /**
   * Anda em linha reta uma distancia definida, devagar, e para no fim.
   *
   * Repare que nenhum numero aparece solto: distancia, velocidade e tolerancia
   * vem de DriveConstants, que e o lugar unico de ajuste.
   */
  public static Command driveDistance(Drive drive, double distanceMeters) {
    return Commands.sequence(
            Commands.runOnce(drive::resetEncoders, drive),
            Commands.run(
                    () ->
                        drive.driveVelocity(
                            DriveConstants.SLOW_SPEED_METERS_PER_SECOND,
                            DriveConstants.SLOW_SPEED_METERS_PER_SECOND),
                    drive)
                .until(
                    () ->
                        Math.abs(drive.getAveragePositionMeters())
                            >= distanceMeters - DriveConstants.POSITION_TOLERANCE_METERS),
            Commands.runOnce(drive::stop, drive))
        .withName("DriveDistance");
  }

  /** Ajuste fino: um passo curto para a frente, util para alinhar o robo. */
  public static Command nudgeForward(Drive drive) {
    return driveDistance(drive, DriveConstants.NUDGE_DISTANCE_METERS).withName("NudgeForward");
  }
}
