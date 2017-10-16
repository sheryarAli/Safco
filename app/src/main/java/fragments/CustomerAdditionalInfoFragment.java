package fragments;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.shery.safco.R;

import SecuGen.FDxSDKPro.JSGFPLib;
import SecuGen.FDxSDKPro.SGDeviceInfoParam;
import SecuGen.FDxSDKPro.SGFDxDeviceName;
import activities.CustomerFormActivity;
import customclasses.Customer;
import customclasses.MicrofinanceCustomers;
import customclasses.OrganizationEmployees;
import databases.DBHandler;
import secugen.FingerPrintReader;
import utilies.HexConversion;

/**
 * Created by shery on 11/16/2016.
 */

public class CustomerAdditionalInfoFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = CustomerAdditionalInfoFragment.class.getSimpleName();
    private Spinner maritalSpin, religionSpin, houseStatusSpin, houseTypeSpin, educationSpin;
    /*    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";*/
    private EditText mobileEdt;
    private Button mButtonRegister = null;
    private boolean isDeviceRegistered = false;
    private PendingIntent mPermissionIntent;
    private ImageView mImageViewFingerprint;
    private IntentFilter filter;
    HexConversion conversion;
    private DBHandler dbHandler;
    private JSGFPLib sgfplib;
    FingerPrintReader fingerPrintReader1;
    private byte[] mRegisterImage, mRegisterTemplate;
    private Toast toast;
/*    private int[] mMaxTemplateSize;
    private int mImageWidth;
    private int mImageHeight;
    private int mImageDPI;*/


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    private void checkViewType() {
        Customer customer = ((CustomerFormActivity) getActivity()).customer;
        if (customer != null && ((CustomerFormActivity) getActivity()).getViewType() != null) {
            maritalSpin.setSelection(customer.getMaritalStatus());
            religionSpin.setSelection(customer.getReligion());
            houseStatusSpin.setSelection(customer.getHouseStatus());
            houseTypeSpin.setSelection(customer.getHouseType());
            educationSpin.setSelection(customer.getEducation());
            mobileEdt.setText(customer.getMobileNumber());


        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_additional_info, container, false);
        toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        maritalSpin = (Spinner) v.findViewById(R.id.maritalSpin);
        religionSpin = (Spinner) v.findViewById(R.id.religionSpin);
        houseStatusSpin = (Spinner) v.findViewById(R.id.houseStatusSpin);
        houseTypeSpin = (Spinner) v.findViewById(R.id.houseTypeSpin);
        educationSpin = (Spinner) v.findViewById(R.id.educationSpin);
/*        mButtonRegister = (Button) v.findViewById(R.id.regPrintBtn);
        mImageViewFingerprint = (ImageView) v.findViewById(R.id.imageViewFingerprint);
        mButtonRegister.setOnClickListener(this);
        conversion = new HexConversion();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        getActivity().registerReceiver(mUsbAttachedAndDetReceiver, intentFilter);*/

        mobileEdt = (EditText) v.findViewById(R.id.mobileEdt);
        checkViewType();
/*        usbPermission();
        initiateDeviceAndView();*/

        return v;
    }

    @Override
    public void onClick(View view) {
        if (view == mButtonRegister) {
            if (isDeviceRegistered) {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        fingerPrintReader1.readFingerPrint();

                        mRegisterTemplate = fingerPrintReader1.getHexTemplate();

                        String tempStr = conversion.getHexString(mRegisterTemplate);
                        Log.e(TAG, "mRegisterTemplate String: " + tempStr);
                        mImageViewFingerprint.setImageBitmap(fingerPrintReader1
                                .toGrayscale(fingerPrintReader1.getFPBitMap()));

                    }
                });
            }
        }
        /*if (dbHandler.insertFP(mRegisterTemplate) > 0) {
                            Log.e(TAG, "Finger Print Successfully inserted");
                        }*/
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
        /*getActivity().unregisterReceiver(mUsbAttachedAndDetReceiver);*/
    }

    private boolean validateUi() {
        if (mobileEdt.getText().toString().equalsIgnoreCase("") && mobileEdt.getText().toString().length() == 0) {

            toast.setText("Please fill all required fields");
            toast.show();
            return false;
        } /*else if (!emailEdt.getText().toString().matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")) {
            toast.setText("Email Not Valid");
            toast.show();

            return false;
        }*/
        return true;
    }

    /*private final BroadcastReceiver mUsbAttachedAndDetReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(intent.getAction())) {
                usbPermission();
                initiateDeviceAndView();
                Log.e(TAG, "onReceive: Usb Attached");
            }
            if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(intent.getAction())){
                Log.e(TAG, "onReceive: USB Detached");
//                fingerPrintReader1 = null;

            }
        }
    };*/


    public boolean addCustomerAddDataInBundle(Bundle customerAddData) {
        Log.e(TAG, "addCustomerAddDataInBundle");
        if (validateUi()) {
            customerAddData.putInt(MicrofinanceCustomers.KEY_MARITAL_STATUS, maritalSpin.getSelectedItemPosition());
            customerAddData.putInt(MicrofinanceCustomers.KEY_RELIGION, religionSpin.getSelectedItemPosition());
            customerAddData.putInt(MicrofinanceCustomers.KEY_HOUSE_STATUS, houseStatusSpin.getSelectedItemPosition());
            customerAddData.putInt(MicrofinanceCustomers.KEY_HOUSE_TYPE, houseTypeSpin.getSelectedItemPosition());
            customerAddData.putInt(MicrofinanceCustomers.KEY_EDUCATION, educationSpin.getSelectedItemPosition());
            customerAddData.putString(OrganizationEmployees.KEY_MOBILENO, mobileEdt.getText().toString());

            return true;
        }

        return false;
    }

    /*public void initiateDeviceAndView() {
        UsbDevice usbDevice = sgfplib.GetUsbDevice();
        if (usbDevice != null) {
            sgfplib.GetUsbManager().requestPermission(usbDevice, mPermissionIntent);
            long error = sgfplib.OpenDevice(0);

            fingerPrintReader1 = new FingerPrintReader(mImageViewFingerprint,
                    sgfplib);


            mMaxTemplateSize = new int[1];
            sgfplib.GetMaxTemplateSize(mMaxTemplateSize);
            mRegisterTemplate = new byte[mMaxTemplateSize[0]];
            SGDeviceInfoParam deviceInfo = new SGDeviceInfoParam();
            mImageWidth = deviceInfo.imageWidth;
            mImageHeight = deviceInfo.imageHeight;
            isDeviceRegistered = true;
        }
    }

    private void usbPermission() {
        mPermissionIntent = PendingIntent.getBroadcast(getContext(), 0, new Intent(
                ACTION_USB_PERMISSION), 0);
        filter = new IntentFilter(ACTION_USB_PERMISSION);
        getActivity().registerReceiver(mUsbReceiver, filter);
        sgfplib = new JSGFPLib(
                (UsbManager) getContext().getSystemService(Context.USB_SERVICE));
        long error = sgfplib.Init(SGFDxDeviceName.SG_DEV_AUTO);
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice) intent
                            .getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(
                            UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {

                            Log.d(TAG, "Vender DI" + device.getVendorId());

                            Log.d(TAG, "Producat ID " + device.getProductId());

                        } else
                            Log.e(TAG,
                                    "mUsbReceiver.onReceive() Device is null");
                    } else
                        Log.e(TAG,
                                "mUsbReceiver.onReceive() permission denied for device "
                                        + device);
                }
            }
        }
    };*/

}
