package com.example.devon.scrollingexperiment;

/**
 * Created by Devon on 23/09/2015.
 */
public class Trial {

    private int subjectId;
    private final Class activity;
    private final int distance;
    private double duration = -1;

    public Trial(int subjectId, Class activity, int distance) {
        this.subjectId = subjectId;
        this.activity = activity;
        this.distance = distance;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public Class getActivity() {
        return activity;
    }

    public int getDistance() {
        return distance;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        if (this.duration < 0) {
            this.duration = duration;
        }
    }
}
