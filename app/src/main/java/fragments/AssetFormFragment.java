package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shery.safco.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import activities.AssetActivity;
import activities.FamilyMemberActivity;
import customclasses.Asset;
import customclasses.CommonConst;
import customclasses.MicrofinanceCustomers;
import databases.DBHandler;

/**
 * Created by shery on 11/25/2016.
 */

public class AssetFormFragment extends Fragment implements View.OnClickListener {

    private Button saveBtn;
    private EditText assetNameEdt, assetQtyEdt, assetValueEdt, assetOwnerEdt, notesEdt;
    private DBHandler dbHandler;
    private Toast toast;
    private Asset asset;
    private TextView addOnTxt;

    private void initViews(View view) {
        saveBtn = (Button) view.findViewById(R.id.saveBtn);
        assetNameEdt = (EditText) view.findViewById(R.id.assetNameEdt);
        assetQtyEdt = (EditText) view.findViewById(R.id.assetQtyEdt);
        assetValueEdt = (EditText) view.findViewById(R.id.assetValueEdt);
        assetOwnerEdt = (EditText) view.findViewById(R.id.assetOwnerEdt);
        notesEdt = (EditText) view.findViewById(R.id.notesEdt);
        addOnTxt = (TextView) view.findViewById(R.id.addOnTxt);
    }

    private void checkViewType() {
        Bundle args = getArguments();
        asset = (Asset) args.getSerializable("asset");
        if (asset != null) {
            assetNameEdt.setText(asset.getAssetName());
            assetQtyEdt.setText(asset.getAssetQuantity());
            assetValueEdt.setText(asset.getAssetValue());
            assetOwnerEdt.setText(asset.getAssetOwner());
            notesEdt.setText(asset.getNotes());
            saveBtn.setText("update");

        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_asset_form, container, false);
        initViews(v);
        toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        dbHandler = new DBHandler(getContext());
        checkViewType();
        addOnTxt.setText(new SimpleDateFormat("MMMM d, yyyy", Locale.US).format(Calendar.getInstance().getTime()));
        saveBtn.setOnClickListener(this);
        return v;
    }

    private boolean validateUi() {
        if (assetNameEdt.getText().toString().equalsIgnoreCase("")
                || assetQtyEdt.getText().toString().equalsIgnoreCase("")
                || assetValueEdt.getText().toString().equalsIgnoreCase("")
                || assetOwnerEdt.getText().toString().equalsIgnoreCase("")
                ) {
            toast.setText("Please fill all required fields");
            toast.show();
            return false;
        }
        return true;

    }

    @Override
    public void onClick(View view) {
        if (validateUi()) {

            if (view == saveBtn) {
                if (saveBtn.getText().toString().equalsIgnoreCase("save")) {
                    Asset asset = new Asset();
                    asset.setCustomerId(((AssetActivity) getActivity()).customerId);
                    asset.setAssetName(assetNameEdt.getText().toString());
                    asset.setAssetQuantity(assetQtyEdt.getText().toString());
                    asset.setAssetValue(assetValueEdt.getText().toString());
                    asset.setAssetOwner(assetOwnerEdt.getText().toString());
                    asset.setNotes(notesEdt.getText().toString());
                    if (dbHandler.insertAssetData(asset) != -1) {
                        Toast.makeText(getContext(), "Asset Inserted!", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }
                } else if (saveBtn.getText().toString().equalsIgnoreCase("update")) {

                    asset.setAssetName(assetNameEdt.getText().toString());
                    asset.setAssetQuantity(assetQtyEdt.getText().toString());
                    asset.setAssetValue(assetValueEdt.getText().toString());
                    asset.setAssetOwner(assetOwnerEdt.getText().toString());
                    asset.setNotes(notesEdt.getText().toString());
                    if (dbHandler.updateAssetData(asset) != 0) {
                        Toast.makeText(getContext(), "Asset Updated!", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }

                }


            }
        }

    }
}
