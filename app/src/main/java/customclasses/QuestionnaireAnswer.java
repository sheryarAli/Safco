package customclasses;

/**
 * Created by shery on 12/22/2016.
 */

public class QuestionnaireAnswer {
    private static final String TAG = Questionnaire.class.getSimpleName();
    public static final String QUESTIONNARE_ANSWER_TABLE = "microfinance_customersloan_questionnaire_answers";
    public static final String KEY_QUESTIONNARE_FORM_ID = "QuestionnaireFormId";
    public static final String KEY_ANSWER11 = "Answer11";
    public static final String KEY_ANSWER12 = "Answer12";
    public static final String KEY_ANSWER13 = "Answer13";

    private int QuestionnaireFormId, CustomerId, Loan_Id;
    private String Answer1, Answer2, Answer3, Answer4, Answer5,
            Answer6, Answer7, Answer8, Answer9, Answer10, Answer11,
            Answer12, Answer13, NICNumber;

    public String getNICNumber() {
        return NICNumber;
    }

    public void setNICNumber(String NICNumber) {
        this.NICNumber = NICNumber;
    }

    public int getQuestionnaireFormId() {
        return QuestionnaireFormId;
    }

    public void setQuestionnaireFormId(int questionnaireFormId) {
        QuestionnaireFormId = questionnaireFormId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public int getLoan_Id() {
        return Loan_Id;
    }

    public void setLoan_Id(int loan_Id) {
        Loan_Id = loan_Id;
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

    public String getAnswer11() {
        return Answer11;
    }

    public void setAnswer11(String answer11) {
        Answer11 = answer11;
    }

    public String getAnswer12() {
        return Answer12;
    }

    public void setAnswer12(String answer12) {
        Answer12 = answer12;
    }

    public String getAnswer13() {
        return Answer13;
    }

    public void setAnswer13(String answer13) {
        Answer13 = answer13;
    }

    public static final String createQuestionnaireAnswerTable = "CREATE TABLE IF NOT EXISTS " + QUESTIONNARE_ANSWER_TABLE + " " +
            "( " + KEY_QUESTIONNARE_FORM_ID + " integer primary key AUTOINCREMENT, " +
            "  " + MicrofinanceCustomers.KEY_CUSTOMER_ID + " int(11) NOT NULL, " +
            "  " + MicrofinanceCustomers.KEY_LOAN_ID + " int(11) NOT NULL DEFAULT '0', " +
            "  " + Questionnaire.KEY_ANSWER1 + " varchar(255) DEFAULT NULL, " +
            "  " + Questionnaire.KEY_ANSWER2 + " varchar(255) DEFAULT NULL, " +
            "  " + Questionnaire.KEY_ANSWER3 + " varchar(255) DEFAULT NULL, " +
            "  " + Questionnaire.KEY_ANSWER4 + " varchar(255) DEFAULT NULL, " +
            "  " + Questionnaire.KEY_ANSWER5 + " varchar(255) DEFAULT NULL, " +
            "  " + Questionnaire.KEY_ANSWER6 + " varchar(255) DEFAULT NULL, " +
            "  " + Questionnaire.KEY_ANSWER7 + " varchar(255) DEFAULT NULL, " +
            "  " + Questionnaire.KEY_ANSWER8 + " varchar(255) DEFAULT NULL, " +
            "  " + Questionnaire.KEY_ANSWER9 + " varchar(255) DEFAULT NULL, " +
            "  " + Questionnaire.KEY_ANSWER10 + " varchar(255) DEFAULT NULL, " +
            "  " + KEY_ANSWER11 + " varchar(255) DEFAULT NULL, " +
            "  " + KEY_ANSWER12 + " varchar(255) DEFAULT NULL, " +
            "  " + KEY_ANSWER13 + " varchar(255) DEFAULT NULL," +
            "  " + MicrofinanceCustomers.KEY_NIC_NUMBER + " varchar(255) DEFAULT NULL" +
            ")";
}
