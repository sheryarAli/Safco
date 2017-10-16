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
import databases.DBHandler;
import fragments.DocumentAddFragment;
import fragments.DocumentFragment;
import fragments.GuaranteerFormFragment;
import fragments.GuaranteerFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by shery on 12/15/2016.
 */

public class DocumentActivity extends AppCompatActivity {
    private static final String TAG = DocumentActivity.class.getSimpleName();
    private FragmentManager mFragmentManager;
    private DBHandler dbHandler;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        mFragmentManager = getSupportFragmentManager();
        dbHandler = new DBHandler(this);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(getIntent().getCharSequenceExtra(MicrofinanceCustomers.KEY_FULL_NAME));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFragmentManager.beginTransaction().replace(R.id.fl_container, new DocumentFragment(), "DocumentFragment").commit();


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

    public void moveToAddDocFragment(){
        Fragment frag = new DocumentAddFragment();

        mFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.fl_container, frag, "DocumentAddFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (Fragment fragment: mFragmentManager.getFragments()){

            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
