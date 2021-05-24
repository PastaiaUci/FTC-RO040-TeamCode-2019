import android.sax.TextElementListener;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldDetector;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.opencv.core.Rect;

import com.disnodeteam.dogecv.filters.LeviColorFilter;

@TeleOp(name="TeleOp", group="Linear OpMode")

public class tests extends LinearOpMode {

     public DcMotor Motor_B;
     public DcMotor Motor_FS;
     public DcMotor Motor_SS;
     public DcMotor Motor_FD;
     public DcMotor Motor_SD;
     public DcMotor Motor_L;
     public DcMotor Motor_Ext;
     public CRServo Servo_M;
     public DistanceSensor RANGE3;



    @Override

    public void runOpMode() {
        Motor_B = hardwareMap.dcMotor.get("Motor_B");
        Motor_FS = hardwareMap.dcMotor.get("Motor_FS");
        Motor_SS = hardwareMap.dcMotor.get("Motor_SS");
        Motor_FD = hardwareMap.dcMotor.get("Motor_FD");
        Motor_SD = hardwareMap.dcMotor.get("Motor_SD");
        Motor_L = hardwareMap.dcMotor.get("Motor_L");
        Motor_Ext = hardwareMap.dcMotor.get("Motor_Ext");
        Servo_M = hardwareMap.crservo.get("Servo_M");
        RANGE3 = hardwareMap.get(DistanceSensor.class, "RANGE3");
        Motor_B.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);





        Motor_B.setDirection(DcMotor.Direction.FORWARD);
        Motor_FS.setDirection(DcMotor.Direction.FORWARD);
        Motor_SS.setDirection(DcMotor.Direction.FORWARD);
        Motor_FD.setDirection(DcMotor.Direction.FORWARD);
        Motor_SD.setDirection(DcMotor.Direction.FORWARD);
        Motor_L.setDirection(DcMotor.Direction.FORWARD);
        Motor_Ext.setDirection(DcMotor.Direction.FORWARD);



        waitForStart();


        while(opModeIsActive() && !isStopRequested()){


            telemetry.addData("range", RANGE3.getDistance(DistanceUnit.INCH));
            telemetry.update();
            //mergere fata-spate


                    Motor_FS.setPower(gamepad1.left_stick_y*3/4);
                    Motor_SS.setPower(gamepad1.left_stick_y*3/4);
                    Motor_FD.setPower(-gamepad1.right_stick_y*3/4);
                    Motor_SD.setPower(-gamepad1.right_stick_y*3/4);




               //translatie stanga-dreapta
            while(gamepad1.left_trigger!=0)
            {
                Motor_FS.setPower(-gamepad1.left_trigger);
                Motor_SS.setPower(gamepad1.left_trigger);
                Motor_FD.setPower(-gamepad1.left_trigger);
                Motor_SD.setPower(gamepad1.left_trigger);

            }


            while(gamepad1.right_trigger!=0)
            {
                Motor_FS.setPower(gamepad1.right_trigger);
                Motor_SS.setPower(-gamepad1.right_trigger);
                Motor_FD.setPower(gamepad1.right_trigger);
                Motor_SD.setPower(-gamepad1.right_trigger);

            }

            // mergere lift
              while(gamepad1.right_bumper == true && !isStopRequested()){
                Motor_L.setPower(1);
              }
              Motor_L.setPower(0);
            while(gamepad1.left_bumper == true && !isStopRequested()){
                Motor_L.setPower(-1);
            }
            Motor_L.setPower(0);

            //extindere brat

            Motor_Ext.setPower(gamepad2.right_stick_y);

                 //invartire morcovi
              if(gamepad2.x)
              {
                  Servo_M.setPower(1);
              }
              if(gamepad2.y)
              {
                  Servo_M.setPower(-1);
              }
              if(gamepad2.a)
              {
                  Servo_M.setPower(0);
              }

               //mergere brat

                Motor_B.setPower(gamepad2.left_stick_y);





            }
        }
    }


