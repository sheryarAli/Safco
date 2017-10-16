package fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shery.safco.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import activities.FullScreenImageActivity;
import activities.LoanActivity;
import adapters.GenericAdapter;
import customclasses.CommonConst;
import customclasses.CommonMethod;
import customclasses.Document;
import customclasses.Loan;
import databases.DBHandler;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

/**
 * Created by shery on 12/3/2016.
 */

public class CustomerLoanFormFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = CustomerLoanFormFragment.class.getSimpleName();
    private EditText loanTypeEdt, reqLoanEdt, appLoanEdt,
            loanTermEdt, amountReqBuisEdt, persCapBuisEdt,
            ExpMoInBuisEdt, bussAddEdt, monIncEdt, monExpenseEdt,
            monHIncomeEdt, monHExpenseEdt, savingEdt, hSavingEdt,
            exBusinessEdt, expBusinessEdt, addOnEdt, notesEdt;
    private Spinner loanStatusSpin;
    private Button saveBtn, pickPhotoBtn;
    private Toast toast;
    private DBHandler dbHandler;
    private int customerId;
    private String nicNumber;
    private Loan loan;
    private RecyclerView imageRecView;
    private GenericAdapter<Bitmap> adapter;
    private ArrayList<String> selectPhotosPath, documentNameList;
    private ArrayList<Bitmap> selectPhotosBitmap;
    private ArrayList<Integer> documentIdList, documentDeleteIdList;
    private ProgressDialog progressDialog;
    private String[] loanStatus;
    private CharSequence menu[] = new CharSequence[]{"View", "Delete"};
    private DatePickerDialog mDatePickerDialog;
    private SimpleDateFormat dateFormater;
    private Date curDate;

    private void initView(View view) {
        loanStatus = getResources().getStringArray(R.array.group_status);
        pickPhotoBtn = (Button) view.findViewById(R.id.pickPhotoBtn);
        loanTypeEdt = (EditText) view.findViewById(R.id.loanTypeEdt);
        reqLoanEdt = (EditText) view.findViewById(R.id.reqLoanEdt);
        appLoanEdt = (EditText) view.findViewById(R.id.appLoanEdt);
        loanTermEdt = (EditText) view.findViewById(R.id.loanTermEdt);
        amountReqBuisEdt = (EditText) view.findViewById(R.id.amountReqBuisEdt);
        persCapBuisEdt = (EditText) view.findViewById(R.id.persCapBuisEdt);
        ExpMoInBuisEdt = (EditText) view.findViewById(R.id.ExpMoInBuisEdt);
        bussAddEdt = (EditText) view.findViewById(R.id.bussAddEdt);
        monIncEdt = (EditText) view.findViewById(R.id.monIncEdt);
        monExpenseEdt = (EditText) view.findViewById(R.id.monExpenseEdt);
        monHIncomeEdt = (EditText) view.findViewById(R.id.monHIncomeEdt);
        monHExpenseEdt = (EditText) view.findViewById(R.id.monHExpenseEdt);
        savingEdt = (EditText) view.findViewById(R.id.savingEdt);
        hSavingEdt = (EditText) view.findViewById(R.id.hSavingEdt);
        exBusinessEdt = (EditText) view.findViewById(R.id.exBusinessEdt);
        expBusinessEdt = (EditText) view.findViewById(R.id.expBusinessEdt);
        addOnEdt = (EditText) view.findViewById(R.id.addOnEdt);
        notesEdt = (EditText) view.findViewById(R.id.notesEdt);
        loanStatusSpin = (Spinner) view.findViewById(R.id.loanStatusSpin);
        saveBtn = (Button) view.findViewById(R.id.saveBtn);
        imageRecView = (RecyclerView) view.findViewById(R.id.imageRecView);
        addOnEdt.setOnClickListener(this);

    }

    private void checkViewType() {
        Bundle args = getArguments();
        loan = (Loan) args.getSerializable("Loan");
        if (loan != null) {
            loanTypeEdt.setText(loan.getLoanTypeId());

            reqLoanEdt.setText(loan.getRequestedLoanAmount());

            appLoanEdt.setText(loan.getApprovedLoanAmount());

            loanTermEdt.setText(loan.getLoanTerm());

            amountReqBuisEdt.setText(loan.getAmountRequiredForBusiness());

            persCapBuisEdt.setText(loan.getPersonalCapitalInBusiness());

            ExpMoInBuisEdt.setText(loan.getExpectedMonthlyIncomeFromBusiness());

            bussAddEdt.setText(loan.getBusinessAddress());

            monIncEdt.setText(loan.getMonthlyIncome());

            monExpenseEdt.setText(loan.getMonthlyExpense());

            monHIncomeEdt.setText(loan.getHousehold_MonthlyIncome());

            monHExpenseEdt.setText(loan.getHousehold_MonthlyExpense());

            savingEdt.setText(loan.getSavings());

            hSavingEdt.setText(loan.getHousehold_Savings());

            exBusinessEdt.setText(loan.getExistingBusiness());

            expBusinessEdt.setText(loan.getExperienceInBusiness());

            addOnEdt.setText(loan.getAddedDateTime());

            notesEdt.setText(loan.getNotes());

            CommonMethod.setSpinnerSelectedItem(loanStatusSpin, loan.getLoanStatus());

            saveBtn.setText("update");
            new LoadImageAsync().execute(loan.getLoanId());
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_loan_form, container, false);
        dbHandler = new DBHandler(getContext());
        initView(view);
        customerId = ((LoanActivity) getActivity()).getCustomerId();
        nicNumber = ((LoanActivity) getActivity()).getNicNumber();
        progressDialog = new ProgressDialog(getContext());
        selectPhotosPath = new ArrayList<String>();
        documentNameList = new ArrayList<String>();

        selectPhotosBitmap = new ArrayList<Bitmap>();
        documentIdList = new ArrayList<Integer>();
        documentDeleteIdList = new ArrayList<Integer>();
        dateFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        addOnEdt.setText(dateFormater.format(Calendar.getInstance().getTime()));
        checkViewType();
        imageRecView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        imageRecView.addItemDecoration(new SpacesItemDecoration(4));

        toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        saveBtn.setOnClickListener(this);
        pickPhotoBtn.setOnClickListener(this);

        return view;
    }

    private boolean validateUi() {
        if (loanTypeEdt.getText().toString().equalsIgnoreCase("")
                || reqLoanEdt.getText().toString().equalsIgnoreCase("")
                || loanTermEdt.getText().toString().equalsIgnoreCase("")
                || amountReqBuisEdt.getText().toString().equalsIgnoreCase("")
                || persCapBuisEdt.getText().toString().equalsIgnoreCase("")
                || ExpMoInBuisEdt.getText().toString().equalsIgnoreCase("")
                || monIncEdt.getText().toString().equalsIgnoreCase("")
                || monExpenseEdt.getText().toString().equalsIgnoreCase("")
                || monHIncomeEdt.getText().toString().equalsIgnoreCase("")
                || monHExpenseEdt.getText().toString().equalsIgnoreCase("")
                || savingEdt.getText().toString().equalsIgnoreCase("")
                || hSavingEdt.getText().toString().equalsIgnoreCase("")
                || exBusinessEdt.getText().toString().equalsIgnoreCase("")
                || expBusinessEdt.getText().toString().equalsIgnoreCase("")
                ) {

            toast.setText("Please fill all required fields");
            toast.show();
            return false;
        }/* else if (Integer.parseInt(reqLoanEdt.getText().toString()) == 0) {
            Toast.makeText(getContext(), "Invalid Value", Toast.LENGTH_LONG).show();
            reqLoanEdt.setError("Value must be > 0");
            return false;
        } */else if (Integer.parseInt(appLoanEdt.getText().toString()) == 0) {
            Toast.makeText(getContext(), "Invalid Value", Toast.LENGTH_LONG).show();
            appLoanEdt.setError("Value must be > 0");
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter = new GenericAdapter<Bitmap>(getContext(), selectPhotosBitmap) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.image_layout, parent, false);
                view.setOnClickListener(this);
                ItemViewHolder viewHolder = new ItemViewHolder(view);
                return viewHolder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, Bitmap val, final int position) {


                final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                Log.e(TAG, "onBindData " + itemViewHolder.getAdapterPosition());
                itemViewHolder.imageView.getRootView().setTag(itemViewHolder.getAdapterPosition());

                itemViewHolder.lblBtn.setText(documentNameList.get(itemViewHolder.getAdapterPosition()));

                itemViewHolder.lblBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.dialog_img_layout);
                        dialog.setTitle("Select Title");
                        Button addBtn = (Button) dialog.findViewById(R.id.addBtn);
                        Button cancelBtn = (Button) dialog.findViewById(R.id.cancelBtn);

                        if (itemViewHolder.lblBtn.getText().toString().contains("Person Photo".toLowerCase()))
                            ((CheckBox) dialog.findViewById(R.id.perPhotoChk)).setChecked(true);

                        if (itemViewHolder.lblBtn.getText().toString().contains("Nic Front/Back".toLowerCase()))
                            ((CheckBox) dialog.findViewById(R.id.nicFBChk)).setChecked(true);

                        if (itemViewHolder.lblBtn.getText().toString().contains("CIB Report".toLowerCase()))
                            ((CheckBox) dialog.findViewById(R.id.cibChk)).setChecked(true);

                        if (itemViewHolder.lblBtn.getText().toString().contains("Loan Application".toLowerCase()))
                            ((CheckBox) dialog.findViewById(R.id.loanAppChk)).setChecked(true);

                        if (itemViewHolder.lblBtn.getText().toString().contains("Supporting CNIC".toLowerCase()))
                            ((CheckBox) dialog.findViewById(R.id.supNicChk)).setChecked(true);

                        if (itemViewHolder.lblBtn.getText().toString().contains("Other".toLowerCase()))
                            ((CheckBox) dialog.findViewById(R.id.otherChk)).setChecked(true);

                        addBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                StringBuilder sb = new StringBuilder();
                                if (((CheckBox) dialog.findViewById(R.id.perPhotoChk)).isChecked()) {
                                    sb.append(", Person Photo");
                                }
                                if (((CheckBox) dialog.findViewById(R.id.nicFBChk)).isChecked()) {
                                    sb.append(", Nic Front/Back");
                                }
                                if (((CheckBox) dialog.findViewById(R.id.cibChk)).isChecked()) {
                                    sb.append(", CIB Report");
                                }
                                if (((CheckBox) dialog.findViewById(R.id.loanAppChk)).isChecked()) {
                                    sb.append(", Loan Application");
                                }
                                if (((CheckBox) dialog.findViewById(R.id.supNicChk)).isChecked()) {
                                    sb.append(", Supporting CNIC");
                                }
                                if (((CheckBox) dialog.findViewById(R.id.otherChk)).isChecked()) {
                                    sb.append(", Other");
                                }
                                String str;
                                if (sb.toString().equalsIgnoreCase("")) {
                                    str = "Add Title";
                                } else {
                                    str = sb.toString().substring(1).trim();
                                }

                                dialog.dismiss();
                                itemViewHolder.lblBtn.setText(str.toLowerCase());
                                /*String str2 = nicNumber + "_" + str;*/
                                /*Toast.makeText(context, str2.toLowerCase() + "", Toast.LENGTH_SHORT).show();*/

                                documentNameList.set(itemViewHolder.getAdapterPosition(), str.toLowerCase());

//                                documentNameList.get(position)

                            }
                        });
                        cancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });

                try {

                    itemViewHolder.imageView.setImageBitmap(val);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onItemClick(View view) {
                int pos = Integer.valueOf(view.getTag() + "");
                showMenu(getItem(pos), pos);

            }
        };

        imageRecView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view == saveBtn) {

            if (validateUi()) {

                if (saveBtn.getText().toString().equalsIgnoreCase("save")) {


                    Loan loan = new Loan();
                    loan.setLoanTypeId(loanTypeEdt.getText().toString());
                    loan.setCustomerId(customerId);
                    loan.setNICNumber(nicNumber);
                    loan.setLoanStatus(loanStatusSpin.getSelectedItem() + "");
                    try {
                        if (Integer.parseInt(reqLoanEdt.getText().toString()) == 0) {
                            Toast.makeText(getContext(), "Requested Loan Can't be 0", Toast.LENGTH_LONG).show();
                            return;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    loan.setRequestedLoanAmount(reqLoanEdt.getText().toString());

                    loan.setApprovedLoanAmount(appLoanEdt.getText().toString());
                    loan.setLoanTerm(loanTermEdt.getText().toString());
                    loan.setAmountRequiredForBusiness(amountReqBuisEdt.getText().toString());
                    loan.setPersonalCapitalInBusiness(persCapBuisEdt.getText().toString());
                    loan.setExpectedMonthlyIncomeFromBusiness(ExpMoInBuisEdt.getText().toString());
                    loan.setBusinessAddress(bussAddEdt.getText().toString());
                    loan.setMonthlyIncome(monIncEdt.getText().toString());
                    loan.setMonthlyExpense(monExpenseEdt.getText().toString());
                    loan.setHousehold_MonthlyIncome(monHIncomeEdt.getText().toString());
                    loan.setHousehold_MonthlyExpense(monHExpenseEdt.getText().toString());
                    loan.setSavings(savingEdt.getText().toString());
                    loan.setHousehold_Savings(hSavingEdt.getText().toString());
                    loan.setExistingBusiness(exBusinessEdt.getText().toString());
                    loan.setExperienceInBusiness(expBusinessEdt.getText().toString());
                    loan.setAddedDateTime(addOnEdt.getText().toString());
                    loan.setNotes(notesEdt.getText().toString());

                    long res = dbHandler.insertLoan(loan);
                    if (res != -1) {
                        Log.e(TAG, "Loan Id: " + res);
                        new AsyncSave("Inserting Loan Data", "Loan Successfully Added!").execute(res + "");

                    }

                } else if (saveBtn.getText().toString().equalsIgnoreCase("update")) {

                    loan.setLoanTypeId(loanTypeEdt.getText().toString());
                    loan.setCustomerId(customerId);
                    loan.setLoanStatus(loanStatusSpin.getSelectedItem() + "");
                    loan.setRequestedLoanAmount(reqLoanEdt.getText().toString());

                    loan.setApprovedLoanAmount(appLoanEdt.getText().toString());
                    loan.setLoanTerm(loanTermEdt.getText().toString());
                    loan.setAmountRequiredForBusiness(amountReqBuisEdt.getText().toString());
                    loan.setPersonalCapitalInBusiness(persCapBuisEdt.getText().toString());
                    loan.setExpectedMonthlyIncomeFromBusiness(ExpMoInBuisEdt.getText().toString());
                    loan.setBusinessAddress(bussAddEdt.getText().toString());
                    loan.setMonthlyIncome(monIncEdt.getText().toString());
                    loan.setMonthlyExpense(monExpenseEdt.getText().toString());
                    loan.setHousehold_MonthlyIncome(monHIncomeEdt.getText().toString());
                    loan.setHousehold_MonthlyExpense(monHExpenseEdt.getText().toString());
                    loan.setSavings(savingEdt.getText().toString());
                    loan.setHousehold_Savings(hSavingEdt.getText().toString());
                    loan.setExistingBusiness(exBusinessEdt.getText().toString());
                    loan.setExperienceInBusiness(expBusinessEdt.getText().toString());
                    loan.setAddedDateTime(addOnEdt.getText().toString());
                    loan.setNotes(notesEdt.getText().toString());

                    int res = dbHandler.updateLoanData(loan);
                    if (res != 0) {
                        dbHandler.deleteDocument(documentDeleteIdList);
                        new AsyncSave("Updating Loan Data", "Loan Successfully Updated!").execute(loan.getLoanId() + "");

                    }
                }

            }
        } else if (view == pickPhotoBtn) {
            FilePickerBuilder.getInstance().setMaxCount(10)
                    .setActivityTheme(R.style.AppTheme3)
                    .pickPhoto(getActivity());

        } else if (view == addOnEdt) {
            Calendar newCalender = Calendar.getInstance();
            mDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    curDate = newDate.getTime();
                    addOnEdt.setText(dateFormater.format(curDate));
                }
            }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));
            mDatePickerDialog.show();
        }


    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        Button lblBtn;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgView);
            lblBtn = (Button) itemView.findViewById(R.id.lblBtn);
        }
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case FilePickerConst.REQUEST_CODE_PHOTO:

                if (resultCode == Activity.RESULT_OK && data != null) {
                    Log.e(TAG, "onActivityResult: ");
                    for (String path : data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_PHOTOS)) {
                        String fileName = CommonMethod.getFileNameFromPath(path);

                        if (!documentNameList.contains(fileName) && !selectPhotosPath.contains(path) && selectPhotosPath.size() < 10) {
                            selectPhotosPath.add(path);
                            File file = new File(path);
                            Log.e(TAG, "Image Name: " + fileName);
                            documentNameList.add(fileName);
                            documentIdList.add(0);
                            selectPhotosBitmap.add(CommonMethod.compressImage(file, getContext()));
                        }
                    }

                    adapter.addItems(selectPhotosBitmap);
                    adapter.notifyDataSetChanged();
                    Log.e(TAG, "Number Of Files: " + selectPhotosPath.size());
                }

        }
    }

    private class AsyncSave extends AsyncTask<String, Void, Void> {
        private String buttonStr;
        private String progressStr, onCompStr;

        public AsyncSave(String progressStr, String onCompStr) {
            this.progressStr = progressStr;
            this.onCompStr = onCompStr;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            buttonStr = saveBtn.getText().toString();
            progressDialog.setMessage(progressStr);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.e(TAG, "Total Size: " + documentNameList.size());
            for (int i = 0; i < documentNameList.size(); i++) {
                Document document = new Document();
                document.setDocumentId(documentIdList.get(i));
                document.setImageName(documentNameList.get(i));
                document.setImage(CommonMethod.getEncoded64ImageStringFromBitmap(selectPhotosBitmap.get(i)));
                document.setLoanId(Integer.valueOf(params[0]));
                document.setCustomerId(customerId);

                dbHandler.insertDocumentData(getContext(), document);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            toast.setText(onCompStr);
            toast.show();

            getActivity().onBackPressed();
        }
    }

    private class LoadImageAsync extends AsyncTask<Integer, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading Loan Data");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            ArrayList<Document> docList = dbHandler.getDocumentData(getContext() ,integers[0]);
            Log.e(TAG, "doInBackground: " + docList.size());
            for (Document document : docList) {
                selectPhotosBitmap.add(CommonMethod.getBitmap(document.getImage()));
                documentIdList.add(document.getDocumentId());
                documentNameList.add((document.getImageName()));

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
            progressDialog.dismiss();
        }
    }

    public void showMenu(final Bitmap item, final int position) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Action");
        builder.setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                Log.e(TAG, "menu: " + which);
                switch (which) {
                    case 0:
                        intent = new Intent(getContext(), FullScreenImageActivity.class);
                        intent.putExtra(CommonConst.KEY_IMAGE, CommonMethod.bitmapToByteArray(item));
                        startActivity(intent);
                        break;

                    case 1:
                        documentNameList.remove(position);
                        documentDeleteIdList.add(documentIdList.get(position));
                        adapter.getItemList().remove(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position, adapter.getItemList().size());
                        break;


                }

                Log.e(TAG, "option: " + which);

            }
        });
        builder.show();
    }

}
