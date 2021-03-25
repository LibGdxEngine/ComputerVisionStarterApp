package com.example.computervisionwithandroidcourse.UseCases.MarkerDetector;

import android.graphics.Bitmap;

import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.List;

public interface MarkerDetector {
     List<Point> markersPoints = new ArrayList<>();
     List<Point> analyseImageAndGetMarkersPoints(Bitmap image);
}
