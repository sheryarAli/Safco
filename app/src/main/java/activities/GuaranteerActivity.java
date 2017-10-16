package activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.shery.safco.R;

import customclasses.MicrofinanceCustomers;
import databases.DBHandler;
import fragments.AssetFormFragment;
import fragments.GuaranteerFormFragment;
import fragments.GuaranteerFragment;
import interfaces.ICustomer;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by shery on 11/24/2016.
 */

public class GuaranteerActivity extends AppCompatActivity {
    private static final String TAG = GuaranteerActivity.class.getSimpleName();
    private FragmentManager mFragmentManager;
    private DBHandler dbHandler;
    public int customerId;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    public DBHandler getDbHandler() {
        return dbHandler;
    }

    public int getCustomerId() {
        return customerId;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guaranteer);
        mFragmentManager = getSupportFragmentManager();
        dbHandler = new DBHandler(this);
        customerId = getIntent().getIntExtra(MicrofinanceCustomers.KEY_CUSTOMER_ID, -1);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(getIntent().getCharSequenceExtra(MicrofinanceCustomers.KEY_FULL_NAME));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFragmentManager.beginTransaction().replace(R.id.fl_container, new GuaranteerFragment(), "GuaranteerFragment").commit();
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





    public void moveToGuaranteerForm(Bundle args) {
        Fragment frag = new GuaranteerFormFragment();
        frag.setArguments(args);

        mFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.fl_container, frag, "GuaranteerFormFragment")
                .addToBackStack(null)
                .commit();
    }


}
