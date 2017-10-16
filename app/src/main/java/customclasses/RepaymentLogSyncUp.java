package customclasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import activities.CustomerSyncUpActivity;
import cz.msebera.android.httpclient.Header;
import databases.DbHandler2;

import static customclasses.CommonConst.RESPONSE_SUCCESS;

/**
 * Created by shery on 9/6/2017.
 */

public class RepaymentLogSyncUp {
    private static final String TAG = "RepaymentSyncUp";
    private Context context;
    private DbHandler2 dbHandler2;
    private ProgressDialog progressDialog;

    public RepaymentLogSyncUp(Context context) {
        dbHandler2 = new DbHandler2(context);
        progressDialog = new ProgressDialog(context);
        this.context = context;

    }

    public void SyncUp() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setMessage("Do You Want To Sync Up Repayment Data?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        SyncUpRepaymenData();


                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void SyncUpRepaymenData() {
        final ArrayList<RepaymentLog> repaymentLogs = dbHandler2.getRepaymentLogData();
        String repaymentJson = new Gson().toJson(repaymentLogs);
        Log.d(TAG, "SyncUpRepaymenData: for Debuging");
        CommonMethod.writeToFile(repaymentJson);
        RequestParams params = new RequestParams();
        params.add("RepaymentInfo", repaymentJson);
        //TODO Test URL
        /*https://mms.safcosupport.org/test/employees/tabData/SyncUpRepayment.php*/
        PhpConnectivity.getClient().post("https://mms.safcosupport.org/employees/tabData/SyncUpRepayment.php", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        progressDialog.setTitle("Repayment Log Sync Up");
                        progressDialog.setMessage("Uploading Data..");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        progressDialog.dismiss();
                        String response = new String(responseBody);
                        if (response.trim().equalsIgnoreCase(RESPONSE_SUCCESS)) {
                            dbHandler2.updateRepaymentLogDataSyncStatus(repaymentLogs);
                            CommonMethod.alert(context
                                    , "Repayment Sync Up:", "Success");

                        } else {
                            Log.e(TAG, "onFailure: " + response);
                            CommonMethod.alert(context
                                    , "Repayment Sync Up Failure:", "Response: " + response);
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        progressDialog.dismiss();
                        if (responseBody == null) {
                            CommonMethod.alert(context
                                    , "Connectivity Error:", "Try Again!");
                            return;
                        }

                        String response = new String(responseBody);
                        Log.e(TAG, "onFailure: " + response);
                        CommonMethod.alert(context
                                , "Repayment Sync Up Failure:", "Response: " + response);
                    }
                }
        );
    }

}
