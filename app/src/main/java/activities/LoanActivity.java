package activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.shery.safco.R;

import customclasses.MicrofinanceCustomers;
import customclasses.Stations;
import fragments.CustomerGroupFormFragment;
import fragments.CustomerLoanFormFragment;
import fragments.LoanFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by shery on 12/9/2016.
 */

public class LoanActivity extends AppCompatActivity {
    private static final String TAG = LoanActivity.class.getSimpleName();
    private FragmentManager mFragmentManager;
    private int customerId;
    private String nicNumber;


    public int getCustomerId() {
        return customerId;
    }

    public String getNicNumber() {
        return nicNumber;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);
        mFragmentManager = getSupportFragmentManager();
        customerId = getIntent().getIntExtra(MicrofinanceCustomers.KEY_CUSTOMER_ID, 0);
        nicNumber = getIntent().getStringExtra(MicrofinanceCustomers.KEY_NIC_NUMBER);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(getIntent().getStringExtra(MicrofinanceCustomers.KEY_FULL_NAME));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFragmentManager.beginTransaction().replace(R.id.fl_container, new LoanFragment(), "LoanFragment").commit();
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

    public void moveToLoanFormFrag(Bundle args) {
        Fragment fragment = new CustomerLoanFormFragment();
        fragment.setArguments(args);
        mFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.fl_container, fragment, "CustomerLoanFormFragment")
                .addToBackStack(null)
                .commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : mFragmentManager.getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }


}
