package activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shery.safco.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import adapters.GenericAdapter;
import adapters.HomePagerAdapter;
import customclasses.CommonConst;
import customclasses.CommonMethod;
import customclasses.Customer;
import customclasses.Employees;
import customclasses.MicrofinanceCustomers;
import customclasses.Region;
import customclasses.Stations;
import databases.DBHandler;
import fragments.CustomerBankInfoFragment;
import fragments.CustomerAddressFragment;
import fragments.CustomerInfoFragment;
import fragments.CustomerAdditionalInfoFragment;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesWithFallbackProvider;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by shery on 11/16/2016.
 */

public class CustomerFormActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = CustomerFormActivity.class.getSimpleName();
    private ViewPager mViewPager;
    private Button nextBtn, backBtn;
    HomePagerAdapter adapter;
    public ArrayList<Stations> stationList;
    public ArrayList<String> stationNameList;
    LocationGooglePlayServicesWithFallbackProvider locationGooglePlayServicesWithFallbackProvider;
    private DBHandler dbHandler;
    public static Customer customer;
    public static int customerId;
    public static Bundle customerDataBundle;
    private static boolean validateUi;
    private TextView addOnTxt;
    private String viewType;
    private ProgressDialog progressDialog;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    public Customer getCustomer() {
        return customer;
    }

    public String getViewType() {
        return viewType;
    }

    private void checkViewType() {
        customerId = getIntent().getIntExtra(MicrofinanceCustomers.KEY_CUSTOMER_ID, 0);
        viewType = getIntent().getStringExtra(CommonConst.VIEW_TYPE);
        if ((viewType != null && viewType.equalsIgnoreCase("view") && customerId != 0)) {
            Log.e(TAG, "customerId " + customerId);
            customer = dbHandler.getCustomer(customerId);

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_form);
        dbHandler = new DBHandler(this);
        locationGooglePlayServicesWithFallbackProvider = new LocationGooglePlayServicesWithFallbackProvider(this);
        checkViewType();
        customerDataBundle = new Bundle();
        stationList = new ArrayList<Stations>();
        stationNameList = new ArrayList<String>();
        stationList = dbHandler.getStationData();
        for (Stations station : stationList) {
            Log.e(TAG, "" + station.getStationName());
            stationNameList.add(station.getStationName());
        }
        Typeface font = Typeface.createFromAsset(getResources().getAssets(),
                "fonts/fontawesome-webfont.ttf");

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        mViewPager = (ViewPager) findViewById(R.id.customerViewPager);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        backBtn = (Button) findViewById(R.id.backBtn);
        addOnTxt = (TextView) findViewById(R.id.addOnTxt);
        nextBtn.setTypeface(font);
        backBtn.setTypeface(font);
        nextBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        getSupportActionBar().setTitle("Customer Form");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        mViewPager.setOffscreenPageLimit(3);
        addOnTxt.setText(new SimpleDateFormat("MMMM d, yyyy", Locale.US).format(Calendar.getInstance().getTime()));
        setupViewPager(mViewPager);
        changeBtnSet(mViewPager.getCurrentItem());


    }

    @Override
    public void onClick(View view) {
        if (view == nextBtn) {
            int pos = mViewPager.getCurrentItem();
            if (pos != adapter.getCount() - 1) {

                switch (pos) {
                    case 0:
                        if (viewType != null && viewType.equalsIgnoreCase("view")) {
                            validateUi = ((CustomerInfoFragment) adapter.getItem(pos)).addCustomerInfoDataInBundleWithoutCheck(customerDataBundle);
                        } else {
                            validateUi = ((CustomerInfoFragment) adapter.getItem(pos)).addCustomerInfoDataInBundle(customerDataBundle);
                        }


                        break;
                    case 1:
                        validateUi = ((CustomerAdditionalInfoFragment) adapter.getItem(pos)).addCustomerAddDataInBundle(customerDataBundle);
                        break;

                }
                if (validateUi) {
                    mViewPager.setCurrentItem(++pos);
                    changeBtnSet(pos);
                }
            } else {

                validateUi = ((CustomerAddressFragment) adapter.getItem(pos)).addCustomerAddressDataInBundle(customerDataBundle);
                if (validateUi && CommonMethod.isGpsOn(this)) {
                    //Insert Data In Table
                    if (viewType != null && viewType.equalsIgnoreCase("view")) {
                        customerDataBundle.putInt(MicrofinanceCustomers.KEY_CUSTOMER_ID, customerId);
                        int res = dbHandler.updateCustomerData(customerDataBundle);
                        if (res != -1) {
                            Toast.makeText(this, "Customer Updated Successfully! ", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    } else {

                        Location location = SmartLocation.with(this).location().getLastLocation();
                        double lat = 0, longi = 0;
                        if (location != null && location.getLatitude() != 0.0) {
                            lat = location.getLatitude();
                            longi = location.getLongitude();
                        }
                        customerDataBundle.putString(MicrofinanceCustomers.KEY_CUSTOMER_GPS_LOC, lat + ", " + longi);
                        long res = dbHandler.insertCustomerData(customerDataBundle);
                        if (res != -1) {

                            Toast.makeText(CustomerFormActivity.this, "Customer Inserted Successfully! ", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        //TODO OLD Location Code
                        /*progressDialog = new ProgressDialog(this);
                        progressDialog.setMessage("Searching For GPS Coordinates....");
                        progressDialog.show();
                        SmartLocation.with(this)
                                .location(locationGooglePlayServicesWithFallbackProvider)
                                .config(LocationParams.BEST_EFFORT).oneFix().start(new OnLocationUpdatedListener() {
                            @Override
                            public void onLocationUpdated(Location location) {
                                progressDialog.dismiss();


                            }
                        });*/

                    }
                }


            }

        } else if (view == backBtn) {
            int pos = mViewPager.getCurrentItem();
            if (pos != 0) {
                mViewPager.setCurrentItem(--pos);
                changeBtnSet(pos);
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                int pos = mViewPager.getCurrentItem();
                if (pos != 0) {
                    mViewPager.setCurrentItem(--pos);
                    changeBtnSet(pos);
                } else {
                    finish();
                }
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {

        adapter = new HomePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CustomerInfoFragment(), "Customer Information");
        adapter.addFragment(new CustomerAdditionalInfoFragment(), "Customer Personal Information");
        adapter.addFragment(new CustomerAddressFragment(), "Customer Address");
        viewPager.setAdapter(adapter);

    }

    public void changeBtnSet(int pos) {
        if (pos == 0) {
            backBtn.setEnabled(false);

        } else if (pos == adapter.getCount() - 1) {
            nextBtn.setText(getResources().getString(R.string.ic_save));
        } else {
            backBtn.setEnabled(true);
            nextBtn.setText(getResources().getString(R.string.ic_arrow_r));
        }

    }


}
