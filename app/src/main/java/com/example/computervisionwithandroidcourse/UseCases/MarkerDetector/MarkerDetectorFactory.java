package com.example.computervisionwithandroidcourse.UseCases.ImageAnalyser;

import com.example.computervisionwithandroidcourse.UseCases.ImageAnalyser.ImageAnalyserImaplementations.MarkerDetectorForColoredMarker;
import com.example.computervisionwithandroidcourse.UseCases.ImageAnalyser.ImageAnalyserImaplementations.MarkerDetectorForSquareMarker;

public class MarkerDetectorFactory {
    public static MarkerDetector getImageAnalyser(MarkerDetectorType analyserType){
        if(analyserType == MarkerDetectorType.DETECTOR_FOR_COLORED_MARKER){
            return new MarkerDetectorForColoredMarker();
        }else if(analyserType == MarkerDetectorType.DETECTOR_FOR_SQUARE_MARKER){
            return new MarkerDetectorForSquareMarker();
        }
        return null;
    }
}
