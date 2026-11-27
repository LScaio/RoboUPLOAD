package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;
import frc.robot.config.RobotType;

/**
 * Constantes globais do projeto.
 *
 * Regra do padrao: aqui entra SOMENTE o que vale para o robo inteiro.
 * Numeros de um mecanismo moram no XxxConstants da pasta daquele mecanismo.
 * Se este arquivo passar de umas 40 linhas uteis, algo esta no lugar errado.
 */
public final class Constants {

  private Constants() {}

  /** Robo fisico desta temporada. Um chassi novo entra como novo valor no enum. */
  public static final RobotType ROBOT_TYPE = RobotType.COMPETITION;

  /** Periodo do loop principal da WPILib, em segundos. */
  public static final double LOOP_PERIOD_SECONDS = 0.02;

  public static final int TEAM_NUMBER = 10466;

  /**
   * Modo em que o codigo esta rodando.
   *
   * REAL   - no robo.
   * SIM    - no computador, com os mecanismos simulados.
   * REPLAY - reprocessando um log gravado, para analisar a partida depois.
   */
  public enum Mode {
    REAL,
    SIM,
    REPLAY
  }

  /** Trocar para Mode.REPLAY quando for reprocessar um log no computador. */
  private static final Mode SIMULATION_MODE = Mode.SIM;

  public static Mode getMode() {
    if (RobotBase.isReal()) {
      return Mode.REAL;
    }
    return SIMULATION_MODE;
  }
}
