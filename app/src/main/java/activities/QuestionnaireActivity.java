package activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shery.safco.R;

import java.util.ArrayList;

import adapters.GenericAdapter;
import customclasses.CommonMethod;
import customclasses.Loan;
import customclasses.MicrofinanceCustomers;
import customclasses.Questionnaire;
import customclasses.QuestionnaireAnswer;
import customclasses.Stations;
import databases.DBHandler;
import fragments.FamilyMemberFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by shery on 12/22/2016.
 */

public class QuestionnaireActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = Questionnaire.class.getSimpleName();
    private RecyclerView questRecView;
    private TextView custNameTxt;
    private Button addBtn;
    private ArrayList<Questionnaire> questionnaires;

    private GenericAdapter<Questionnaire> adapter;
    private DBHandler dbHandler;
    private Loan loan;
    private Toast toast;
    private QuestionnaireAnswer questionnaireAnswer;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    private void checkViewType() {
        questionnaires = dbHandler.getQuestionnaireData();
        questionnaireAnswer = dbHandler.getQuestionnaireAnswerData(loan.getLoanId(), loan.getCustomerId());
        if (questionnaireAnswer != null) {

            questionnaires.get(0).setSelectedAnswer(questionnaireAnswer.getAnswer1());
            questionnaires.get(1).setSelectedAnswer(questionnaireAnswer.getAnswer2());
            questionnaires.get(2).setSelectedAnswer(questionnaireAnswer.getAnswer3());
            questionnaires.get(3).setSelectedAnswer(questionnaireAnswer.getAnswer4());
            questionnaires.get(4).setSelectedAnswer(questionnaireAnswer.getAnswer5());
            questionnaires.get(5).setSelectedAnswer(questionnaireAnswer.getAnswer6());
            questionnaires.get(6).setSelectedAnswer(questionnaireAnswer.getAnswer7());
            questionnaires.get(7).setSelectedAnswer(questionnaireAnswer.getAnswer8());
            questionnaires.get(8).setSelectedAnswer(questionnaireAnswer.getAnswer9());
            questionnaires.get(9).setSelectedAnswer(questionnaireAnswer.getAnswer10());
            questionnaires.get(10).setSelectedAnswer(questionnaireAnswer.getAnswer11());
            questionnaires.get(11).setSelectedAnswer(questionnaireAnswer.getAnswer12());
            questionnaires.get(12).setSelectedAnswer(questionnaireAnswer.getAnswer13());

            addBtn.setText("Update");

        } else {


            for (Questionnaire questionnaire : questionnaires) {
                questionnaire.setSelectedAnswer("Select Answer");
            }
        }


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionare);
        questRecView = (RecyclerView) findViewById(R.id.questRecView);
        addBtn = (Button) findViewById(R.id.addBtn);
        questRecView.setLayoutManager(new LinearLayoutManager(this));
        custNameTxt = (TextView) findViewById(R.id.custNameTxt);
        loan = (Loan) getIntent().getSerializableExtra("Loan");
        dbHandler = new DBHandler(this);
        checkViewType();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Poverty Score Card");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);


        addBtn.setOnClickListener(this);


    }

    private boolean validateUI() {
        for (int i = 0; i < 13; i++) {
            if (questionnaires.get(i).getSelectedAnswer().equalsIgnoreCase("Select Answer")) {
                toast.setText("Please Fill All Questions");
                toast.show();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == addBtn) {
            if (validateUI()) {
                if (addBtn.getText().toString().equalsIgnoreCase("add")) {

                    QuestionnaireAnswer questionnaireAnswer = new QuestionnaireAnswer();
                    questionnaireAnswer.setAnswer1(questionnaires.get(0).getSelectedAnswer());
                    questionnaireAnswer.setAnswer2(questionnaires.get(1).getSelectedAnswer());
                    questionnaireAnswer.setAnswer3(questionnaires.get(2).getSelectedAnswer());
                    questionnaireAnswer.setAnswer4(questionnaires.get(3).getSelectedAnswer());
                    questionnaireAnswer.setAnswer5(questionnaires.get(4).getSelectedAnswer());
                    questionnaireAnswer.setAnswer6(questionnaires.get(5).getSelectedAnswer());
                    questionnaireAnswer.setAnswer7(questionnaires.get(6).getSelectedAnswer());
                    questionnaireAnswer.setAnswer8(questionnaires.get(7).getSelectedAnswer());
                    questionnaireAnswer.setAnswer9(questionnaires.get(8).getSelectedAnswer());
                    questionnaireAnswer.setAnswer10(questionnaires.get(9).getSelectedAnswer());
                    questionnaireAnswer.setAnswer11(questionnaires.get(10).getSelectedAnswer());
                    questionnaireAnswer.setAnswer12(questionnaires.get(11).getSelectedAnswer());
                    questionnaireAnswer.setAnswer13(questionnaires.get(12).getSelectedAnswer());
                    questionnaireAnswer.setCustomerId(loan.getCustomerId());
                    questionnaireAnswer.setLoan_Id(loan.getLoanId());
                    questionnaireAnswer.setNICNumber(loan.getNICNumber());

                    if (dbHandler.insertQuestionnareAnswer(questionnaireAnswer) != -1) {
                        toast.setText("Poverty Score Code Successfully Inserted!");
                        toast.show();
                        finish();

                    }

                } else if (addBtn.getText().toString().equalsIgnoreCase("update")) {
                    questionnaireAnswer.setAnswer1(questionnaires.get(0).getSelectedAnswer());
                    questionnaireAnswer.setAnswer2(questionnaires.get(1).getSelectedAnswer());
                    questionnaireAnswer.setAnswer3(questionnaires.get(2).getSelectedAnswer());
                    questionnaireAnswer.setAnswer4(questionnaires.get(3).getSelectedAnswer());
                    questionnaireAnswer.setAnswer5(questionnaires.get(4).getSelectedAnswer());
                    questionnaireAnswer.setAnswer6(questionnaires.get(5).getSelectedAnswer());
                    questionnaireAnswer.setAnswer7(questionnaires.get(6).getSelectedAnswer());
                    questionnaireAnswer.setAnswer8(questionnaires.get(7).getSelectedAnswer());
                    questionnaireAnswer.setAnswer9(questionnaires.get(8).getSelectedAnswer());
                    questionnaireAnswer.setAnswer10(questionnaires.get(9).getSelectedAnswer());
                    questionnaireAnswer.setAnswer11(questionnaires.get(10).getSelectedAnswer());
                    questionnaireAnswer.setAnswer12(questionnaires.get(11).getSelectedAnswer());
                    questionnaireAnswer.setAnswer13(questionnaires.get(12).getSelectedAnswer());

                    if (dbHandler.updateQuestionnaireAnswer(questionnaireAnswer) != -1) {
                        toast.setText("Poverty Score Code Successfully Updated!");
                        toast.show();
                        finish();

                    }
                }
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new GenericAdapter<Questionnaire>(this, questionnaires) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(QuestionnaireActivity.this).inflate(R.layout.questionare_item_layout, parent, false);
                view.setOnClickListener(this);
                ItemViewHolder viewHolder = new ItemViewHolder(view);
                return viewHolder;

            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, final Questionnaire val, int position) {
                final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

                itemViewHolder.cardView.setTag(position);
                itemViewHolder.questTxt.setText("Question " + val.getQuestionnaireId() + ": " + val.getQuestion());
                final ArrayList<String> answers = new ArrayList<String>();
                answers.add(val.getSelectedAnswer());
                switch (val.getAnswerType()) {
                    case 0:
                        if (val.getAnswer1() != null) {
                            answers.add(val.getAnswer1());
                        }
                        if (val.getAnswer2() != null) {
                            answers.add(val.getAnswer2());
                        }
                        if (val.getAnswer3() != null) {
                            answers.add(val.getAnswer3());
                        }
                        if (val.getAnswer4() != null) {
                            answers.add(val.getAnswer4());
                        }
                        if (val.getAnswer5() != null) {
                            answers.add(val.getAnswer5());
                        }
                        if (val.getAnswer6() != null) {
                            answers.add(val.getAnswer6());
                        }
                        if (val.getAnswer7() != null) {
                            answers.add(val.getAnswer7());
                        }
                        if (val.getAnswer8() != null) {
                            answers.add(val.getAnswer8());
                        }
                        if (val.getAnswer9() != null) {
                            answers.add(val.getAnswer9());
                        }
                        if (val.getAnswer10() != null) {
                            answers.add(val.getAnswer10());
                        }
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, answers) {
                            @Override
                            public boolean isEnabled(int position) {
                                if (position == 0) {
                                    return false;
                                } else {
                                    return true;
                                }
                            }

                            @Override
                            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view;
                                if (position == 0) {

                                    tv.setTextColor(Color.GRAY);
                                }
                                return view;
                            }
                        };
                        itemViewHolder.answerSpin.setAdapter(adapter1);
                        break;
                    case 1:


                        for (int i = Integer.parseInt(val.getAnswer1()); i <= Integer.parseInt(val.getAnswer2()); i++) {
                            answers.add(i + ". " + i);
                        }
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, answers) {
                            @Override
                            public boolean isEnabled(int position) {
                                if (position == 0) {
                                    return false;
                                } else {
                                    return true;
                                }
                            }

                            @Override
                            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view;
                                if (position == 0) {

                                    tv.setTextColor(Color.GRAY);
                                }
                                return view;
                            }

                        };
                        itemViewHolder.answerSpin.setAdapter(adapter2);

                        break;


                }


                itemViewHolder.answerSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        val.setSelectedAnswer(itemViewHolder.answerSpin.getSelectedItem() + "");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                CommonMethod.setSpinnerSelectedItem(itemViewHolder.answerSpin, val.getSelectedAnswer());

            }

            @Override
            public void onItemClick(View view) {

            }
        };

        questRecView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView questTxt;
        Spinner answerSpin;
        CardView cardView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            questTxt = (TextView) itemView.findViewById(R.id.questTxt);
            answerSpin = (Spinner) itemView.findViewById(R.id.answerSpin);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }


}
