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

    public static void setupTrials() {
        trials.add(new Trial(subjectId, ListViewFrictionActivity.class, 50));
        trials.add(new Trial(subjectId, ListViewNoFrictionActivity.class, 50));
        trials.add(new Trial(subjectId, DialActivity.class, 50));
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

    public static void log(Context context) {
        int subjectId = currentTrial.getSubjectId();
        Class activity = currentTrial.getActivity();
        int distance = currentTrial.getDistance();
        double duration = currentTrial.getDuration();
        String data = subjectId + "," + activity + "," + distance + "," + duration + "\n";

        String oldData = readFile(context, "data.csv");
        writeFile(context, "data.csv", oldData + data);
    }

    private static void writeFile(Context context, String filename, String data) {
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

    private static String readFile(Context context, String filename) {
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
