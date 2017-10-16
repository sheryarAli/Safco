package fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.shery.safco.R;

import java.util.ArrayList;
import java.util.Iterator;

import activities.MainActivity;
import customclasses.CommonMethod;
import customclasses.Repayment;
import customclasses.ServerResponseParse2;
import customclasses.ServerResponseParser;
import customclasses.Stations;
import databases.DBHandler;
import databases.DbHandler2;
import retrofit.ApiClient;
import retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shery on 8/10/2017.
 */

public class DialogRepaymentSyncDownFragment extends DialogFragment implements View.OnClickListener {
    private static final String TAG = "DialogRepSyncDownFrag";
    private Spinner stationSpin;
    private ArrayAdapter<String> adapter1;
    private ArrayList<Stations> stationList;
    private ArrayList<String> stationNameList;
    private DBHandler dbHandler;
    private DbHandler2 dbHandler2;
    private Button syncDownBtn;
    private ProgressDialog progressDialog;
    private Toast toast;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LinearLayout root = new LinearLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_repayment, container, false);
        stationSpin = (Spinner) view.findViewById(R.id.stationSpin);
        syncDownBtn = (Button) view.findViewById(R.id.syncDownBtn);

        syncDownBtn.setOnClickListener(this);
        stationList = new ArrayList<Stations>();
        stationNameList = new ArrayList<String>();
        toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        dbHandler = new DBHandler(getContext());
        dbHandler2 = new DbHandler2(getContext());
        stationList = dbHandler.getStationData();
        for (Stations station : stationList) {

            stationNameList.add(station.getStationName());
        }
        adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, stationNameList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stationSpin.setAdapter(adapter1);
        return view;
    }


    @Override
    public void onClick(View view) {
        if (view == syncDownBtn) {

            showSyncAlert();

        }
    }

    protected void showSyncAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sync Down")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        syncDownRepaymentData();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setMessage("Are You Sure(Previous data will be erased)?");

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    private void syncDownRepaymentData() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Preparing For Sync Down...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ServerResponseParse2> call = apiService.getRepaymentData(stationList.get(stationSpin.getSelectedItemPosition()).getStationId());

        call.enqueue(new Callback<ServerResponseParse2>() {
            @Override
            public void onResponse(Call<ServerResponseParse2> call, Response<ServerResponseParse2> response) {
                ArrayList<Repayment> repayments = response.body().getRepaymentArrayList();
//                for (Iterator<Repayment> iterator: repayments.iterator().hasNext())
                Log.e(TAG, "onResponse: repayment total records: " + repayments.size());
                dbHandler2.clearDB();
                progressDialog.dismiss();
                new RepaymentAsync(getActivity(), repayments.size()).execute(repayments);
                getDialog().dismiss();


            }

            @Override
            public void onFailure(Call<ServerResponseParse2> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "Failure: " + t.getMessage());
                CommonMethod.alert(getContext(), "Sync Down Failure", "Server Error: " + t.getMessage());
                getDialog().dismiss();
            }
        });

    }

    protected class RepaymentAsync extends AsyncTask<ArrayList<Repayment>, Integer, Integer> {
        private ProgressDialog progressDialog;
        private int max;
        private Activity activity;


        protected RepaymentAsync(Activity activity, int max) {
            this.max = max;
            this.activity = activity;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Downloading Repayment Data");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(max);
            progressDialog.setProgress(0);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(ArrayList<Repayment>... arrayLists) {
            for (int i = 0; i < arrayLists[0].size(); i++) {
                double approveAmount = Double.parseDouble(arrayLists[0].get(i).getApproveAmount());
                double totalPaid = Double.parseDouble(arrayLists[0].get(i).getTotalpaid() == null ? "0.0" : arrayLists[0].get(i).getTotalpaid());
                if (totalPaid >= approveAmount) {
                    continue;
                }
                long res = dbHandler2.insertRepaymentData(arrayLists[0].get(i));
                if (res == 0) {
                    Log.e(TAG, "doInBackground: insertRepaymentData res: " + res);
                    return 1;

                }


                publishProgress(i + 1);
            }
            return 0;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            switch (s) {
                case 0:
                    Log.e(TAG, "onPostExecute: Data Successfully Inserted");
                    CommonMethod.alert(activity, "Sync Down", "Success");
                    break;
                case 1:
                    Log.e(TAG, "onPostExecute: Data Insertion Failed");
                    CommonMethod.alert(activity, "Sync Down Failure", "Try Again");
                    break;

            }
        }

    }


}
