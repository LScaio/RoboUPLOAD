

package frc.robot;


public final class Constants {
  

  public static class ElevatorConstants {
    public static final int kLeftMotor = 1; //Alterar quando a eletrica confirmar o ID
    public static final int kRightMotor = 2; //Alterar quando a eletrica confirmar o ID

    public static final double kReduction = 16.0;

    public static final double kSprocket = 22.0;
    public static final double kPitchChain = 0.009525;

    public static final double kStages = 2.0;

    public static final double kOffset = 0.157;
    public static final double kMaximumHeight = 1.12;

    public static final double kTolerance = 0.04;
    
    public static final double kTableTarget = 0.79;
    public static final double kRetreatTableTarget = 1.12;

    public static final double kDockTarget = 0.34;
    public static final double kRetreatDockTarget = 0.70;

    public static final double kMetersAroundMotor = kSprocket * kPitchChain / kReduction * kStages;

  }
}
