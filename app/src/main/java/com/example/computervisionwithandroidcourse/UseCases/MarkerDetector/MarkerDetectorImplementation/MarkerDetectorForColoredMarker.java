package com.example.computervisionwithandroidcourse.UseCases.MarkerDetector.MarkerDetectorImplementation;

import android.graphics.Bitmap;
import com.example.computervisionwithandroidcourse.UseCases.MarkerDetector.MarkerDetector;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import java.util.List;

public class MarkerDetectorForColoredMarker implements MarkerDetector {

    @Override
    public List<Point> analyseImageAndGetMarkersPoints(Bitmap image) {
        return markersPoints;
    }
}
