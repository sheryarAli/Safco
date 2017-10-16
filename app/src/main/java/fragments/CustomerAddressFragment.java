package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.shery.safco.R;

import activities.CustomerFormActivity;
import customclasses.Customer;
import customclasses.MicrofinanceCustomers;

/**
 * Created by shery on 11/16/2016.
 */

public class CustomerAddressFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = CustomerAddressFragment.class.getSimpleName();

    private EditText countryEdt, countryEdt2, stateEdt, stateEdt2, districtEdt,
            districtEdt2, talukaEdt, talukaEdt2, ucEdt, ucEdt2, mohallaEdt, mohallaEdt2,
            cityEdt, addressEdt, cityEdt2, addressEdt2, noyEdt, notesEdt;
    private CheckBox sameBox;

    private Toast toast;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }
    private void checkViewType(){

        Customer customer = ((CustomerFormActivity) getActivity()).customer;
        if (customer != null && ((CustomerFormActivity) getActivity()).getViewType()!=null) {
            countryEdt.setText(customer.getAddress_Permanent_Country());
            countryEdt2.setText(customer.getAddress_Present_Country());
            stateEdt.setText(customer.getAddress_Permanent_State());
            stateEdt2.setText(customer.getAddress_Present_State());
            districtEdt.setText(customer.getAddress_Permanent_District_Name());
            districtEdt2.setText(customer.getAddress_Present_District_Name());
            talukaEdt.setText(customer.getAddress_Permanent_Taluka_Name());
            talukaEdt2.setText(customer.getAddress_Present_Taluka_Name());
            ucEdt.setText(customer.getAddress_Permanent_UC_Name());
            ucEdt2.setText(customer.getAddress_Present_UC_Name());
            mohallaEdt.setText(customer.getAddress_Permanent_Mohalla_Village());
            mohallaEdt2.setText(customer.getAddress_Present_Mohalla_Village());
            cityEdt.setText(customer.getAddress_Permanent_City());
            cityEdt2.setText(customer.getAddress_Present_City());
            addressEdt.setText(customer.getAddress_Permanent_Address());
            addressEdt2.setText(customer.getAddress_Present_Address());
            noyEdt.setText(customer.getAddress_Present_NumberOfYears());
            notesEdt.setText(customer.getNotes());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_address, container, false);
        toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        sameBox = (CheckBox) v.findViewById(R.id.sameBox);
        countryEdt = (EditText) v.findViewById(R.id.countryEdt);
        countryEdt2 = (EditText) v.findViewById(R.id.countryEdt2);
        stateEdt = (EditText) v.findViewById(R.id.stateEdt);
        stateEdt2 = (EditText) v.findViewById(R.id.stateEdt2);
        districtEdt = (EditText) v.findViewById(R.id.districtEdt);
        districtEdt2 = (EditText) v.findViewById(R.id.districtEdt2);
        talukaEdt = (EditText) v.findViewById(R.id.talukaEdt);
        talukaEdt2 = (EditText) v.findViewById(R.id.talukaEdt2);
        ucEdt = (EditText) v.findViewById(R.id.ucEdt);
        ucEdt2 = (EditText) v.findViewById(R.id.ucEdt2);
        mohallaEdt = (EditText) v.findViewById(R.id.mohallaEdt);
        mohallaEdt2 = (EditText) v.findViewById(R.id.mohallaEdt2);
        cityEdt = (EditText) v.findViewById(R.id.cityEdt);
        addressEdt = (EditText) v.findViewById(R.id.addressEdt);
        notesEdt = (EditText) v.findViewById(R.id.notesEdt);

        cityEdt2 = (EditText) v.findViewById(R.id.cityEdt2);
        addressEdt2 = (EditText) v.findViewById(R.id.addressEdt2);
        noyEdt = (EditText) v.findViewById(R.id.noyEdt);

        checkViewType();


        sameBox.setOnClickListener(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private boolean validateUi() {
        if (
                ((districtEdt.getText().toString().equalsIgnoreCase("") && districtEdt.getText().toString().length() == 0)
                        || (talukaEdt.getText().toString().equalsIgnoreCase("") && talukaEdt.getText().toString().length() == 0)
                        || (ucEdt.getText().toString().equalsIgnoreCase("") && ucEdt.getText().toString().length() == 0)
                        || (mohallaEdt.getText().toString().equalsIgnoreCase("") && mohallaEdt.getText().toString().length() == 0)
                        || (cityEdt.getText().toString().equalsIgnoreCase("") && cityEdt.getText().toString().length() == 0)
                        || (addressEdt.getText().toString().equalsIgnoreCase("") && addressEdt.getText().toString().length() == 0)
                        || (noyEdt.getText().toString().equalsIgnoreCase("")))
                        && ((districtEdt2.getText().toString().equalsIgnoreCase("") && districtEdt2.getText().toString().length() == 0)
                        || (talukaEdt2.getText().toString().equalsIgnoreCase("") && talukaEdt2.getText().toString().length() == 0)
                        || (ucEdt2.getText().toString().equalsIgnoreCase("") && ucEdt2.getText().toString().length() == 0)
                        || (mohallaEdt2.getText().toString().equalsIgnoreCase("") && mohallaEdt2.getText().toString().length() == 0)
                        || (cityEdt2.getText().toString().equalsIgnoreCase("") && cityEdt2.getText().toString().length() == 0)
                        || (addressEdt2.getText().toString().equalsIgnoreCase("") && addressEdt2.getText().toString().length() == 0))
                ) {

            toast.setText("Please fill all required fields");
            toast.show();
            return false;

        }

        return true;
    }

    @Override
    public void onClick(View view) {
        if (((CheckBox) view).isChecked()) {
            countryEdt2.setText(countryEdt.getText().toString());
            stateEdt2.setText(stateEdt.getText().toString());
            districtEdt2.setText(districtEdt.getText().toString());
            talukaEdt2.setText(talukaEdt.getText().toString());
            ucEdt2.setText(ucEdt.getText().toString());
            mohallaEdt2.setText(mohallaEdt.getText().toString());
            cityEdt2.setText(cityEdt.getText().toString());
            addressEdt2.setText(addressEdt.getText().toString());
        }
    }

    public boolean addCustomerAddressDataInBundle(Bundle customerAddressData) {
        Log.e(TAG, "addCustomerAddressDataInBundle");
        if (validateUi()) {
            customerAddressData.putString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_COUNTRY, countryEdt.getText().toString());
            customerAddressData.putString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_DISTRICT_NAME, districtEdt.getText().toString());
            customerAddressData.putString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_STATE, stateEdt.getText().toString());
            customerAddressData.putString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_TALUKA_NAME, talukaEdt.getText().toString());
            customerAddressData.putString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_UC_NAME, ucEdt.getText().toString());
            customerAddressData.putString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_MOHALLA_VILLAGE, mohallaEdt.getText().toString());
            customerAddressData.putString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_CITY, cityEdt.getText().toString());
            customerAddressData.putString(MicrofinanceCustomers.KEY_ADDRESS_PERMANENT_ADDRESS, addressEdt.getText().toString());
            customerAddressData.putString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_COUNTRY, countryEdt2.getText().toString());
            customerAddressData.putString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_STATE, stateEdt2.getText().toString());
            customerAddressData.putString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_DISTRICT_NAME, districtEdt2.getText().toString());
            customerAddressData.putString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_TALUKA_NAME, talukaEdt2.getText().toString());
            customerAddressData.putString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_UC_NAME, ucEdt2.getText().toString());
            customerAddressData.putString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_MOHALLA_VILLAGE, mohallaEdt2.getText().toString());
            customerAddressData.putString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_CITY, cityEdt2.getText().toString());
            customerAddressData.putString(MicrofinanceCustomers.KEY_ADDRESS_PRESENT_ADDRESS, addressEdt2.getText().toString());
            customerAddressData.putString(MicrofinanceCustomers.KEY_NOTES, notesEdt.getText().toString());
            customerAddressData.putInt(MicrofinanceCustomers.KEY_PRESENT_NUM_OF_YEARS, ((noyEdt.getText().toString().length() > 0)) ? Integer.valueOf(noyEdt.getText().toString()) : 0);
            return true;
        }

        return false;

    }
}
