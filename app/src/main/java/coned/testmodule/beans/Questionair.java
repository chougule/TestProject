package coned.testmodule.beans;

import java.util.ArrayList;

/**
 * Created by Deepak on 11-Dec-17.
 */

public class Questionair {

    private String Question_id="";
    private String Question="";
    private ArrayList<String> Options;
    private String CorrectAnswer ="";
    private String AnswerDescription="";
    private int Answer = 0;
    private long TimeTaken =0;

    public long getTimeTaken() {
        return TimeTaken;
    }

    public void setTimeTaken(long timeTaken) {
        TimeTaken = timeTaken;
    }

    public String getQuestion_id() {
        return Question_id;
    }

    public int getAnswer() {
        return Answer;
    }

    public void setAnswer(int answer) {
        Answer = answer;
    }

    public void setQuestion_id(String question_id) {
        Question_id = question_id;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public ArrayList<String> getOptions() {
        return Options;
    }

    public void setOptions(ArrayList<String> options) {
        Options = options;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public String getAnswerDescription() {
        return AnswerDescription;
    }

    public void setAnswerDescription(String answerDescription) {
        AnswerDescription = answerDescription;
    }
}
