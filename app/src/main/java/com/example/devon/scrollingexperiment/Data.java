package com.example.devon.scrollingexperiment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devon on 23/09/2015.
 */
public class Data {

    public static int currentSubjectId = 1;

    public static int getNextSubjectId() {
        int nextSubjectId = currentSubjectId;
        currentSubjectId += 1;
        return nextSubjectId;
    }
}
