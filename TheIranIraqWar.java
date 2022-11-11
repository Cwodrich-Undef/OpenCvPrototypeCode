package org.openftc.easyopencv;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

class TheIranIraqWar extends OpenCvPipeline {
    Telemetry telemetry;
    Mat mat = new Mat();
    /**
     * variable as to the position of the detected object color
     */
    public enum Location {
        LEFT,
        RIGHT,
        MIDDLE,
        CRING
    }
    private Location location;

    /**
     * sets the perameters for the 3 different regions
     */
    static final Rect LEFT = new Rect(
            new Point(300,600),
            new Point(700,440)
    );
    static final Rect MIDDLE = new Rect(
            new Point(300,440),
            new Point(700,300)
    );
    static final Rect RIGHT = new Rect(
            new Point(300,300),
            new Point(700,200)
    );

    /**
     * Percentage of color that the object has
     */
    double PERCENT_COLOR_THRESHOLD = .5;

    TheIranIraqWar(Telemetry t){telemetry = t;}


    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        /**
         * The range of color that you want to detect
         */
        Scalar lowHSV = new Scalar(25, 15, 15);
        Scalar highHSV = new Scalar(34, 23, 25);

        Core.inRange(mat, lowHSV, highHSV, mat);
        /**
         * connects the regions defined earlier to the main image
         * A submat is just a subsection of the original mat image
         */
        Mat left = mat.submat(LEFT);
        Mat right = mat.submat(RIGHT);
        Mat middle = mat.submat(MIDDLE);

        double leftValue = Core.sumElems(left).val[0] / LEFT.area() / 255;
        double rightValue = Core.sumElems(right).val[0] / RIGHT.area() / 255;
        double middleValue = Core.sumElems(middle).val[0] / RIGHT.area() / 255;

        left.release();
        right.release();
        middle.release();

        telemetry.addData("Left raw value", (int)Core.sumElems(left).val[0]);
        telemetry.addData("Right raw value", (int)Core.sumElems(right).val[0]);
        telemetry.addData("Middle raw value", (int)Core.sumElems(middle).val[0]);
        telemetry.addData("Left Percentage", Math.round(leftValue * 100) + "%");
        telemetry.addData("Right Percentage", Math.round(rightValue * 100) + "%");
        telemetry.addData("Middle Percentage", Math.round(middleValue * 100) + "%");

        /**
         * Sets a boolean (true/false statement) as to where the object is, left, middle or right
         */

        boolean coneLeft = leftValue > PERCENT_COLOR_THRESHOLD;
        boolean coneRight = rightValue > PERCENT_COLOR_THRESHOLD;
        boolean coneMiddle = middleValue > PERCENT_COLOR_THRESHOLD;

        /**
         * checks location of the object
         */
        if(coneRight && coneLeft) {
            location = Location.CRING;
            telemetry.addData("Cone Number:", "Not Found, you are a bozo");
            //bad, no cringe
        }else if(coneLeft){
            location = Location.LEFT;
            telemetry.addData("Cone Number:", "1");
            //cone left
        }else if(coneLeft && coneMiddle){
            location = Location.RIGHT;
            telemetry.addData("Cone Number:", "2");
            //cone right
        }else {
            location = Location.MIDDLE;
            telemetry.addData("Cone Number", "3");
            //cone middle
        }
        telemetry.update();

        /**
         * Changes the color of the image from greyscale to rgb
         */
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);

        /**
         * in order to make sure no other objects are detected which may be confused
         */
        Scalar colorBlueCone = new Scalar(0,0,255);
        Scalar colorRedCone = new Scalar(255,0,0);

        /**
         * idk bro, more stuff with the defined parameters again, you just need this
         */
        Imgproc.rectangle(mat, LEFT, location == Location.LEFT? colorBlueCone:colorRedCone);
        Imgproc.rectangle(mat, RIGHT, location == Location.RIGHT? colorBlueCone:colorRedCone);
        Imgproc.rectangle(mat, MIDDLE, location == Location.MIDDLE? colorBlueCone:colorRedCone);

        /**
         * outputs the cool stuff we just did! wooooooo! I am rapidly losing sanity!!!!
         */
        return mat;
    }

    public Location getLocation(){
        return location;
    }
}
