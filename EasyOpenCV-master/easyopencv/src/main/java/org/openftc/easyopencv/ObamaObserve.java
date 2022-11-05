package org.openftc.easyopencv;

import android.provider.ContactsContract;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

@Autonomous(name="Racism")
//@Disabled
public class ObamaObserve extends LinearOpMode {
    OpenCvWebcam webcam;
    @Override
    public void runOpMode() throws InterruptedException {
        /**
         * sets up the webcam
         */
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "webcam");
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        /**
         * calls the pipeline and sets the detector, basically, goober moment
         */
        TheIranIraqWar detecc = new TheIranIraqWar(telemetry);
        webcam.setPipeline(detecc);
        /**
         * starts the camera working
         */
        webcam.openCameraDeviceAsync( new OpenCvCamera.AsyncCameraOpenListener(){
            @Override
            public void onOpened() {
                webcam.startStreaming(1280,720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        waitForStart();

        /**
         * make robot do thing
         */
        switch (detecc.getLocation()){
            case LEFT:
                /**
                 * What it does when it's on the left
                 */

                break;
            case RIGHT:
                /**
                 * What it does when it's on the right
                 */

                break;
            case MIDDLE:
                /**
                 * What it does when it's in the middle
                 */

                break;
            case CRING:
                /**
                 * What it does when it's being weird
                 */
                
                break;
        }
        webcam.stopStreaming();
    }
}
