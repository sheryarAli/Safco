package fragments;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.shery.safco.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Locale;

import SecuGen.FDxSDKPro.JSGFPLib;
import SecuGen.FDxSDKPro.SGDeviceInfoParam;
import SecuGen.FDxSDKPro.SGFDxDeviceName;
import SecuGen.FDxSDKPro.SGFDxSecurityLevel;
import activities.MainActivity;
import customclasses.CommonMethod;
import customclasses.NicTextWatcher;
import customclasses.PhpConnectivity;
import secugen.FingerPrintReader;
import utilies.HexConversion;

/**
 * Created by Haseeb on 28/04/2017.
 */

public class SyncupFPAndNic extends Fragment implements View.OnClickListener {
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private IntentFilter filter;
    HexConversion conversion;
    private static final String URL1 = "https://mms.safcosupport.org/employees/tabData/login_demo.php";
    private static final String URL2 = "https://mms.safcosupport.org/employees/tabData/thumbVerification.php";
    private JSGFPLib sgfplib;
    private boolean isDeviceRegistered = false;
    FingerPrintReader fingerPrintReader1;
    private PendingIntent mPermissionIntent;
    private byte[] mRegisterImage, mCaptureTemplate, mVerifyTemplate;
    private int[] mMaxTemplateSize;
    private int mImageWidth;
    private int mImageHeight;
    private int mImageDPI;
    private String cnic, base64Temp;
    private static final String TAG = SyncupFPAndNic.class.getSimpleName();
    private DatePickerDialog mDatePickerDialog;
    private SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private EditText nicEdt;
    private Bitmap fingerBmp;
    Toast toast;
    Button captureBtn, submitbtn, retryBtn, verifyBtn;
    private ImageView imageViewCapFingerprint;
    private String mRegisterTemplateStr;
    private AlertDialog alertDialog;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.syncup_nic_and_fprint, container, false);
        imageViewCapFingerprint = (ImageView) v.findViewById(R.id.imageViewCapFingerprint);
        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        nicEdt = (EditText) v.findViewById(R.id.etnic);
        submitbtn = (Button) v.findViewById(R.id.submitBtn);
        retryBtn = (Button) v.findViewById(R.id.retryBtn);
        captureBtn = (Button) v.findViewById(R.id.captureBtn);
        verifyBtn = (Button) v.findViewById(R.id.verifyBtn);

        nicEdt.setFilters(NicTextWatcher.getFilter());
        nicEdt.setText("");
        nicEdt.addTextChangedListener(NicTextWatcher.getInstance());
        submitbtn.setOnClickListener(this);
        retryBtn.setOnClickListener(this);
        captureBtn.setOnClickListener(this);
        verifyBtn.setOnClickListener(this);

        conversion = new HexConversion();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        getActivity().registerReceiver(mUsbAttachedAndDetReceiver, intentFilter);

        usbPermission();
        initiateDeviceAndView();


        return v;
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

    private final BroadcastReceiver mUsbAttachedAndDetReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(intent.getAction())) {
                usbPermission();
                initiateDeviceAndView();
                Log.e(TAG, "onReceive: Usb Attached");
            }
            if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(intent.getAction())) {
                Log.e(TAG, "onReceive: USB Detached");
                fingerPrintReader1 = null;

            }
        }
    };

    public void initiateDeviceAndView() {
        UsbDevice usbDevice = sgfplib.GetUsbDevice();
        if (usbDevice != null) {
            sgfplib.GetUsbManager().requestPermission(usbDevice, mPermissionIntent);
            long error = sgfplib.OpenDevice(0);
            fingerPrintReader1 = new FingerPrintReader(imageViewCapFingerprint,
                    sgfplib);

            mMaxTemplateSize = new int[1];
            sgfplib.GetMaxTemplateSize(mMaxTemplateSize);
            mCaptureTemplate = new byte[mMaxTemplateSize[0]];
            SGDeviceInfoParam deviceInfo = new SGDeviceInfoParam();
            mImageWidth = deviceInfo.imageWidth;
            mImageHeight = deviceInfo.imageHeight;
            isDeviceRegistered = true;
        }
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
    };


    @Override
    public void onClick(View view) {
        if (view == submitbtn) {

            cnic = nicEdt.getText().toString();

            if (validateUi()) {
                new VerifyAsync().execute(0);
            } else {
                toast.setText("Kindly enter your CNIC....");
            }

        }
        if (view == retryBtn) {
            initiateDeviceAndView();
            if (mRegisterImage != null && mRegisterImage.length > 0)
                imageViewCapFingerprint.setImageBitmap((CommonMethod.byteArrayToBitmap(mRegisterImage)));

        }
        if (view == captureBtn) {
            if (isDeviceRegistered) {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        fingerPrintReader1.readFingerPrint();
                        mCaptureTemplate = fingerPrintReader1.getHexTemplate();
                        fingerBmp = fingerPrintReader1.toGrayscale(fingerPrintReader1.getFPBitMap());
                        imageViewCapFingerprint.setImageBitmap(fingerBmp);
                        mRegisterImage = CommonMethod.bitmapToByteArray(fingerBmp);
                        verifyBtn.setEnabled(true);


                    }
                });

            }
        }

        if (view == verifyBtn) {

            boolean[] matched = new boolean[1];
            sgfplib.MatchTemplate(mCaptureTemplate, mVerifyTemplate, SGFDxSecurityLevel.SL_NORMAL, matched);
            if (matched[0]) {
                /*toast.setText("Match Confirmed");
                toast.show();*/
                new VerifyAsync().execute(1);
            } else {
                toast.setText("Match Not Confirmed");
                toast.show();
            }
        }

    }

    private boolean validateUi() {
        if (CommonMethod.checkEmpty(nicEdt) || !nicEdt.getText().toString().matches("\\d{5}-\\d{7}-\\d")) {
            nicEdt.setError("Invalid CNIC");
            return false;
        }
        return true;
    }


    @Override
    public void onStop() {
        super.onStop();
        try {
            getActivity().unregisterReceiver(mUsbAttachedAndDetReceiver);
            getActivity().unregisterReceiver(mUsbReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private class VerifyAsync extends AsyncTask<Integer, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Verification");
            progressDialog.setMessage("Fetching Data..");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Integer... params) {
            String jsonData = "";
            try {
                JSONObject json = new JSONObject();
                json.put("cnic", cnic);

                jsonData = json.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            switch (params[0]) {
                case 0:
                    return PhpConnectivity.getStringFromUrl(URL1, PhpConnectivity.getParams(jsonData));
                case 1:
                    return PhpConnectivity.getStringFromUrl(URL2, PhpConnectivity.getParams(jsonData));
            }
            return "";

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.e(TAG, "response: " + result);

            if (result == null || result.equalsIgnoreCase("")) {
                toast.setText("Response is null or empty");
                toast.show();
                return;
            } else {

                try {
                    JSONObject jsonObject = new JSONObject(result);


                    if (jsonObject.has("Pin")) {
                        mVerifyTemplate = Base64.decode(jsonObject.getString("Pin"), Base64.DEFAULT);
                        captureBtn.setEnabled(true);
                        retryBtn.setEnabled(true);
                    } else {
                        ((MainActivity) getActivity()).moveToLoanVeriForm(jsonObject.toString());
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    toast.setText(result);
                    toast.show();
                    Log.e(TAG, "onPostExecute: " + result);
                }

            }


        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");


    }


}
