package fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shery.safco.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import customclasses.CommonMethod;
import customclasses.PhpConnectivity;

/**
 * Created by shery on 5/4/2017.
 */

public class LoanVerForm extends Fragment implements View.OnClickListener {
    Toast toast;
    private static final String TAG = "LoanVerForm";
    private static final String URL1 = "https://mms.safcosupport.org/employees/tabData/disburse_loan.php";
    private TextView loanIdTxt, cusotmerIdTxt, loanAppByTxt,
            stationTxt, loanStatusTxt, loanAppOnTxt, projectTxt,
            loanTypeTxt, pLoanAmountTxt, loanSubTypeTxt,
            serviceChargesTxt, loanTermTxt, tLoanAmountTxt,
            repFreqTxt, tAmountPaidTxt, chequeNumberTxt,
            descTxt, remainingBalTxt, chequeDisbursedByTxt,
            bankChequeBookTxt, bankChequeNumberTxt, registrationfeesTxt, emergencyfundTxt;
    private Button disburseBtn;

    private DatePickerDialog mDatePickerDialog;
    private Date curDate;
    private SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private EditText chequeDisbursedOnTxt, chequePreparedOnTxt, notesTxt, otherfeesTxt, descriptionTxt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loan_veri_form_xml, container, false);
        Log.e(TAG, "onCreateView: " + getArguments().getString("loan_data"));
        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        disburseBtn = (Button) view.findViewById(R.id.disburseBtn);
        loanIdTxt = (TextView) view.findViewById(R.id.loanIdTxt);
        cusotmerIdTxt = (TextView) view.findViewById(R.id.cusotmerIdTxt);
        loanAppByTxt = (TextView) view.findViewById(R.id.loanAppByTxt);
        stationTxt = (TextView) view.findViewById(R.id.stationTxt);
        loanStatusTxt = (TextView) view.findViewById(R.id.loanStatusTxt);
        loanAppOnTxt = (TextView) view.findViewById(R.id.loanAppOnTxt);
        projectTxt = (TextView) view.findViewById(R.id.projectTxt);
        loanTypeTxt = (TextView) view.findViewById(R.id.loanTypeTxt);
        pLoanAmountTxt = (TextView) view.findViewById(R.id.pLoanAmountTxt);
        loanSubTypeTxt = (TextView) view.findViewById(R.id.loanSubTypeTxt);
        serviceChargesTxt = (TextView) view.findViewById(R.id.serviceChargesTxt);
        loanTermTxt = (TextView) view.findViewById(R.id.loanTermTxt);
        tLoanAmountTxt = (TextView) view.findViewById(R.id.tLoanAmountTxt);
        repFreqTxt = (TextView) view.findViewById(R.id.repFreqTxt);
        tAmountPaidTxt = (TextView) view.findViewById(R.id.tAmountPaidTxt);
        chequeNumberTxt = (TextView) view.findViewById(R.id.chequeNumberTxt);
        descTxt = (TextView) view.findViewById(R.id.descTxt);
        remainingBalTxt = (TextView) view.findViewById(R.id.remainingBalTxt);
        chequeDisbursedByTxt = (TextView) view.findViewById(R.id.chequeDisbursedByTxt);
        bankChequeBookTxt = (TextView) view.findViewById(R.id.bankChequeBookTxt);
        bankChequeNumberTxt = (TextView) view.findViewById(R.id.bankChequeNumberTxt);
        chequePreparedOnTxt = (EditText) view.findViewById(R.id.chequePreparedOnTxt);
        notesTxt = (EditText) view.findViewById(R.id.notesTxt);
        chequeDisbursedOnTxt = (EditText) view.findViewById(R.id.chequeDisbursedOnTxt);
        otherfeesTxt = (EditText) view.findViewById(R.id.otherfeesTxt);
        descriptionTxt = (EditText) view.findViewById(R.id.descriptionTxt);
        registrationfeesTxt = (TextView) view.findViewById(R.id.registrationfeesTxt);
        emergencyfundTxt = (TextView) view.findViewById(R.id.emergencyfundTxt);

        chequeDisbursedOnTxt.setOnClickListener(this);
//        chequePreparedOnTxt.setOnClickListener(this);
        disburseBtn.setOnClickListener(this);
        try {
            JSONObject jsonObj = new JSONObject(getArguments().getString("loan_data"));
            Iterator<String> keys = jsonObj.keys();
            while (keys.hasNext()) {
                String key = keys.next();

                if (jsonObj.getString(key).equalsIgnoreCase("null")) {
                    jsonObj.put(key, "");
                }
            }

            loanIdTxt.setText(jsonObj.getString("LoanID"));
            cusotmerIdTxt.setText(jsonObj.getString("CustomerId"));
            loanAppByTxt.setText(jsonObj.getString("EmoloyeeFullName"));
            stationTxt.setText(jsonObj.getString("StatoinName"));
            loanStatusTxt.setText(jsonObj.getString("Loan_Status"));
            loanAppOnTxt.setText(jsonObj.getString("LoanDateTime"));
            projectTxt.setText(jsonObj.getString("ProjectName"));
            loanTypeTxt.setText(jsonObj.getString("sParentLoanTypeName"));
            pLoanAmountTxt.setText(jsonObj.getString("dApprovedLoanAmount"));
            loanSubTypeTxt.setText(jsonObj.getString("sLoanTypeName"));
            serviceChargesTxt.setText(jsonObj.getString("dServiceChargesRate"));
            loanTermTxt.setText(jsonObj.getString("sLoanTerm"));
            tLoanAmountTxt.setText(jsonObj.getString("dTotalLoanAmount"));
            repFreqTxt.setText(jsonObj.getString("sRepaymentFrequency"));
            tAmountPaidTxt.setText("0.00");
            chequeNumberTxt.setText(jsonObj.getString("DeathCase_ChequeNumber"));
            descTxt.setText(jsonObj.getString("DeathCase_Description"));
            chequeDisbursedByTxt.setText(jsonObj.getString("EmoloyeeFullName"));
            bankChequeBookTxt.setText(jsonObj.getString("sBankName") + " " + jsonObj.getString("sBranchName") + "(" + jsonObj.getString("iAccountNumber") + ")");
            bankChequeNumberTxt.setText(jsonObj.getString("ChequeNumber"));
            registrationfeesTxt.setText(jsonObj.getString("dRegistrationFees"));
            emergencyfundTxt.setText(jsonObj.getString("dEmergencyFund"));
            otherfeesTxt.setText(jsonObj.getString("iOtherFees"));
            remainingBalTxt.setText(jsonObj.getString("dTotalLoanAmount"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        /*if (view == chequePreparedOnTxt) {
            showCalendarDate(chequePreparedOnTxt);
        }*/
        if (view == chequeDisbursedOnTxt) {
            showCalendarDate(chequeDisbursedOnTxt);
        }
        if (view == disburseBtn) {
            try {
                if (validateUi()) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("cusotmerId", cusotmerIdTxt.getText().toString());
                    jsonObject.put("loanId", loanIdTxt.getText().toString());
                    jsonObject.put("registrationfees", registrationfeesTxt.getText().toString());
                    jsonObject.put("emergencyfund", emergencyfundTxt.getText().toString());
                    jsonObject.put("otherfees", otherfeesTxt.getText().toString());
                    jsonObject.put("otherFeesdescription", descriptionTxt.getText().toString());
                    jsonObject.put("chequeDisbursedOn", chequeDisbursedOnTxt.getText().toString());
                    jsonObject.put("notes", notesTxt.getText().toString());
                    new SyncUpLoanAsync().execute(jsonObject.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean validateUi() {
        if (CommonMethod.checkEmpty(chequeDisbursedOnTxt)) {
            chequeDisbursedOnTxt.setError("Empty Field");
            return false;
        }if (CommonMethod.checkEmpty(otherfeesTxt)) {
            otherfeesTxt.setError("Empty Field");
            return false;
        }
        return true;
    }

    protected class SyncUpLoanAsync extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Syncing Up Loan Disbursment Data....");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            return PhpConnectivity.getStringFromUrl(URL1, PhpConnectivity.getParams(strings[0]));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Log.e(TAG, "response: " + s);
            if (s != null && !s.isEmpty()) {
                if (s.trim().equalsIgnoreCase("Successfully Disbursed Amount")) {
                    toast.setText(s);
                    toast.show();
                }else{
                    toast.setText(s);
                    toast.show();
                    getActivity().onBackPressed();
                }

            }

        }
    }

    public void showCalendarDate(final EditText edit) {
        Calendar newCalender = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                curDate = newDate.getTime();
                edit.setText(dateFormater.format(curDate));
            }
        }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.show();
    }
}
