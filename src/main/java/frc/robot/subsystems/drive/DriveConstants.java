package frc.robot.subsystems.drive;

/**
 * Numeros da tracao.
 *
 * Regras do padrao aplicadas aqui:
 *  - tudo em SI (metros, radianos, segundos);
 *  - a unidade vai sempre no fim do nome;
 *  - nenhum numero solto nos comandos: se um comando precisa de um valor,
 *    ele vem daqui com nome proprio.
 *
 * O que muda de um robo para outro (IDs de CAN, inversoes) NAO esta aqui:
 * esta em RobotConfig.
 */
public final class DriveConstants {

  private DriveConstants() {}

  // --- Geometria -----------------------------------------------------------

  /** Roda de 6 polegadas. */
  public static final double WHEEL_RADIUS_METERS = 0.0762;

  /** Distancia entre o centro das rodas esquerda e direita. */
  public static final double TRACK_WIDTH_METERS = 0.58;

  /** Reducao da caixa (ex.: Toughbox Mini 10.71:1). */
  public static final double GEAR_RATIO = 10.71;

  /**
   * Metros percorridos por volta do encoder.
   *
   * Assume o encoder no eixo de saida da caixa. Se o encoder estiver no eixo
   * do motor, divida tambem por GEAR_RATIO.
   */
  public static final double METERS_PER_ENCODER_ROTATION = 2.0 * Math.PI * WHEEL_RADIUS_METERS;

  // --- Limites -------------------------------------------------------------

  public static final double MAX_SPEED_METERS_PER_SECOND = 3.5;
  public static final int CURRENT_LIMIT_AMPS = 40;
  public static final double MAX_VOLTS = 12.0;

  // --- Ganhos --------------------------------------------------------------

  public static final double FEEDFORWARD_KS_VOLTS = 0.15;
  public static final double FEEDFORWARD_KV_VOLTS_PER_METER_PER_SECOND = 2.60;
  public static final double VELOCITY_KP = 0.0;
  public static final double VELOCITY_KD = 0.0;

  // --- Valores usados pelos comandos ---------------------------------------

  public static final double SLOW_SPEED_METERS_PER_SECOND = 1.0;
  public static final double NUDGE_DISTANCE_METERS = 0.25;
  public static final double POSITION_TOLERANCE_METERS = 0.03;

  // --- Massa e inercia, usados apenas pela simulacao -----------------------

  public static final double ROBOT_MASS_KILOGRAMS = 60.0;
  public static final double MOMENT_OF_INERTIA_KILOGRAM_SQUARE_METERS = 7.5;

  public static final double JOYSTICK_DEADBAND = 0.1;
}
