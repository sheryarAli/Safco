package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.shery.safco.R;

import customclasses.MicrofinanceCustomers;

/**
 * Created by shery on 11/16/2016.
 */

public class CustomerBankInfoFragment extends Fragment {
    private static final String TAG = CustomerBankInfoFragment.class.getSimpleName();
    private EditText bankAccNoEdt, bankAccTitleEdt, bankNameEdt, bankBranchNameEdt;
    private Spinner accountSpin;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_customer_bank_info, container, false);

        return v;
    }
    @Override
    public void onResume() {
        super.onResume();

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){

        }
    }
    public void addCustomerBankInfoData(Bundle bankData){
        bankData.putString(MicrofinanceCustomers.KEY_DET_BANK_ACC,bankAccNoEdt.getText().toString());

    }
}
