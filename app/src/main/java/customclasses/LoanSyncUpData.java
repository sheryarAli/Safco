package customclasses;

import java.util.ArrayList;

/**
 * Created by shery on 12/30/2016.
 */

public class LoanSyncUpData {
    private Loan Loan;
    private QuestionnaireAnswer questionnaireAnswer;
    private ArrayList<Document> documents;

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }


    public customclasses.Loan getLoan() {
        return Loan;
    }

    public void setLoan(customclasses.Loan loan) {
        Loan = loan;
    }

    public QuestionnaireAnswer getQuestionnaireAnswer() {
        return questionnaireAnswer;
    }

    public void setQuestionnaireAnswer(QuestionnaireAnswer questionnaireAnswer) {
        this.questionnaireAnswer = questionnaireAnswer;
    }
}
