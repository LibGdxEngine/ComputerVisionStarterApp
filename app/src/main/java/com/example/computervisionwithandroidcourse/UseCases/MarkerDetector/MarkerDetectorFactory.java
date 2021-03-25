package com.example.computervisionwithandroidcourse.UseCases.MarkerDetector;

import com.example.computervisionwithandroidcourse.UseCases.MarkerDetector.MarkerDetectorImplementation.MarkerDetectorForColoredMarker;
import com.example.computervisionwithandroidcourse.UseCases.MarkerDetector.MarkerDetectorImplementation.MarkerDetectorForSquareMarker;

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
