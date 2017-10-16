package activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gc.materialdesign.views.ButtonFloat;
import com.shery.safco.R;

import customclasses.MicrofinanceCustomers;
import fragments.AssetFragment;
import fragments.CustomerGroupFormFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by shery on 12/9/2016.
 */

public class GroupActivity extends AppCompatActivity {
    private static final String TAG = GroupActivity.class.getSimpleName();
    private FragmentManager mFragmentManager;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        mFragmentManager = getSupportFragmentManager();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Group Form");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFragmentManager.beginTransaction().replace(R.id.fl_container, new CustomerGroupFormFragment(), "CustomerGroupFormFragment").commit();


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


}
