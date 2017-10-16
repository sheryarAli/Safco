package customclasses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shery on 12/22/2016.
 */

public class Questionnaire {
    private static final String TAG = Questionnaire.class.getSimpleName();
    public static final String QUESTIONNAIRE_TABLE = "microfinance_customers_questionnaire";
    public static final String KEY_QUESTIONNAIRE_ID = "QuestionnaireId";
    public static final String KEY_QUESTION = "Question";
    public static final String KEY_ANSWER_TYPE = "AnswerType";
    public static final String KEY_ANSWER1 = "Answer1";
    public static final String KEY_ANSWER2 = "Answer2";
    public static final String KEY_ANSWER3 = "Answer3";
    public static final String KEY_ANSWER4 = "Answer4";
    public static final String KEY_ANSWER5 = "Answer5";
    public static final String KEY_ANSWER6 = "Answer6";
    public static final String KEY_ANSWER7 = "Answer7";
    public static final String KEY_ANSWER8 = "Answer8";
    public static final String KEY_ANSWER9 = "Answer9";
    public static final String KEY_ANSWER10 = "Answer10";
    public static final String KEY_STATUS = "Status";


    @SerializedName("QuestionnaireId")
    private int QuestionnaireId;
    private int Status;
    @SerializedName("AnswerType")
    private int AnswerType;

    @SerializedName("Question")
    private String Question;
    @SerializedName("Answer1")
    private String Answer1;
    @SerializedName("Answer2")
    private String Answer2;
    @SerializedName("Answer3")
    private String Answer3;
    @SerializedName("Answer4")
    private String Answer4;
    @SerializedName("Answer5")
    private String Answer5;
    @SerializedName("Answer6")
    private String Answer6;
    @SerializedName("Answer7")
    private String Answer7;
    @SerializedName("Answer8")
    private String Answer8;
    @SerializedName("Answer9")
    private String Answer9;
    @SerializedName("Answer10")
    private String Answer10;
    private String selectedAnswer;

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public int getAnswerType() {
        return AnswerType;
    }

    public void setAnswerType(int answerType) {
        AnswerType = answerType;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public int getQuestionnaireId() {
        return QuestionnaireId;
    }

    public void setQuestionnaireId(int questionnaireId) {
        QuestionnaireId = questionnaireId;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getAnswer1() {
        return Answer1;
    }

    public void setAnswer1(String answer1) {
        Answer1 = answer1;
    }

    public String getAnswer2() {
        return Answer2;
    }

    public void setAnswer2(String answer2) {
        Answer2 = answer2;
    }

    public String getAnswer3() {
        return Answer3;
    }

    public void setAnswer3(String answer3) {
        Answer3 = answer3;
    }

    public String getAnswer4() {
        return Answer4;
    }

    public void setAnswer4(String answer4) {
        Answer4 = answer4;
    }

    public String getAnswer5() {
        return Answer5;
    }

    public void setAnswer5(String answer5) {
        Answer5 = answer5;
    }

    public String getAnswer6() {
        return Answer6;
    }

    public void setAnswer6(String answer6) {
        Answer6 = answer6;
    }

    public String getAnswer7() {
        return Answer7;
    }

    public void setAnswer7(String answer7) {
        Answer7 = answer7;
    }

    public String getAnswer8() {
        return Answer8;
    }

    public void setAnswer8(String answer8) {
        Answer8 = answer8;
    }

    public String getAnswer9() {
        return Answer9;
    }

    public void setAnswer9(String answer9) {
        Answer9 = answer9;
    }

    public String getAnswer10() {
        return Answer10;
    }


    public void setAnswer10(String answer10) {
        Answer10 = answer10;
    }

    public static final String createQuestionnaireTable = "CREATE TABLE IF NOT EXISTS microfinance_customers_questionnaire (" + KEY_QUESTIONNAIRE_ID + " integer primary key autoincrement," +
            " " + KEY_QUESTION + " text not null, " + KEY_ANSWER_TYPE + " tinyint(1) not null default '0', " +
            "" + KEY_ANSWER1 + " text," +
            "" + KEY_ANSWER2 + " text," +
            "" + KEY_ANSWER3 + " text," +
            "" + KEY_ANSWER4 + " text," +
            "" + KEY_ANSWER5 + " text," +
            "" + KEY_ANSWER6 + " text," +
            "" + KEY_ANSWER7 + " text," +
            "" + KEY_ANSWER8 + " text," +
            "" + KEY_ANSWER9 + " text," +
            "" + KEY_ANSWER10 + " text," +
            "" + OrganizationEmployees.KEY_STATUS + " tinyint(1) not null default '0'" +
            ")";

}
