package com.example.computervisionwithandroidcourse.UseCases.ImageAnalyser;

import android.graphics.Bitmap;
import android.graphics.Point;
import androidx.camera.core.ImageProxy;

import java.util.ArrayList;
import java.util.List;

public interface MarkerDetector {
     List<Point> markersPoints = new ArrayList<>();
     List<Point> analyseImageAndGetMarkersPoints(Bitmap image);
}
