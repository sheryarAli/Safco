package fragments;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shery.safco.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import SecuGen.FDxSDKPro.JSGFPLib;
import SecuGen.FDxSDKPro.SGDeviceInfoParam;
import SecuGen.FDxSDKPro.SGFDxDeviceName;
import activities.CustomerFormActivity;
import customclasses.CommonMethod;
import customclasses.Customer;
import customclasses.MicrofinanceCustomers;
import customclasses.NicTextWatcher;
import customclasses.OrganizationEmployees;
import customclasses.OrganizationRegion;
import customclasses.OrganizationStaions;
import databases.DBHandler;
import secugen.FingerPrintReader;
import utilies.HexConversion;

/**
 * Created by shery on 11/16/2016.
 */

public class CustomerInfoFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private IntentFilter filter;
    HexConversion conversion;
    private JSGFPLib sgfplib;
    private boolean isDeviceRegistered = false;
    FingerPrintReader fingerPrintReader1;
    private PendingIntent mPermissionIntent;
    private byte[] mRegisterImage, mRegisterTemplate;
    private int[] mMaxTemplateSize;
    private int mImageWidth;
    private int mImageHeight;
    private int mImageDPI;

    private static final String TAG = CustomerInfoFragment.class.getSimpleName();
    private DatePickerDialog mDatePickerDialog;
    private SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private EditText customerIdEdt, customerDOBEdt, customerNicExpEdt, customerFNameEdt,
            customerSNameEdt, guardianEdt, customerFNumberEdt,
            customerNicNumberEdt, regionEdt, guardianNicEdt, tokenNumberEdt;
    private Spinner customerCNICSpin, customerTypeSpin, customerStatusSpin, stationSpin, customerGenderSpin;
    private boolean cnicType;

    private Button mButtonRegister, retrytBtn;


    private LinearLayout guardianContainer, tokenContainer;
    private RadioGroup guardianGrp;
    private TextView cincTypeTxt;
    private ImageView mImageViewFingerprint;
    private CheckBox isDecBox, isDisBox, statusBox;
    private ArrayAdapter<String> adapter1;
    private String mRegisterTemplateStr;
    private Date curDate;
    private String timeStamp;
    private CustomerFormActivity activity;
    private Toast toast;
    private DBHandler dbHandler;

    /*regex for alphabets and space only    */

    private void checkViewType() {
        Customer customer = ((CustomerFormActivity) getActivity()).getCustomer();

        if (customer != null && ((CustomerFormActivity) getActivity()).getViewType() != null) {
            Log.e(TAG, "checkViewType: called");
            customerIdEdt.setText(customer.getCustomerId() + "");
            customerNicExpEdt.setText(customer.getNICExpiryDate());
            customerFNameEdt.setText(customer.getFirstName());
            customerSNameEdt.setText(customer.getLastName());
            guardianEdt.setText(customer.getGuardian());
            customerDOBEdt.setText(customer.getDateOfBirth());
            customerFNumberEdt.setText(customer.getFamilyNumber());
            mRegisterImage = customer.getFPImage();

            if ((mRegisterImage != null && mRegisterImage.length > 0)) {
                Log.e(TAG, "checkViewType: mRegisterImage size = " + mRegisterImage.length);
                mRegisterTemplateStr = customer.getFPImageTemp();
                mImageViewFingerprint.setImageBitmap((CommonMethod.byteArrayToBitmap(mRegisterImage)));
            }
            /*mButtonRegister = */
            RadioButton radioButton = ((RadioButton) guardianGrp.getChildAt(customer.getGuardianType()));
            radioButton.setChecked(true);

            if (!radioButton.getText().toString().equalsIgnoreCase("S/O")) {
                guardianContainer.setVisibility(View.VISIBLE);
                guardianNicEdt.setText(customer.getGuardianNICNumber());
            }

            if (!customer.getNICExpiryDate().equalsIgnoreCase("") && compareExpDate(customer.getNICExpiryDate())) {
                tokenContainer.setVisibility(View.VISIBLE);
                tokenNumberEdt.setText(customer.getTokenNumber());
            }

            regionEdt.setText(customer.getRegionName());

            isDecBox.setChecked((customer.getIsDeceased() == 1));
            isDisBox.setChecked((customer.getIsDisabled() == 1));
            statusBox.setChecked((customer.getSyncStatus() == 1));

//            stationSpin.setSelection();
            CommonMethod.setSpinnerSelectedItem(stationSpin, customer.getStationName());
            customerNicNumberEdt.setText(customer.getNICNumber());
            customerStatusSpin.setSelection(customer.getStatus());


            CommonMethod.setSpinnerSelectedItem(customerGenderSpin, (customer.getGender().equalsIgnoreCase("m")) ? "male" : "female");
            Log.e(TAG, "checkViewType: view initialized ");
            Log.e(TAG, "checkViewType: " + customerFNameEdt.getText().toString());

        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_info, container, false);
        Log.e(TAG, "onCreateView: called");
        activity = (CustomerFormActivity) getActivity();
        dbHandler = new DBHandler(getContext());
        customerDOBEdt = (EditText) v.findViewById(R.id.customerDOBEdt);
        customerIdEdt = (EditText) v.findViewById(R.id.customerIdEdt);
        customerFNameEdt = (EditText) v.findViewById(R.id.customerFNameEdt);
        customerNicExpEdt = (EditText) v.findViewById(R.id.customerNicExpEdt);
        customerSNameEdt = (EditText) v.findViewById(R.id.customerSNameEdt);
        guardianEdt = (EditText) v.findViewById(R.id.guardianEdt);
        guardianNicEdt = (EditText) v.findViewById(R.id.guardianNicEdt);
        customerFNumberEdt = (EditText) v.findViewById(R.id.customerFNumberEdt);
        tokenNumberEdt = (EditText) v.findViewById(R.id.tokenNumberEdt);
        customerNicNumberEdt = (EditText) v.findViewById(R.id.customerNicNumberEdt);
        tokenContainer = (LinearLayout) v.findViewById(R.id.tokenContainer);

        stationSpin = (Spinner) v.findViewById(R.id.stationSpin);
        customerTypeSpin = (Spinner) v.findViewById(R.id.customerTypeSpin);
        guardianContainer = (LinearLayout) v.findViewById(R.id.guardianContainer);

        customerStatusSpin = (Spinner) v.findViewById(R.id.customerStatusSpin);
        customerGenderSpin = (Spinner) v.findViewById(R.id.customerGenderSpin);

        cincTypeTxt = (TextView) v.findViewById(R.id.cincTypeTxt);
        toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        customerNicNumberEdt.setSelection(customerNicNumberEdt.getText().toString().length());

        regionEdt = (EditText) v.findViewById(R.id.regionEdt);
        guardianGrp = (RadioGroup) v.findViewById(R.id.guardianGrp);
        isDecBox = (CheckBox) v.findViewById(R.id.isDecBox);
        isDisBox = (CheckBox) v.findViewById(R.id.isDisBox);
        statusBox = (CheckBox) v.findViewById(R.id.statusBox);
        customerIdEdt.setText(" (Automatically Assigned) ");
        guardianGrp.setOnCheckedChangeListener(this);

        mButtonRegister = (Button) v.findViewById(R.id.regPrintBtn);
        retrytBtn = (Button) v.findViewById(R.id.retrytBtn);

        mButtonRegister.setOnClickListener(this);
        retrytBtn.setOnClickListener(this);
        mImageViewFingerprint = (ImageView) v.findViewById(R.id.imageViewFingerprint);

        conversion = new HexConversion();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        getActivity().registerReceiver(mUsbAttachedAndDetReceiver, intentFilter);

        adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, activity.stationNameList);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stationSpin.setAdapter(adapter1);
        usbPermission();
        initiateDeviceAndView();
        checkViewType();

        customerIdEdt.setEnabled(false);


        customerDOBEdt.setOnClickListener(this);
        customerNicExpEdt.setOnClickListener(this);
        customerNicNumberEdt.setOnClickListener(this);

        guardianNicEdt.setFilters(NicTextWatcher.getFilter());
        guardianNicEdt.addTextChangedListener(NicTextWatcher.getInstance());


        cincTypeTxt.setText("NIC Number");
        customerNicNumberEdt.setFilters(NicTextWatcher.getFilter());
        customerNicNumberEdt.addTextChangedListener(NicTextWatcher.getInstance());
        tokenNumberEdt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});

        /*if (customerCNICSpin.getSelectedItemPosition() == 0) {



        } else {
            customerCNICSpin.getSelectedItemPosition();
            customerCNICSpin.setSelection(1, false);
            cincTypeTxt.setText("Token Number");
            customerNicNumberEdt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
            cnicType = false;
        }*/

        /*customerCNICSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {


                    cincTypeTxt.setText("NIC Number");
                    customerNicNumberEdt.setFilters(NicTextWatcher.getFilter());
                    customerNicNumberEdt.setText("");
                    customerNicNumberEdt.addTextChangedListener(NicTextWatcher.getInstance());
                    cnicType = true;

                } else {
                    cincTypeTxt.setText("Token Number");
                    customerNicNumberEdt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                    customerNicNumberEdt.setText("");
                    customerNicNumberEdt.removeTextChangedListener(NicTextWatcher.getInstance());
                    cnicType = false;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/


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
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (radioGroup == guardianGrp) {
            if (getCheckedRadioButtonText(radioGroup).equalsIgnoreCase("S/O") && guardianContainer.getVisibility() == View.VISIBLE) {
                guardianContainer.setVisibility(View.GONE);
                CommonMethod.setSpinnerSelectedItem(customerGenderSpin, "male");
            } else {
                if (guardianContainer.getVisibility() == View.GONE)
                    guardianContainer.setVisibility(View.VISIBLE);
                CommonMethod.setSpinnerSelectedItem(customerGenderSpin, "female");
            }
        }
    }


    @Override
    public void onClick(View view) {
        if (view == retrytBtn) {
            initiateDeviceAndView();
            if (mRegisterImage != null && mRegisterImage.length > 0)
                mImageViewFingerprint.setImageBitmap((CommonMethod.byteArrayToBitmap(mRegisterImage)));
            /*getActivity().recreate();*/
        }
        if (view == mButtonRegister) {
            if (isDeviceRegistered) {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        fingerPrintReader1.readFingerPrint();
                        mRegisterTemplate = fingerPrintReader1.getHexTemplate();

//                        mRegisterTemplateStr = conversion.getHexString(mRegisterTemplate);


                        mRegisterTemplateStr = Base64.encodeToString(mRegisterTemplate, Base64.DEFAULT);
                        Bitmap img = fingerPrintReader1.toGrayscale(fingerPrintReader1.getFPBitMap());
                        mImageViewFingerprint.setImageBitmap(img);
                        mRegisterImage = CommonMethod.bitmapToByteArray(img);

//                        mRegisterImage = CommonMethod.bitmapToByteArray(((BitmapDrawable) mImageViewFingerprint.getDrawable()).getBitmap());


//                        CommonMethod.writeToFile(CommonMethod.getEncoded64ImageStringFromBitmap(fingerPrintReader1.getFPBitMap()));
//                        Log.e(TAG, "run: " + CommonMethod.getEncoded64ImageStringFromBitmap(fingerPrintReader1.getFPBitMap()) );

                    }
                });

            }
        }
        if (view == customerDOBEdt) {
            Calendar newCalender = Calendar.getInstance();
            mDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    curDate = newDate.getTime();
                    customerDOBEdt.setText(dateFormater.format(curDate));


                    timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US).format(curDate);
                }
            }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));
            mDatePickerDialog.show();
        } else if (view == customerNicExpEdt) {
            Calendar newCalender = Calendar.getInstance();
            mDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    curDate = newDate.getTime();
                    customerNicExpEdt.setText(dateFormater.format(curDate));

                    if (compareExpDate(dateFormater.format(curDate))) {
                        tokenContainer.setVisibility(View.VISIBLE);
                    } else {
                        tokenContainer.setVisibility(View.GONE);
                    }
                    timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US).format(curDate);

                }
            }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));
            mDatePickerDialog.show();
        } else if (view == customerNicNumberEdt) {

            customerNicNumberEdt.setSelection(customerNicNumberEdt.getText().length());
        }
    }

    private boolean validateUi() {
        Log.e(TAG, "validateUi: " + customerFNameEdt.getText().toString());
        if (CommonMethod.checkEmpty(customerFNameEdt)
                || CommonMethod.checkEmpty(customerSNameEdt)
                || CommonMethod.checkEmpty(guardianEdt)
                || CommonMethod.checkEmpty(customerDOBEdt)
                || CommonMethod.checkEmpty(customerNicNumberEdt)
                || CommonMethod.checkEmpty(customerNicExpEdt)
                || CommonMethod.checkEmpty(regionEdt)

                ) {
            toast.setText("Please fill all required fields");
            toast.show();
            return false;

        } else if (mRegisterImage == null || mRegisterImage.length == 0) {
            toast.setText("Finger Print Not Captured!");
            toast.show();
            return false;

        } else if (guardianContainer.getVisibility() == View.VISIBLE && CommonMethod.checkEmpty(guardianNicEdt) && !guardianNicEdt.getText().toString().matches("\\d{5}-\\d{7}-\\d")) {
            toast.setText("Invalid Guardian CNIC");
            toast.show();
            return false;
        } else if (!customerNicNumberEdt.getText().toString().matches("\\d{5}-\\d{7}-\\d")) {
            toast.setText("Invalid CNIC");
            toast.show();
            return false;
        } else if (!customerFNameEdt.getText().toString().matches("^[a-zA-Z ]*$")) {
            toast.setText("First Name Not Valid ");
            toast.show();
            return false;

        } else if (tokenContainer.getVisibility() == View.VISIBLE && CommonMethod.checkEmpty(tokenNumberEdt)) {
            toast.setText("Invalid Token Number ");
            toast.show();
            return false;

        } else if (!customerSNameEdt.getText().toString().matches("^[a-zA-Z ]*$")) {

            toast.setText("Surname Not Valid ");
            toast.show();
            return false;

        }/* else if (regionEdt.getText().toString().matches("^[a-zA-Z ]*$")) {
            toast.setText("Region Not Valid ");
            toast.show();
        }*/

        return true;
    }

    public boolean compareExpDate(String date) {
        try {
            String curDate = dateFormater.format(Calendar.getInstance().getTime());
            long curDateInMilis = dateFormater.parse(curDate).getTime();
            long expDateInMilis = dateFormater.parse(date).getTime();
            if (curDateInMilis > expDateInMilis) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkDate(ArrayList<String> dates) {
        String curDate = dateFormater.format(Calendar.getInstance().getTime());
        if (dates.size() == 0) {
            return true;
        }
        try {
            long recentDate = 0;
            for (String date : dates) {
                long d = dateFormater.parse(date).getTime();
                if (recentDate < d)
                    recentDate = d;
            }

            long diff = dateFormater.parse(curDate).getTime() - recentDate;

            long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            if (days > 90) {
                return true;
            }

            Toast.makeText(getContext(), "Can't Add Customer Now,Try again after " + (90 - days) + " days", Toast.LENGTH_SHORT).show();
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
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

    public boolean addCustomerInfoDataInBundle(Bundle customerData) {


        if (validateUi() && checkDate(dbHandler.getCustomerAddedDateList(customerNicNumberEdt.getText().toString()))) {
            int rbid = guardianGrp.getCheckedRadioButtonId();
            View rb = guardianGrp.findViewById(rbid);
            int idx = guardianGrp.indexOfChild(rb);

            customerData.putString(OrganizationEmployees.KEY_FIRSTNAME, customerFNameEdt.getText().toString());
            customerData.putString(OrganizationEmployees.KEY_LASTNAME, customerSNameEdt.getText().toString());
            customerData.putString(MicrofinanceCustomers.KEY_GUARDIAN, guardianEdt.getText().toString());
            customerData.putChar(MicrofinanceCustomers.KEY_GENDER, ((customerGenderSpin.getSelectedItemPosition() == 0) ? 'M' : 'F'));
            customerData.putString(MicrofinanceCustomers.KEY_FAMILY_NUMBER, customerFNumberEdt.getText().toString());

            customerData.putString(OrganizationEmployees.KEY_NICNUMBER, customerNicNumberEdt.getText().toString());
            if (tokenContainer.getVisibility() == View.VISIBLE) {
                customerData.putString(MicrofinanceCustomers.KEY_TOKEN_NUMBER, tokenNumberEdt.getText().toString());
            } else {
                customerData.putString(MicrofinanceCustomers.KEY_TOKEN_NUMBER, "");
            }
            customerData.putByteArray(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG, mRegisterImage);
            customerData.putString(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG_TEMP, mRegisterTemplateStr);

            if (guardianContainer.getVisibility() == View.VISIBLE) {
                customerData.putString(MicrofinanceCustomers.KEY_GUARDIAN_NIC_NUMBER, guardianNicEdt.getText().toString());
            } else {
                customerData.putString(MicrofinanceCustomers.KEY_GUARDIAN_NIC_NUMBER, "");
            }

            customerData.putString(MicrofinanceCustomers.KEY_GUARDIAN_NIC_NUMBER, guardianNicEdt.getText().toString());

            customerData.putString(MicrofinanceCustomers.KEY_DOB, customerDOBEdt.getText().toString());
            customerData.putString(MicrofinanceCustomers.KEY_NIC_EXPIRY_DATE, customerNicExpEdt.getText().toString());

            customerData.putString(OrganizationRegion.KEY_REGION_NAME, regionEdt.getText().toString());
            customerData.putString(MicrofinanceCustomers.KEY_EMPLOYEE_STATION_NAME, stationSpin.getSelectedItem() + "");
            customerData.putInt(OrganizationStaions.KEY_STATION_ID, activity.stationList.get(stationSpin.getSelectedItemPosition()).getStationId());

            customerData.putInt(MicrofinanceCustomers.KEY_CUSTOMER_TYPE, customerTypeSpin.getSelectedItemPosition());
            customerData.putInt(OrganizationEmployees.KEY_STATUS, customerStatusSpin.getSelectedItemPosition());

            customerData.putInt(MicrofinanceCustomers.KEY_IS_DECEASED, (isDecBox.isChecked() ? 1 : 0));
            customerData.putInt(MicrofinanceCustomers.KEY_IS_DISABLED, (isDisBox.isChecked() ? 1 : 0));
            customerData.putInt(MicrofinanceCustomers.KEY_DATA_SYNCUP, (statusBox.isChecked() ? 1 : 0));

            customerData.putInt(MicrofinanceCustomers.KEY_GUARDIAN_TYPE, idx);

            return true;
        }
        return false;
    }

    public boolean addCustomerInfoDataInBundleWithoutCheck(Bundle customerData) {


        if (validateUi()) {
            int rbid = guardianGrp.getCheckedRadioButtonId();
            View rb = guardianGrp.findViewById(rbid);
            int idx = guardianGrp.indexOfChild(rb);

            if (guardianContainer.getVisibility() == View.VISIBLE) {
                customerData.putString(MicrofinanceCustomers.KEY_GUARDIAN_NIC_NUMBER, guardianNicEdt.getText().toString());
            } else {
                customerData.putString(MicrofinanceCustomers.KEY_GUARDIAN_NIC_NUMBER, "");
            }

            customerData.putString(OrganizationEmployees.KEY_FIRSTNAME, customerFNameEdt.getText().toString());
            customerData.putString(OrganizationEmployees.KEY_LASTNAME, customerSNameEdt.getText().toString());
            customerData.putString(MicrofinanceCustomers.KEY_GUARDIAN, guardianEdt.getText().toString());
            customerData.putChar(MicrofinanceCustomers.KEY_GENDER, ((customerGenderSpin.getSelectedItemPosition() == 0) ? 'M' : 'F'));
            customerData.putString(MicrofinanceCustomers.KEY_FAMILY_NUMBER, customerFNumberEdt.getText().toString());

            customerData.putByteArray(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG, mRegisterImage);
            customerData.putString(MicrofinanceCustomers.KEY_CUSTOMER_FP_IMG_TEMP, mRegisterTemplateStr);


            customerData.putString(OrganizationEmployees.KEY_NICNUMBER, customerNicNumberEdt.getText().toString());
            if (tokenContainer.getVisibility() == View.VISIBLE) {
                customerData.putString(MicrofinanceCustomers.KEY_TOKEN_NUMBER, tokenNumberEdt.getText().toString());
            } else {
                customerData.putString(MicrofinanceCustomers.KEY_TOKEN_NUMBER, "");
            }

            customerData.putString(MicrofinanceCustomers.KEY_DOB, customerDOBEdt.getText().toString());
            customerData.putString(MicrofinanceCustomers.KEY_NIC_EXPIRY_DATE, customerNicExpEdt.getText().toString());

            customerData.putString(OrganizationRegion.KEY_REGION_NAME, regionEdt.getText().toString());
            customerData.putString(MicrofinanceCustomers.KEY_EMPLOYEE_STATION_NAME, stationSpin.getSelectedItem() + "");
            customerData.putInt(OrganizationStaions.KEY_STATION_ID, activity.stationList.get(stationSpin.getSelectedItemPosition()).getStationId());

            customerData.putInt(OrganizationEmployees.KEY_STATUS, customerStatusSpin.getSelectedItemPosition());

            customerData.putInt(MicrofinanceCustomers.KEY_IS_DECEASED, (isDecBox.isChecked() ? 1 : 0));
            customerData.putInt(MicrofinanceCustomers.KEY_IS_DISABLED, (isDisBox.isChecked() ? 1 : 0));
            customerData.putInt(MicrofinanceCustomers.KEY_DATA_SYNCUP, (statusBox.isChecked() ? 1 : 0));

            customerData.putInt(MicrofinanceCustomers.KEY_GUARDIAN_TYPE, idx);

            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");


    }

    public String getCheckedRadioButtonText(RadioGroup radioButtonGroup) {
        int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
        Log.e(TAG, "getCheckedRadioButton: " + radioButtonID);
        View radioButton = radioButtonGroup.findViewById(radioButtonID);
        int radioId = radioButtonGroup.indexOfChild(radioButton);

        try {
            return ((RadioButton) radioButtonGroup.getChildAt(radioId)).getText().toString();

        } catch (NullPointerException e) {
            return "";
        }

    }

}
