import android.test.MoreAsserts;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.filters.LeviColorFilter;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldDetector;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name = "Crater", group="Pushbot")
public class Autonomie_Crater extends LinearOpMode{


    public DcMotor Motor_SD;
    public DcMotor Motor_FD;
    public DcMotor Motor_FS;
    public DcMotor Motor_SS;
    public DistanceSensor RANGE1 = null;
    public DcMotor Motor_L;
    public DcMotor Motor_Ext;
    public DistanceSensor RANGE2 = null;
    public DistanceSensor RANGE3 = null;
    public CRServo Servo_T = null;
    public long t= System.currentTimeMillis();
    long end = t+15000;
    public DcMotor Motor_B = null;

    //Webcam object
    WebcamName webcamName;

    // DogeCV detector
    GoldDetector detector;

    public void init_CV() {
        telemetry.addData("Status", "DogeCV 2019.1 - Webcam Gold Example");

        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1"); //Retrieves the webcam from the hardware map

        detector = new GoldDetector(); // Create a Gold Detector

        //Sets the Vuforia license key. ALWAYS SET BEFORE INIT!
        detector.VUFORIA_KEY = "AUl37JP/////AAABmdJBNrzS9k2jr0HCMJpvZsF9MS0rop6JvUT++uE1t13gIO/rX532b9grKiYgOBCmd0RsGT63k6ByvGKDhKvaHGVadNplXAjeE3dzPibWibcwCxnMyok/9tFNtB/fPnW+raef9QSRyAmmcxds18iAvWOai8oRivh2yREWUGLdPj31nxO939J++6+6uM8X3KLV4UZrlZ8d8U6LDD5si6LmT+K9Va1XTQyB3e5K78uzKjnQgckQJpH0fAdo+YWiX1FGPjDojGbC88FMAsaWHbZX+BBoMWogsseCk64hbnTQgzpOR7BO0Fg4mr0UncCYC/YCTJKDi+FnD7DiuHtLnVvrLvmUzNDQSe8hW8591l64Ffiu";

        //Inits the detector. Choose which camera to use, and whether to detect VuMarks here
        detector.init(hardwareMap.appContext,CameraViewDisplay.getInstance(), DogeCV.CameraMode.WEBCAM, false, webcamName);

        //Sets basic detector settings
        detector.yellowFilter = new LeviColorFilter(LeviColorFilter.ColorPreset.YELLOW, 85); // Create new filter
        detector.useDefaults(); // Use default settings
        detector.areaScoringMethod = DogeCV.AreaScoringMethod.PERFECT_AREA; // Can also be PERFECT_AREA
       // detector.cropTLCorner = new Point( 1080/2 , 960);
        // detector.cropBRCorner = new Point(1080,0);
        detector.perfectAreaScorer.perfectArea = 1000; // Uncomment if using PERFECT_AREA scoring

        detector.enable();
    }


    public void StopDriving() {
        Motor_FS.setPower(0);
        Motor_SS.setPower(0);
        Motor_FD.setPower(0);
        Motor_SD.setPower(0);
    }


    public void DriveForward(double power, int time) {
        Motor_FS.setPower(power);
        Motor_SS.setPower(power);
        Motor_FD.setPower(-power);
        Motor_SD.setPower(-power);
        sleep(time);
        StopDriving();
    }

    public void DriveRight(double power, int time) {
        Motor_FS.setPower(power);
        Motor_SS.setPower(-power);
        Motor_FD.setPower(power);
        Motor_SD.setPower(-power);
        sleep(time);
        StopDriving();
    }



    public void DriveLeft(double power, int time) {
        Motor_FS.setPower(-power);
        Motor_SS.setPower(power);
        Motor_FD.setPower(-power);
        Motor_SD.setPower(power);
        sleep(time);
        StopDriving();
    }

    public void DriveBackwards(double power, int time) {
        Motor_FS.setPower(-power);
        Motor_SS.setPower(-power);
        Motor_FD.setPower(power);
        Motor_SD.setPower(power);
        sleep(time);
        StopDriving();
    }

    public void SpinLeft(double power, int time) {
        Motor_FS.setPower(-power);
        Motor_SS.setPower(-power);
        Motor_FD.setPower(-power);
        Motor_SD.setPower(-power);
        sleep(time);
        StopDriving();
    }

    public void SpinRight(double power, int time) {
        Motor_FS.setPower(power);
        Motor_SS.setPower(power);
        Motor_FD.setPower(power);
        Motor_SD.setPower(power);
        sleep(time);
        StopDriving();
    }


    @Override

    public void runOpMode(){

        Motor_FS = hardwareMap.dcMotor.get("Motor_FS");
        Motor_SS = hardwareMap.dcMotor.get("Motor_SS");
        Motor_FD = hardwareMap.dcMotor.get("Motor_FD");
        Motor_SD = hardwareMap.dcMotor.get("Motor_SD");
        Motor_L = hardwareMap.dcMotor.get("Motor_L");
        Motor_Ext = hardwareMap.dcMotor.get("Motor_Ext");
        RANGE1 = hardwareMap.get(DistanceSensor.class, "RANGE1");
        RANGE2 = hardwareMap.get(DistanceSensor.class, "RANGE2");
        RANGE3 = hardwareMap.get(DistanceSensor.class, "RANGE3");
        Servo_T = hardwareMap.crservo.get("Servo_T");
        Motor_B = hardwareMap.dcMotor.get("Motor_B");


        Motor_FS.setDirection(DcMotor.Direction.REVERSE);
        Motor_SS.setDirection(DcMotor.Direction.REVERSE);
        Motor_FD.setDirection(DcMotor.Direction.REVERSE);
        Motor_SD.setDirection(DcMotor.Direction.REVERSE);
        Motor_L.setDirection(DcMotor.Direction.REVERSE);
        Motor_Ext.setDirection(DcMotor.Direction.FORWARD);
        Motor_B.setDirection(DcMotor.Direction.REVERSE);

        init_CV();

        waitForStart();

        telemetry.addData("State", "Waiting for start");
        telemetry.update();

        while(!opModeIsActive()&&!isStopRequested()){
            telemetry.addData("Status","Waiting in init");
            telemetry.update();
        }


        telemetry.addData("IsFound: ", detector.isFound());
        Rect rect = detector.getFoundRect();

       while(RANGE1.getDistance(DistanceUnit.INCH) >2.3 && !isStopRequested()){
            Motor_L.setPower(1);
           telemetry.addData("range", String.format("%.01f in", RANGE1.getDistance(DistanceUnit.INCH)));
        }
        Motor_L.setPower(0);

        sleep(500);

       DriveForward(0.4,300);
       DriveLeft(0.5,1200);
       DriveForward(0.4,700);

       sleep(500);

        while(!detector.isFound() && !isStopRequested())
        {
            Motor_FS.setPower(-0.3);
            Motor_SS.setPower(-0.3);
            Motor_FD.setPower(0.3);
            Motor_SD.setPower(0.3);

            if(detector.isFound())
            {
                StopDriving();
                break;
            }

        }
        if(detector.isFound())
        {
            DriveLeft(0.4,850);
        }

        DriveRight(0.5,850);


        while(RANGE2.getDistance(DistanceUnit.INCH) > 11 && !isStopRequested())
        {
            Motor_FS.setPower(-0.4);
            Motor_SS.setPower(-0.4);
            Motor_FD.setPower(0.4);
            Motor_SD.setPower(0.4);
        }

        StopDriving();

        SpinLeft(0.4, 540);

        DriveLeft(0.7, 1000);

        DriveRight(0.3,300);

        while(RANGE2.getDistance(DistanceUnit.INCH)>11 && !isStopRequested()){
            Motor_FS.setPower(-0.4);
            Motor_SS.setPower(-0.4);
            Motor_FD.setPower(0.4);
            Motor_SD.setPower(0.4);
        }
        StopDriving();


        Servo_T.setPower(0.5);
        sleep(850);
        Servo_T.setPower(0);

        DriveForward(0.5, 1750);
        DriveLeft(0.5,1200);
        DriveRight(0.3,300);
        DriveForward(0.35,1750);


        Motor_B.setPower(0.7);
        sleep(600);

               

    }

}
