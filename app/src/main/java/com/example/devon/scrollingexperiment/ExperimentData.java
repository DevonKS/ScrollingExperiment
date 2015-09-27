package com.example.devon.scrollingexperiment;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devon on 23/09/2015.
 */
public class ExperimentData {

    private static int subjectId = -1;
    private static List<Trial> trials = new ArrayList<Trial>();
    private static Trial currentTrial;
    private static List<Trial> orderOfTrials = new ArrayList<Trial>();

    public static void setupTrials() {
        orderOfTrials.add(new Trial(subjectId, ListViewFrictionActivity.class, 50));
        orderOfTrials.add(new Trial(subjectId, ListViewFrictionActivity.class, 100));
        orderOfTrials.add(new Trial(subjectId, ListViewFrictionActivity.class, 500));
        orderOfTrials.add(new Trial(subjectId, ListViewNoFrictionActivity.class, 50));
        orderOfTrials.add(new Trial(subjectId, ListViewNoFrictionActivity.class, 100));
        orderOfTrials.add(new Trial(subjectId, ListViewNoFrictionActivity.class, 500));
        orderOfTrials.add(new Trial(subjectId, DialActivity.class, 50));
        orderOfTrials.add(new Trial(subjectId, DialActivity.class, 100));
        orderOfTrials.add(new Trial(subjectId, DialActivity.class, 500));




        int lowerIndex = subjectId%orderOfTrials.size();
        trials.add(orderOfTrials.get(lowerIndex));
        lowerIndex += 1;
        int upperIndex = orderOfTrials.size() - 1;
        boolean upper = false;

        for (int i = 0; i < orderOfTrials.size() - 1; i += 1) {
            if (upper) {
                trials.add(orderOfTrials.get(upperIndex));
                upperIndex -= 1;
                upper = false;
            } else {
                trials.add(orderOfTrials.get(lowerIndex));
                lowerIndex += 1;
                upper = true;
            }
        }

//        n1, n2, n-1, n3, n-2, n4, n-3, n5, n-4
//
//        1 2 9 3 8 4 7 5 6
//        2 3 1 4 9 5 8 6 7
//        3 4 2 5 1 6 9 7 8
//        4 5 3 6 2 7 1 8 9
//        5 6 4 7 3 8 2 9 1
//        6 7 5 8 4 9 3 1 2
//        7 8 6 9 5 1 4 2 3
//        8 9 7 1 6 2 5 3 4
//        9 1 8 2 7 3 6 4 5
//        6 5 7 4 8 3 9 2 1
//        7 6 8 5 9 4 1 3 2
//        8 7 9 6 1 5 2 4 3
//        9 8 1 7 2 6 3 5 4
    }

    public static int getSubjectId() {
        subjectId = Data.getNextSubjectId();
        trials.clear();
        currentTrial = null;

        return subjectId;
    }

    public static Trial getCurrentTrial() {
        return currentTrial;
    }

    public static Trial getNextTrial() {
        if (trials.isEmpty()) {
            return null;
        }

        Trial next = trials.get(0);
        trials.remove(0);
        currentTrial = next;
        return next;
    }

    public static void log() {
        int subjectId = currentTrial.getSubjectId();
        Class activity = currentTrial.getActivity();
        int distance = currentTrial.getDistance();
        double duration = currentTrial.getDuration();

        String activityString;
        if (activity == ListViewFrictionActivity.class) {
            activityString = "Standard Scrolling With Friction";
        } else if (activity == ListViewNoFrictionActivity.class) {
            activityString = "Standard Scrolling Without Friction";
        }
        else {
            activityString = "Chrial Scrolling";
        }
        String data = subjectId + "," + activityString + "," + distance + "," + duration + "\n";

        String oldData = readFile("data.csv");
        writeFile("data.csv", oldData + data);
    }

    private static void writeFile(String filename, String data) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_RINGTONES), filename);
        FileOutputStream outputStream;

        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFile(String filename) {
        String ret = "";

        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_RINGTONES), filename);

            InputStream inputStream = new FileInputStream(file);

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString;
            StringBuilder stringBuilder = new StringBuilder();

            while ( (receiveString = bufferedReader.readLine()) != null ) {
                stringBuilder.append(receiveString).append("\n");
            }

            inputStream.close();
            ret = stringBuilder.toString();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.toString());
        } catch (IOException e) {
            System.out.println("Can not read file: " + e.toString());
        }

        return ret;
    }
//
//    private static void writeFile(Context context, String filename, String data) {
//        try {
//            File sdCard = Environment.getExternalStorageDirectory();
//            File file = new File (sdCard.getAbsolutePath() + "/ExperimentResults/data.csv");
//
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//
//            BufferedWriter out = new BufferedWriter(new FileWriter(file.getAbsolutePath(), false));
//            out.write(data);
//            out.close();
//        }
//        catch (IOException e) {
//            System.out.println("File write failed: " + e.toString());
//        }
//    }
//
//    /* Checks if external storage is available for read and write */
//    public boolean isExternalStorageWritable() {
//        String state = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED.equals(state)) {
//            return true;
//        }
//        return false;
//    }
//
//    /* Checks if external storage is available to at least read */
//    public boolean isExternalStorageReadable() {
//        String state = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED.equals(state) ||
//                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
//            return true;
//        }
//        return false;
//    }
}
