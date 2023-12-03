package com.example.quiz;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileManager {


    String fileName = "CorrectAnswers.txt";
    void  writeCorrectAnswersToFile(Context context, int correctAnswersCount, int numberOfQuestions)  {

        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_APPEND);
            fos.write(String.valueOf(correctAnswersCount + ", " + numberOfQuestions +"\n").getBytes() );

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    void deleteFiles(Context context){

        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write("".getBytes());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    int[] getCorrectAnswers(MainActivity activity) {
        FileInputStream fis = null;
        int totalCorrectAnswers = 0;
        int totalQuestions = 0;
        try {
            fis = activity.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();

            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = reader.readLine();
            }

            // Split content by newline characters
            String[] lines = stringBuilder.toString().split("\n");

            // Parse correct answers and total questions for each line
            for (String content : lines) {
                String[] result = content.split(", ");
                if (result.length == 2) {
                    int correctAnswers = Integer.parseInt(result[0]);
                    totalCorrectAnswers += correctAnswers;
                    int questions = Integer.parseInt(result[1]);
                    totalQuestions += questions;
                } else {
                    // Handle incorrect line format
                    Log.e("FileManager", "Incorrect line format: " + content);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new int[]{totalCorrectAnswers, totalQuestions};
    }
}
