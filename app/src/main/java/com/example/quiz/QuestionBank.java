package com.example.quiz;

import java.util.ArrayList;

public class QuestionBank extends QuestionModel{
    private ArrayList<QuestionModel> quizQuestion = new ArrayList<>();
    private ArrayList<Integer> colours = new ArrayList<>();

    public  ArrayList<Integer> getColours(){
        colours.add(R.color.violet);
        colours.add(R.color.babyBlue);
        colours.add(R.color.yellow);
        colours.add(R.color.turquoise);
        colours.add(R.color.coral);
        colours.add(R.color.green);
        colours.add(R.color.red);
        colours.add(R.color.pink);
        colours.add(R.color.lavender);
        colours.add(R.color.fuchsia);
        return colours;
    }


    public QuestionBank() {

    }

    public void QuestionBank(ArrayList<QuestionModel> quizQuestion) {}

    public void setQuizQuestion(ArrayList<QuestionModel> quizQuestion) {
        this.quizQuestion = quizQuestion;
    }

    public ArrayList<QuestionModel> getQuizQuestion() {
        quizQuestion.add(new QuestionModel(1, true, "Enumerations are a special data type in Java that allows for a variable to be set to a predefined variable."));
        quizQuestion.add(new QuestionModel(2, false, "Garbage Collection is manual process."));
        quizQuestion.add(new QuestionModel(2, false, "Assignment operator is evaluated Left to Right."));
        quizQuestion.add(new QuestionModel(2, false, " Java programming is not statically-typed, means all variables should not first be declared before they can be used."));
        quizQuestion.add(new QuestionModel(2, true, "A .class file contains bytecodes"));
        quizQuestion.add(new QuestionModel(2, false, "Interfaces can be instantiated."));
        quizQuestion.add(new QuestionModel(2, false, "Declarations must appear at the start of the body of a Java method."));
        quizQuestion.add(new QuestionModel(2, false, "The \"switch\" selection structure must end with the default case."));
        quizQuestion.add(new QuestionModel(2, true, "An individual array element from an array of type int, when passed to a method is passed by value."));
        quizQuestion.add(new QuestionModel(2, true, "Objects of a subclass can be assigned to a super class reference."));


        return quizQuestion;
    }

    public QuestionBank(ArrayList<QuestionModel> quizQuestion) {
        this.quizQuestion = quizQuestion;
    }

    public QuestionBank(int color, boolean answer, String description, ArrayList<QuestionModel> quizQuestion) {
        super(color, answer, description);
        this.quizQuestion = quizQuestion;
    }


}
