package com.example.quiz;

import android.app.Application;

import java.util.ArrayList;


public class MyApp extends Application {
    private ArrayList<QuestionModel> shuffledQuestions;
    private int correctAnswer;
    private int questionCountApp;
    private int questionIndex;
    private ArrayList<Integer> myColors;

    FileManager fileManager = new FileManager();


    public void setQuestionCountApp(int questionCountApp) {
        this.questionCountApp = questionCountApp;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    public void setCorrectAnswer(int correctAnswer) {
        correctAnswer = correctAnswer;
    }

    public void setMyColors(ArrayList<Integer> myColors) {
        this.myColors = myColors;
    }

    public void setShuffledQuestions(ArrayList<QuestionModel> shuffledQuestions) {
        this.shuffledQuestions = shuffledQuestions;
    }



    public int getQuestionCountApp() {
        return questionCountApp;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }







    public int getQuestionCount() {
        return questionCountApp;
    }





    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public ArrayList<QuestionModel> getShuffledQuestions() {
        return shuffledQuestions;
    }

    public ArrayList<Integer> getMyColors() {
        return myColors;
    }






}
