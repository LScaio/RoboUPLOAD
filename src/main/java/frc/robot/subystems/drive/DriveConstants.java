package frc.robot.subystems.drive;

public class DriveConstants {

    public final static int ID_RIGHT_FRONT = 7;
    public final static int ID_RIGHT_BACK = 5;
    
    public final static int ID_LEFT_FRONT = 6;
    public final static int ID_LEFT_BACK = 8;

    public final static int ID_PIGEON = 22;

    //-----------------------------------------------------------------------------

    public final static double VELOCIDADE_NORMAL = 0.7;
    public final static double VELOCIDADE_TURBO = 0.9;

    //------------------------------------------------------------------------------

    public final static double P = 1.5;
    public final static double I = 0;
    public final static double D = 0.05;

    //----------------------------------------------------------------------------

    public final static double KV = 3.39;
    public final static double KS = 0.82;

    public final static int COMPENSATION_VOLTAGE = 12;

    //-------------------------------------------------------------------------------

    public final static double CONVERSAO_METROS_POR_SEG = Math.PI * 0.1524 / 60;
    public final static double CONVERSAO_METROS = Math.PI * 0.1524;

    //--------------------------------------------------------------------------------

    public final static double MOUNTPOSE_YAW = -90.61016845703125;
    public final static double MOUNTPOSE_ROLL = -0.3616531789302826;
    public final static double MOUNTPOSEPITCH = -0.9369150996208191;

    //--------------------------------------------------------------------------------

    public final static double MASS_ROBOt_KG = 36.0;
    public final static double REDUCTION = 10.71;
    public final static double WHEEL_RADIUS_METERS = 0.1524 / 2.0;
    public final static double TRACK_WIDTH_METERS = 0.60;
    public final static double MOI_KG_M2 = MASS_ROBOt_KG * (0.70 * 0.70 + 0.60 * 0.60) / 12.0;
}
