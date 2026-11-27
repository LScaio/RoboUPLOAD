package frc.robot.subsystems.drive;

import org.littletonrobotics.junction.AutoLog;

/**
 * Contrato entre a logica da tracao e o hardware.
 *
 * Aqui nao existe classe de fabricante, nao existe numero e nao existe conta.
 * Este arquivo continua igual em 2027 ou em 2030; o que muda sao as
 * implementacoes (DriveIOSparkMax, DriveIOSim).
 */
public interface DriveIO {

  /**
   * Tudo que a tracao mede.
   *
   * A anotacao @AutoLog faz o AdvantageKit gravar cada campo no log. O que nao
   * estiver aqui nao existe no replay: por isso toda leitura de sensor passa
   * por esta estrutura, e nunca direto no meio da logica.
   */
  @AutoLog
  class DriveIOInputs {
    public boolean leftConnected = false;
    public boolean rightConnected = false;

    public double leftPositionMeters = 0.0;
    public double rightPositionMeters = 0.0;

    public double leftVelocityMetersPerSecond = 0.0;
    public double rightVelocityMetersPerSecond = 0.0;

    public double leftAppliedVolts = 0.0;
    public double rightAppliedVolts = 0.0;

    public double leftCurrentAmps = 0.0;
    public double rightCurrentAmps = 0.0;

    public double gyroYawRadians = 0.0;
  }

  /** Le o hardware e preenche as entradas. Chamado uma vez por ciclo. */
  default void updateInputs(DriveIOInputs inputs) {}

  /** Aplica tensao em cada lado, em volts. */
  default void setVoltage(double leftVolts, double rightVolts) {}

  /** Liga ou desliga o freio ao soltar o comando. */
  default void setBrakeMode(boolean enabled) {}

  /** Zera a leitura de posicao dos encoders. */
  default void resetEncoders() {}
}
