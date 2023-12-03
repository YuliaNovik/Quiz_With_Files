package com.example.quiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnTrue, btnFalse;
    private ProgressBar prBar;
    private QuestionBank qb;
    private ArrayList<QuestionModel> questionsList;
    private ArrayList<Integer> colorList;
    private int indexQ;
    private int countCorrectAnswer;
    private int questionCount;
    FileManager fm;
    int totalCorrectAnswers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = ((MyApp)getApplication()).fileManager;

        // Initialize UI components
        btnTrue = findViewById(R.id.btnTrue);
        btnFalse = findViewById(R.id.btnFalse);
        prBar = findViewById(R.id.progressBar);

        // Set click listeners for buttons
        btnFalse.setOnClickListener(this);
        btnTrue.setOnClickListener(this);

        // Initialize or restore questions and state
        initializeQuestions();
    }

    // Initialize or restore questions and state
    private void initializeQuestions() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        qb = new QuestionBank();

        if (fragment != null) {
            // If fragment exists, restore state
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            questionsList = ((MyApp) getApplication()).getShuffledQuestions();
            colorList = ((MyApp) getApplication()).getMyColors();
            countCorrectAnswer = ((MyApp) getApplication()).getCorrectAnswer();
        } else {
            // Otherwise, initialize questions and shuffle
            questionsList = qb.getQuizQuestion();
            colorList = qb.getColours();
            shuffleQuestions();
            ((MyApp) getApplication()).setMyColors(colorList);
            ((MyApp) getApplication()).setShuffledQuestions(questionsList);
            indexQ = 0;
            ((MyApp) getApplication()).setCorrectAnswer(indexQ);
            countCorrectAnswer = 0;
            ((MyApp) getApplication()).setCorrectAnswer(countCorrectAnswer);
            questionCount = 0;
            ((MyApp) getApplication()).setCorrectAnswer(questionCount);
        }

        // Get or set the total number of questions
        int qCount = ((MyApp) getApplication()).getQuestionCount();
        questionCount = (qCount == 0) ? questionsList.size() : qCount;

        // Display the first question
        String q = questionsList.get(indexQ).getDescription();
        int c = colorList.get(indexQ);
        QuestionFragment newQFragment = QuestionFragment.newInstance(q, c);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_view, newQFragment).commit();
    }

    // Shuffle the questions and colors
    private void shuffleQuestions() {
        Collections.shuffle(questionsList);
        Collections.shuffle(colorList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTrue:
                // Handle the answer for True button
                handleAnswer(true);
                break;
            case R.id.btnFalse:
                // Handle the answer for False button
                handleAnswer(false);
                break;
        }

        // Move to the next question
        indexQ++;


        // Update the question fragment
        updateQuestionFragment();

        // Calculate progress and update the ProgressBar
        int progress = (indexQ * 100) / questionCount;
        prBar.setProgress(progress);
    }



    // Handle the user's answer
    private void handleAnswer(boolean selectedAnswer) {
        boolean correctAnswer = questionsList.get(indexQ).getAnswer();
        String message = (selectedAnswer == correctAnswer) ? "Correct" : "Incorrect";

        // Log statements for testing
        Log.d("AnswerHandling", "Selected Answer: " + selectedAnswer);
        Log.d("AnswerHandling", "Correct Answer: " + correctAnswer);
        Log.d("AnswerHandling", "Message: " + message);

        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

        // Update the correct answer count if the answer is correct
        if (selectedAnswer == correctAnswer) {
            countCorrectAnswer++;
        }
    }

    // Update the question fragment with the next question
    private void updateQuestionFragment() {
        if (indexQ == questionCount) {
            // If it's the last question, show the results dialog
            showResultsDialog();
            return;
        }
        String resetDesc = questionsList.get(indexQ).getDescription();
        int resetColor = colorList.get(indexQ);

        ((MyApp) getApplication()).setQuestionIndex(indexQ);
        ((MyApp) getApplication()).setCorrectAnswer(countCorrectAnswer);

        // Remove the existing fragment and add a new one with the next question and background color
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            QuestionFragment questionFragment = QuestionFragment.newInstance(resetDesc, resetColor);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_view, questionFragment).commit();
        }
    }
    // This method can be called when the quiz is completed
    private void showResultsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quiz Results");

        // Assuming "countCorrectAnswer" is the variable that holds the user's correct answers count
        builder.setMessage("Your score is: " + countCorrectAnswer + " out of " + questionCount);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Implement the logic to save the results
                fm.writeCorrectAnswersToFile(MainActivity.this, countCorrectAnswer, questionCount);
               //reset
                if (indexQ == questionCount) {
                    shuffleQuestions();
                    indexQ = 0;
                    countCorrectAnswer = 0;
                }
            }
        });

        builder.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  reset;
                if (indexQ == questionCount) {
            shuffleQuestions();
            indexQ = 0;
            countCorrectAnswer = 0;
        }

            }
        });

        builder.show();
    }


 public boolean onCreateOptionsMenu(Menu menu){
     MenuInflater inflater = getMenuInflater();
     inflater.inflate(R.menu.quiz_menu, menu);
return  super.onCreateOptionsMenu(menu);
 }

 public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.average:
                int[] results = fm.getCorrectAnswers(this);
                showAverageResultsDialog(results);
                return true;
            case R.id.numberOfQuestions:
                return true;
            case R.id.reset:
                showResetConfirmationDialog();
                return true;
            default: return  super.onOptionsItemSelected(item);
        }
 }

    private void showResetConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reset Quiz");
        builder.setMessage("Are you sure you want to reset the quiz and erase the results from file?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Reset the quiz
                fm.deleteFiles(MainActivity.this);

                resetQuiz();

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    private void resetQuiz() {
        // Reset necessary variables and update the UI
        shuffleQuestions();
        indexQ = 0;
        countCorrectAnswer = 0;
        updateQuestionFragment();

        // Calculate progress and update the ProgressBar
        int progress = (indexQ * 100) / questionCount;
        prBar.setProgress(progress);
    }

    private void showAverageResultsDialog(int[] results) {
        int totalCorrectAnswers = results[0];
        int totalQuestions = results[1];

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Average Results");

        // Display the correct answers information
        builder.setMessage("Number of Correct Answers: " + totalCorrectAnswers +
                "\nTotal Number of Questions: " + totalQuestions);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when the user clicks OK
            }
        });
        // Show the dialog
        builder.show();
    }
}