package frc.robot.subsystems.drive;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import frc.robot.config.RobotConfig;
import frc.robot.config.RobotConfig.DriveHardware;

/**
 * Implementacao da tracao com motores CIM acionados por SPARK MAX.
 *
 * O CIM e um motor escovado: ele nao tem eletronica nem encoder proprios. Quem
 * controla e quem le o encoder e o SPARK MAX, por isso o arquivo leva o nome do
 * CONTROLADOR e nao o do motor. O encoder entra pela porta de dados do SPARK
 * (encoder alternativo).
 *
 * Esta e a UNICA classe da tracao que conhece a REVLib e a unica que converte
 * unidades. Se um import com.revrobotics aparecer em qualquer outro arquivo
 * desta pasta, o molde foi violado.
 */
public class DriveIOSparkMax implements DriveIO {

  private final SparkMax leftLeader;
  private final SparkMax leftFollower;
  private final SparkMax rightLeader;
  private final SparkMax rightFollower;

  private final RelativeEncoder leftEncoder;
  private final RelativeEncoder rightEncoder;

  private final ADXRS450_Gyro gyro = new ADXRS450_Gyro();

  public DriveIOSparkMax() {
    DriveHardware hardware = RobotConfig.getDriveHardware();

    leftLeader = new SparkMax(hardware.leftLeaderCanId(), MotorType.kBrushed);
    leftFollower = new SparkMax(hardware.leftFollowerCanId(), MotorType.kBrushed);
    rightLeader = new SparkMax(hardware.rightLeaderCanId(), MotorType.kBrushed);
    rightFollower = new SparkMax(hardware.rightFollowerCanId(), MotorType.kBrushed);

    // Conversao de unidade configurada no proprio controlador: a partir daqui o
    // SPARK ja devolve metros e metros por segundo.
    SparkMaxConfig leftConfig = new SparkMaxConfig();
    leftConfig
        .inverted(hardware.leftInverted())
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(DriveConstants.CURRENT_LIMIT_AMPS);
    leftConfig
        .alternateEncoder
        .countsPerRevolution((int) hardware.encoderPulsesPerRevolution())
        .positionConversionFactor(DriveConstants.METERS_PER_ENCODER_ROTATION)
        .velocityConversionFactor(DriveConstants.METERS_PER_ENCODER_ROTATION / 60.0);

    SparkMaxConfig rightConfig = new SparkMaxConfig();
    rightConfig.apply(leftConfig).inverted(hardware.rightInverted());

    SparkMaxConfig leftFollowerConfig = new SparkMaxConfig();
    leftFollowerConfig.apply(leftConfig).follow(leftLeader);

    SparkMaxConfig rightFollowerConfig = new SparkMaxConfig();
    rightFollowerConfig.apply(rightConfig).follow(rightLeader);

    leftLeader.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightLeader.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    leftFollower.configure(
        leftFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightFollower.configure(
        rightFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    leftEncoder = leftLeader.getAlternateEncoder();
    rightEncoder = rightLeader.getAlternateEncoder();
  }

  @Override
  public void updateInputs(DriveIOInputs inputs) {
    inputs.leftConnected = leftLeader.getFirmwareVersion() != 0;
    inputs.rightConnected = rightLeader.getFirmwareVersion() != 0;

    inputs.leftPositionMeters = leftEncoder.getPosition();
    inputs.rightPositionMeters = rightEncoder.getPosition();

    inputs.leftVelocityMetersPerSecond = leftEncoder.getVelocity();
    inputs.rightVelocityMetersPerSecond = rightEncoder.getVelocity();

    inputs.leftAppliedVolts = leftLeader.getAppliedOutput() * leftLeader.getBusVoltage();
    inputs.rightAppliedVolts = rightLeader.getAppliedOutput() * rightLeader.getBusVoltage();

    inputs.leftCurrentAmps = leftLeader.getOutputCurrent();
    inputs.rightCurrentAmps = rightLeader.getOutputCurrent();

    inputs.gyroYawRadians = Math.toRadians(-gyro.getAngle());
  }

  @Override
  public void setVoltage(double leftVolts, double rightVolts) {
    leftLeader.setVoltage(leftVolts);
    rightLeader.setVoltage(rightVolts);
  }

  @Override
  public void setBrakeMode(boolean enabled) {
    SparkMaxConfig config = new SparkMaxConfig();
    config.idleMode(enabled ? IdleMode.kBrake : IdleMode.kCoast);
    leftLeader.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    rightLeader.configure(
        config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
  }

  @Override
  public void resetEncoders() {
    leftEncoder.setPosition(0.0);
    rightEncoder.setPosition(0.0);
  }
}
