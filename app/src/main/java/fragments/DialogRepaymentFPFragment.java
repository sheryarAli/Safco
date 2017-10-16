package fragments;

import android.app.Dialog;
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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shery.safco.R;

import java.util.Map;

import SecuGen.FDxSDKPro.JSGFPLib;
import SecuGen.FDxSDKPro.SGDeviceInfoParam;
import SecuGen.FDxSDKPro.SGFDxDeviceName;
import SecuGen.FDxSDKPro.SGFDxSecurityLevel;
import customclasses.CommonMethod;
import customclasses.Repayment;
import databases.DbHandler2;
import secugen.FingerPrintReader;
import utilies.HexConversion;

/**
 * Created by shery on 8/30/2017.
 */

public class DialogRepaymentFPFragment extends DialogFragment implements View.OnClickListener{
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private static final String TAG = "DialogRepFPFragment";
    private ImageView imageViewFingerprint;
    private Button matchFPBtn, retryBtn, captureBtn;
    private IntentFilter filter;
    HexConversion conversion;
    private JSGFPLib sgfplib;
    private boolean isDeviceRegistered = false;
    FingerPrintReader fingerPrintReader1;
    private PendingIntent mPermissionIntent;
    private byte[] mRegisterImage, mCaptureTemplate, mVerifyTemplate;
    private int[] mMaxTemplateSize;
    private int mImageWidth;
    private int mImageHeight;
    private int mImageDPI;
    private Bitmap fingerBmp;
    private String mRegisterTemplateStr;
    private Toast toast;

    private DbHandler2 dbHandler;

    //TODO onCreateDialog
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LinearLayout root = new LinearLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        final Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    //TODO onCreateView

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_repayment_fp, container, false);
        imageViewFingerprint = (ImageView) view.findViewById(R.id.imageViewFingerprint);
        matchFPBtn = (Button) view.findViewById(R.id.matchFPBtn);
        retryBtn = (Button) view.findViewById(R.id.retryBtn);
        captureBtn = (Button) view.findViewById(R.id.captureBtn);
        matchFPBtn.setOnClickListener(this);
        retryBtn.setOnClickListener(this);
        captureBtn.setOnClickListener(this);
        dbHandler = new DbHandler2(getContext());
        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        conversion = new HexConversion();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        getActivity().registerReceiver(mUsbAttachedAndDetReceiver, intentFilter);
        usbPermission();
        initiateDeviceAndView();
        return view;
    }
    //TODO mUsbAttachedAndDetReceiver
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
    //TODO usbPermission
    private void usbPermission() {
        mPermissionIntent = PendingIntent.getBroadcast(getContext(), 0, new Intent(
                ACTION_USB_PERMISSION), 0);
        filter = new IntentFilter(ACTION_USB_PERMISSION);
        getActivity().registerReceiver(mUsbReceiver, filter);
        sgfplib = new JSGFPLib(
                (UsbManager) getContext().getSystemService(Context.USB_SERVICE));
        long error = sgfplib.Init(SGFDxDeviceName.SG_DEV_AUTO);
    }
    //TODO initiateDeviceAndView
    public void initiateDeviceAndView() {
        UsbDevice usbDevice = sgfplib.GetUsbDevice();
        if (usbDevice != null) {
            sgfplib.GetUsbManager().requestPermission(usbDevice, mPermissionIntent);
            long error = sgfplib.OpenDevice(0);
            fingerPrintReader1 = new FingerPrintReader(imageViewFingerprint,
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
    //TODO mUsbReceiver
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

                            Log.d(TAG, "Vender ID" + device.getVendorId());

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
    //TODO AsyncMatch
    private class AsyncMatch extends AsyncTask<Void, Void , Boolean>{
        private Map<String, String> loanIdWithFPMap;
        private ProgressDialog progressDialog;
        private int loanId;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Searching for match...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            loanIdWithFPMap = dbHandler.getFPWithLoanId(loanId);
            while(!loanIdWithFPMap.isEmpty()){

                boolean[] matched = new boolean[1];

                mVerifyTemplate = Base64.decode(loanIdWithFPMap.get(Repayment.KEY_FP_IMAGE_TEMP), Base64.DEFAULT);
                /*mVerifyTemplate = Base64.decode("Rk1SACAyMAABCgAKADUAAAEEASwAxQDFAQAAAAAngG8AHWMAQJ4AIQUAgIgAImMAgMMAOK8AQD0A\n" +
                        "PXEAQOsAQqkAgIEAUQgAgMsAU1QAQG8AWAsAgPIAWaUAQCoAYRgAgJ0AYgEAQCcAZIQAQDUAaBUA\n" +
                        "QDUAeREAgFAAfnIAQJcAhF8AgHAAjAwAgBoAjh0AQFEAnBcAQIwAngQAgGIApxIAQGgAt3IAQN8A\n" +
                        "wZsAgHcAwg0AQMUAxkgAQMUAypkAgK4A1ksAQI4A1gQAQOMA25MAgNQA3TsAQCgA5HkAgLEA8pMA\n" +
                        "gF8A9HwAgI8A9gYAQJEA/6UAgKgBEYkAQBwBGB8AQCABGnUAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                        "AAA=", Base64.DEFAULT);*/
                loanId = Integer.parseInt(loanIdWithFPMap.get(Repayment.KEY_LOAN_ID));
                sgfplib.MatchTemplate(mCaptureTemplate, mVerifyTemplate, SGFDxSecurityLevel.SL_NORMAL, matched);
                if (matched[0]) {
                    return true;
                }

                loanIdWithFPMap = dbHandler.getFPWithLoanId(loanId);
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressDialog.dismiss();
            if (aBoolean){
                Log.d(TAG, "onPostExecute: Match Successfully found!");
                ((RepaymentFragment) getParentFragment()).setLoanId(loanId);
                ((RepaymentFragment) getParentFragment()).loadMore(2);
                dismiss();

            }else{
                ((RepaymentFragment) getParentFragment()).loadMore(-1);
                Log.d(TAG, "onPostExecute: Match not found!");
                dismiss();
            }

        }
    }
    //TODO onStop
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


        //TODO onClick
    @Override
    public void onClick(View view) {
        if (view == matchFPBtn){
            /*if (getParentFragment() instanceof RepaymentFragment){
                Log.d(TAG, "onClick: getParentFragment() instanceof RepaymentFragment");
            }*/
            new AsyncMatch().execute();


        }
        if (view == retryBtn){
            initiateDeviceAndView();
            if (mRegisterImage != null && mRegisterImage.length > 0)
                imageViewFingerprint.setImageBitmap((CommonMethod.byteArrayToBitmap(mRegisterImage)));
        }

        if (view == captureBtn) {
            if (isDeviceRegistered) {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        fingerPrintReader1.readFingerPrint();
                        mCaptureTemplate = fingerPrintReader1.getHexTemplate();
                        String base64Str = Base64.encodeToString(mCaptureTemplate, Base64.DEFAULT);
                        fingerBmp = fingerPrintReader1.toGrayscale(fingerPrintReader1.getFPBitMap());
                        imageViewFingerprint.setImageBitmap(fingerBmp);
                        mRegisterImage = CommonMethod.bitmapToByteArray(fingerBmp);
                        matchFPBtn.setEnabled(true);


                    }
                });

            }
        }
    }
}
