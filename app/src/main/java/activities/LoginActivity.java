package activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.shery.safco.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import customclasses.PhpConnectivity;
import databases.DBHandler;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by shery on 11/15/2016.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private Button loginBtn;
    private DBHandler dbHandler;
    private SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHandler = new DBHandler(this);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        setSupportActionBar(mToolbar);
        loginBtn.setOnClickListener(this);
        getSupportActionBar().setTitle("Login");

    }



    @Override
    public void onClick(View view) {
        if (view == loginBtn) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            /*new LoginTask().execute();*/


        }
    }

    private class LoginTask extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("logging in...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {


            return PhpConnectivity.getStringFromUrl("http://mms.safcosupport.org/employees/tabData/login.php", getParams("abc"));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e(TAG, s);
            progressDialog.dismiss();

        }
    }


    public static List<NameValuePair> getParams(String data) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", data));
        return params;
    }
}
