package com.example.computervisionwithandroidcourse.UseCases.ImageAnalyser.ImageAnalyserImaplementations;

import android.graphics.Bitmap;
import android.graphics.Point;

import com.example.computervisionwithandroidcourse.UseCases.ImageAnalyser.MarkerDetector;

import java.util.List;

public class MarkerDetectorForColoredMarker implements MarkerDetector {

    @Override
    public List<Point> analyseImageAndGetMarkersPoints(Bitmap image) {
        markersPoints.add(new Point(0 , 0));
        return markersPoints;
    }
}
