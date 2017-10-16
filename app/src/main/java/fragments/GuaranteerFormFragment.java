package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shery.safco.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import activities.GuaranteerActivity;
import customclasses.CommonConst;
import customclasses.CommonMethod;
import customclasses.Guaranteer;
import customclasses.NicTextWatcher;
import databases.DBHandler;

/**
 * Created by shery on 11/25/2016.
 */

public class GuaranteerFormFragment extends Fragment implements View.OnClickListener {
    private Button saveBtn;
    private EditText fNameEdt, nicNumEdt, addEdt, conNumEdt, jobDescEdt, bAddEdt, notesEdt;
    private CheckBox status;
    private Spinner jobTypeSpin;
    private DBHandler dbHandler;
    private Guaranteer guaranteer;
    private Toast toast;
    private TextView addOnTxt;

    private void initViews(View view) {
        saveBtn = (Button) view.findViewById(R.id.saveBtn);
        fNameEdt = (EditText) view.findViewById(R.id.fNameEdt);
        nicNumEdt = (EditText) view.findViewById(R.id.nicNumEdt);
        addEdt = (EditText) view.findViewById(R.id.addEdt);
        conNumEdt = (EditText) view.findViewById(R.id.conNumEdt);

        jobDescEdt = (EditText) view.findViewById(R.id.jobDescEdt);
        bAddEdt = (EditText) view.findViewById(R.id.bAddEdt);
        status = (CheckBox) view.findViewById(R.id.status);
        jobTypeSpin = (Spinner) view.findViewById(R.id.jobTypeSpin);
        addOnTxt = (TextView) view.findViewById(R.id.addOnTxt);
        notesEdt = (EditText) view.findViewById(R.id.notesEdt);

    }

    private void checkViewType() {
        Bundle args = getArguments();
        if (args == null) {
            return;
        }
        guaranteer = (Guaranteer) args.getSerializable("guaranteer");
        if (guaranteer != null) {
            fNameEdt.setText(guaranteer.getFullName());
            nicNumEdt.setText(guaranteer.getNICNumber());
            addEdt.setText(guaranteer.getAddress());
            conNumEdt.setText(guaranteer.getContactNumber());
            jobDescEdt.setText(guaranteer.getJobDescription());
            bAddEdt.setText(guaranteer.getBusinessAddress());
            addEdt.setText(guaranteer.getAddress());
            notesEdt.setText(guaranteer.getNotes());
            status.setChecked((guaranteer.getStatus() == 1));
            CommonMethod.setSpinnerSelectedItem(jobTypeSpin, guaranteer.getJobType());
            saveBtn.setText("update");
        }


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_guaranteers_form, container, false);
        dbHandler = new DBHandler(getContext());
        initViews(v);
        checkViewType();
        toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        nicNumEdt.setFilters(NicTextWatcher.getFilter());
        nicNumEdt.addTextChangedListener(NicTextWatcher.getInstance());
        conNumEdt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        addOnTxt.setText(new SimpleDateFormat("MMMM d, yyyy", Locale.US).format(Calendar.getInstance().getTime()));
        saveBtn.setOnClickListener(this);
        return v;
    }

    private boolean validateUi() {

        if (fNameEdt.getText().toString().equalsIgnoreCase("")
                || nicNumEdt.getText().toString().equalsIgnoreCase("")
                || addEdt.getText().toString().equalsIgnoreCase("")
                || conNumEdt.getText().toString().equalsIgnoreCase("")
                ) {
            toast.setText("Please fill all required fields");
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
                    Guaranteer guaranteer = new Guaranteer();
                    guaranteer.setCustomerId(((GuaranteerActivity) getActivity()).customerId);
                    guaranteer.setFullName(fNameEdt.getText().toString());
                    guaranteer.setNICNumber(nicNumEdt.getText().toString());
                    guaranteer.setAddress(addEdt.getText().toString());
                    guaranteer.setContactNumber(conNumEdt.getText().toString());
                    guaranteer.setJobDescription(jobDescEdt.getText().toString());
                    guaranteer.setBusinessAddress(bAddEdt.getText().toString());
                    guaranteer.setNotes(notesEdt.getText().toString());
                    guaranteer.setStatus((status.isChecked() ? 1 : 0));
                    guaranteer.setJobType(jobTypeSpin.getSelectedItem() + "");

                    if (dbHandler.insertGuaranteerData(guaranteer) != -1) {

                        Toast.makeText(getContext(), "Guaranteer Inserted!", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }
                } else if (saveBtn.getText().toString().equalsIgnoreCase("update")) {
                    guaranteer.setCustomerId(((GuaranteerActivity) getActivity()).customerId);
                    guaranteer.setFullName(fNameEdt.getText().toString());
                    guaranteer.setNICNumber(nicNumEdt.getText().toString());
                    guaranteer.setAddress(addEdt.getText().toString());
                    guaranteer.setContactNumber(conNumEdt.getText().toString());
                    guaranteer.setJobDescription(jobDescEdt.getText().toString());
                    guaranteer.setBusinessAddress(bAddEdt.getText().toString());
                    guaranteer.setNotes(notesEdt.getText().toString());
                    guaranteer.setStatus((status.isChecked() ? 1 : 0));
                    guaranteer.setJobType(jobTypeSpin.getSelectedItem() + "");

                    if (dbHandler.updateGuaranteerData(guaranteer) != 0) {

                        Toast.makeText(getContext(), "Guaranteer Updated!", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }
                }

            }
        }
    }

}
