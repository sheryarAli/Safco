package customclasses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shery on 11/18/2016.
 */

public class ServerResponseParser implements Serializable {
    @SerializedName("Station")
    private ArrayList<Stations> stationList = new ArrayList<Stations>();
    @SerializedName("Questionaire")
    private ArrayList<Questionnaire> QuestionnaireList = new ArrayList<Questionnaire>();
    public ServerResponseParser() {
    }

    public ArrayList<Questionnaire> getQuestionnaireList() {
        return QuestionnaireList;
    }

    public void setQuestionnaireList(ArrayList<Questionnaire> questionnaireList) {
        QuestionnaireList = questionnaireList;
    }

    public ArrayList<Stations> getStationList() {
        return stationList;
    }

    public void setStationList(ArrayList<Stations> stationList) {
        this.stationList = stationList;
    }
}
