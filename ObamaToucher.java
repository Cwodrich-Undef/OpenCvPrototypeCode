package org.openftc.easyopencv;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

@Autonomous(name="Racism")
//@Disabled
public class ObamaToucher extends LinearOpMode {
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
        Color1 detecc1 = new Color1(telemetry);
        Color2 detecc2 = new Color2(telemetry);
        Color3 detecc3 = new Color3(telemetry);
        webcam.setPipeline(detecc1);
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
        switch (detecc1.getLocation()){
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
                webcam.setPipeline(detecc2);
                switch (detecc2.getLocation()){
                    case LEFT:

                        break;
                    case MIDDLE:

                        break;
                    case RIGHT:

                        break;
                    case CRING:
                        webcam.setPipeline(detecc3);
                        switch (detecc3.getLocation()){
                            case LEFT:

                                break;
                            case MIDDLE:

                                break;
                            case RIGHT:

                                break;
                            case CRING:

                                break;
                        }
                        break;
                }
                break;
        }
        webcam.stopStreaming();
    }
}
