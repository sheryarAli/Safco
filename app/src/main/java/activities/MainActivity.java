package activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shery.safco.R;

import java.util.ArrayList;

import customclasses.CommonMethod;
import customclasses.Questionnaire;
import customclasses.RepaymentLogSyncUp;
import customclasses.ServerResponseParser;

import customclasses.Stations;
import databases.DBHandler;
import databases.DbHandler2;
import fragments.DialogRepaymentSyncDownFragment;
import fragments.HomeFragment;
import fragments.LoanVerForm;
import fragments.RepaymentDeleteFragment;
import fragments.RepaymentFragment;
import fragments.SyncupFPAndNic;
import fragments.ManageRepLogFragment;
import preferences.LocalStoragePreferences;
import retrofit.ApiClient;
import retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toast toast;
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private FragmentManager mFragmentManager;
    private Toolbar mToolbar;
    private int lastMenu = 0;
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView userNameTxt;
    private DBHandler dbHandler;
    private DbHandler2 dbHandler2;
    private LocalStoragePreferences preferences;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("LAST_SELECTED_ITEM", lastMenu);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastMenu = savedInstanceState.getInt("LAST_SELECTED_ITEM", 0);
    }

    private void initView() {
        mFragmentManager = getSupportFragmentManager();
        mDrawer = (DrawerLayout) findViewById(R.id.dr_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        preferences = new LocalStoragePreferences(this);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHandler = new DBHandler(this);
        dbHandler2 = new DbHandler2(this);
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);

        initView();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };


        mDrawer.addDrawerListener(mDrawerToggle);
        mNavigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null && lastMenu == 0) {

            mNavigationView.getMenu().performIdentifierAction(R.id.nav_home_frag, 0);

        } else {
            mNavigationView.setCheckedItem(lastMenu);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment frag = null;

        if (lastMenu != item.getItemId()) {
            switch (item.getItemId()) {
                case R.id.nav_home_frag:
                    frag = new HomeFragment();
                    break;
                case R.id.nav_group_sync_up:
                    moveToSyncUpActivity();
//                    mDrawer.closeDrawers();
                    break;
                case R.id.nav_cust_sync_up:
                    startActivity(new Intent(this, CustomerSyncUpActivity.class));
//                    mDrawer.closeDrawers();
                    break;
                case R.id.nav_sync_down:
                    if (CommonMethod.isNetworkAvailable(this)){
                        getData();
                    }
                    break;
                case R.id.nav_repayment_sync_down:
                    if (CommonMethod.isNetworkAvailable(this)){
                        showRepaymentDialog();
                    }
                    break;
//                    return false;
                case R.id.nav_repayment:
                    frag = new RepaymentFragment();
//                    mDrawer.closeDrawers();
                    break;
                case R.id.nav_manage_rep_log:
                    frag = new ManageRepLogFragment();
//                    CommonMethod.showUnavailableToast(MainActivity.this);
//                    mDrawer.closeDrawers();
                    break;
                case R.id.nav_del_syn_repayment:
                    frag = new RepaymentDeleteFragment();
//                    mDrawer.closeDrawers();
                    break;
                case R.id.nav_repayment_sync_up:
                    if (CommonMethod.isNetworkAvailable(this)){
                        new RepaymentLogSyncUp(MainActivity.this).SyncUp();
                    }

//                    mDrawer.closeDrawers();
                    break;
                case R.id.nav_loan_verification:
                    frag = new SyncupFPAndNic();
                    break;
                case R.id.nav_logout:
                    alert("Are You Sure?");
//                    mDrawer.closeDrawers();
                    break;
            }

        }

        if (frag != null) {
            lastMenu = item.getItemId();
            mFragmentManager.beginTransaction().replace(R.id.fl_container, frag).commit();
            mNavigationView.setCheckedItem(item.getItemId());
            item.setChecked(true);
            mDrawer.closeDrawers();
            setTitle(item.getTitle());
        }
        mDrawer.closeDrawers();
        return false;
    }
    public void showRepaymentDialog(){
        DialogRepaymentSyncDownFragment repaymentSyncDownFragment = new DialogRepaymentSyncDownFragment();
        repaymentSyncDownFragment.show(mFragmentManager, "DialogRepaymentSyncDownFragment");



    }

    public void moveToLoanVeriForm(String jsonData){
        Fragment frag = new LoanVerForm();
        Bundle args = new Bundle();
        args.putString("loan_data", jsonData);
        frag.setArguments(args);
        mFragmentManager.beginTransaction().replace(R.id.fl_container, frag).addToBackStack(null).commit();
        setTitle("Loan Verification");
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private void alert(String message) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Log Out")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();

                        moveToLoginActivity();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }


    public void getData() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Syncing Down Data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponseParser> call = apiService.getStationData();


        call.enqueue(new Callback<ServerResponseParser>() {


            @Override
            public void onResponse(Call<ServerResponseParser> call, Response<ServerResponseParser> response) {
                Log.e(TAG, "Response: " + response.body());
                ArrayList<Stations> statioList = response.body().getStationList();
                ArrayList<Questionnaire> questionnaireList = response.body().getQuestionnaireList();
                Log.e(TAG, "Station array size: " + statioList.size());
                /*for (Stations station : statioList) {
                    Log.e(TAG, "Station: " + station.getStationName());


                }

                foar (Questionnaire questionnaire : questionnaireList) {
                    Log.e(TAG, "Questionnaire Id: " + questionnaire.getQuestionnaireId());
                }*/
                /*dbHandler.clearStationTable();*/
                dbHandler.insertStationData(statioList);
                toast.setText("Sync Down Successfull!");
                toast.show();
                preferences.setIsSyncedDown(true);
                dbHandler.insertQuestionnaire(questionnaireList);
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ServerResponseParser> call, Throwable t) {
                Log.e(TAG, "Failure: " + t.getMessage());
                toast.setText("Sync Down Failed");
                toast.show();
            }
        });


    }

    public void moveToSyncUpActivity() {

        startActivity(new Intent(this, SyncUpActivity.class));
    }

    public void moveToLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


}
