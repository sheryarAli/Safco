package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shery.safco.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import activities.FamilyMemberActivity;
import activities.LoanActivity;
import customclasses.CommonConst;
import customclasses.CommonMethod;
import customclasses.FamilyMember;
import customclasses.MicrofinanceCustomers;
import customclasses.NicTextWatcher;
import databases.DBHandler;

/**
 * Created by shery on 11/25/2016.
 */

public class FamilyMemberFormFragment extends Fragment implements View.OnClickListener {
    private DBHandler dbHandler;
    private Button saveBtn;
    private EditText fNameEdt, nicNumEdt, relationEdt, ageEdt, eduEdt, mEarningEdt, soiEdt, bAddEdt;
    private Spinner genderSpin;
    private Toast toast;
    private TextView addOnTxt;
    private FamilyMember familyMember;

    private void initViews(View view) {
        saveBtn = (Button) view.findViewById(R.id.saveBtn);
        fNameEdt = (EditText) view.findViewById(R.id.fNameEdt);
        nicNumEdt = (EditText) view.findViewById(R.id.nicNumEdt);

        relationEdt = (EditText) view.findViewById(R.id.relationEdt);
        ageEdt = (EditText) view.findViewById(R.id.ageEdt);
        eduEdt = (EditText) view.findViewById(R.id.eduEdt);
        mEarningEdt = (EditText) view.findViewById(R.id.mEarningEdt);
        soiEdt = (EditText) view.findViewById(R.id.soiEdt);
        bAddEdt = (EditText) view.findViewById(R.id.bAddEdt);
        addOnTxt = (TextView) view.findViewById(R.id.addOnTxt);
        genderSpin = (Spinner) view.findViewById(R.id.genderSpin);

    }

    private void checkViewType() {
        Bundle args = getArguments();
        familyMember = (FamilyMember) args.getSerializable("family_member");
        if (familyMember != null) {
            fNameEdt.setText(familyMember.getFullName());
            nicNumEdt.setText(familyMember.getNICNumber());
            relationEdt.setText(familyMember.getRelationshipWithCustomer());
            mEarningEdt.setText(familyMember.getMonthlyEarning() + "");
            ageEdt.setText(familyMember.getAge() + "");
            eduEdt.setText(familyMember.getEducation());
            soiEdt.setText(familyMember.getSourceOfIncome() + "");
            bAddEdt.setText(familyMember.getBusinessAddress());
            CommonMethod.setSpinnerSelectedItem(genderSpin, (familyMember.getGender().equalsIgnoreCase("M")?"male":"female"));
            saveBtn.setText("Update");
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fmember_form, container, false);
        initViews(v);
        checkViewType();

        dbHandler = new DBHandler(getContext());
        toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        nicNumEdt.setFilters(NicTextWatcher.getFilter());
        nicNumEdt.addTextChangedListener(NicTextWatcher.getInstance());
        addOnTxt.setText(new SimpleDateFormat("MMMM d, yyyy", Locale.US).format(Calendar.getInstance().getTime()));
        saveBtn.setOnClickListener(this);
        return v;
    }

    private boolean validateUi() {
        if (fNameEdt.getText().toString().equalsIgnoreCase("")
                || nicNumEdt.getText().toString().equalsIgnoreCase("")
                || ageEdt.getText().toString().equalsIgnoreCase("")
                || relationEdt.getText().toString().equalsIgnoreCase("")
                || eduEdt.getText().toString().equalsIgnoreCase("")
                || mEarningEdt.getText().toString().equalsIgnoreCase("")
                || soiEdt.getText().toString().equalsIgnoreCase("")
                ) {

            toast.setText("Please fill all required fields");
            toast.show();
            return false;

        } else if (!nicNumEdt.getText().toString().matches("\\d{5}-\\d{7}-\\d")) {
            toast.setText("NIC Not Valid ");
            toast.show();
            return false;
        } else if (!fNameEdt.getText().toString().matches("^[a-zA-Z ]*$")) {
            toast.setText("Name Not Valid ");
            toast.show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (validateUi()) {
            if (view == saveBtn) {

                if (saveBtn.getText().toString().equalsIgnoreCase("save")) {
                    FamilyMember familyMember = new FamilyMember();

                    familyMember.setCustomerId(((FamilyMemberActivity) getActivity()).customerId);
                    familyMember.setFullName(fNameEdt.getText().toString());
                    familyMember.setNICNumber(nicNumEdt.getText().toString());
                    familyMember.setAge(Integer.valueOf(ageEdt.getText().toString()));
                    familyMember.setRelationshipWithCustomer(relationEdt.getText().toString());
                    familyMember.setEducation(eduEdt.getText().toString());
                    familyMember.setMonthlyEarning(Double.valueOf(mEarningEdt.getText().toString()));
                    familyMember.setSourceOfIncome(soiEdt.getText().toString());
                    familyMember.setBusinessAddress(bAddEdt.getText().toString());
                    familyMember.setGender("" + ((genderSpin.getSelectedItemPosition() == 0) ? 'M' : 'F'));


                    if (dbHandler.insertFamilyMemberData(familyMember) != 0) {

                        Toast.makeText(getContext(), "Family Member Inserted!", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }
                } else if (saveBtn.getText().toString().equalsIgnoreCase("update")) {
                    familyMember.setCustomerId(((FamilyMemberActivity) getActivity()).customerId);
                    familyMember.setFullName(fNameEdt.getText().toString());
                    familyMember.setNICNumber(nicNumEdt.getText().toString());
                    familyMember.setAge(Integer.valueOf(ageEdt.getText().toString()));
                    familyMember.setRelationshipWithCustomer(relationEdt.getText().toString());
                    familyMember.setEducation(eduEdt.getText().toString());
                    familyMember.setMonthlyEarning(Double.valueOf(mEarningEdt.getText().toString()));
                    familyMember.setSourceOfIncome(soiEdt.getText().toString());
                    familyMember.setBusinessAddress(bAddEdt.getText().toString());
                    familyMember.setGender("" + ((genderSpin.getSelectedItemPosition() == 0) ? 'M' : 'F'));

                    if (dbHandler.updateFamilyMember(familyMember) != -1) {

                        Toast.makeText(getContext(), "Family Member Updated!", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }
                }

            }
        }


    }

}
