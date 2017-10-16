package fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shery.safco.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import adapters.GenericAdapter;
import customclasses.CommonMethod;
import customclasses.NicTextWatcher;
import customclasses.Repayment;
import customclasses.RepaymentLog;
import databases.DbHandler2;

/**
 * Created by shery on 8/26/2017.
 */

public class RepaymentFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "RepaymentFragment";
    private GenericAdapter<Repayment> recAdapter;
    private ArrayList<Repayment> repayments;
    private RecyclerView recView;
    private RadioGroup rgSearchType;
    private EditText searchValueEdt;
    private TextView notAvailableTxt;
    private Button searchBtn, searchByFPBtn, submitBtn;
    private LinearLayoutManager linearLayoutManager;
    private DbHandler2 dbHandler;
    private ArrayList<ItemViewHolder> holderList;
    private Toast toast;
    private int loanId;
    private FragmentManager mFragmentManager;

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repayment, container, false);
        recView = (RecyclerView) view.findViewById(R.id.recView);
        rgSearchType = (RadioGroup) view.findViewById(R.id.rgSearchType);
        searchValueEdt = (EditText) view.findViewById(R.id.searchValueEdt);
        notAvailableTxt = (TextView) view.findViewById(R.id.notAvailableTxt);
        searchBtn = (Button) view.findViewById(R.id.searchBtn);
        searchByFPBtn = (Button) view.findViewById(R.id.searchByFPBtn);
        submitBtn = (Button) view.findViewById(R.id.submitBtn);
        notAvailableTxt.setVisibility(View.INVISIBLE);
        mFragmentManager = getChildFragmentManager();
        toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        holderList = new ArrayList<>();
        rgSearchType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                searchValueEdt.setText("");
                switch (CommonMethod.getCheckedChildIndex(radioGroup)) {
                    case 0:
                        searchValueEdt.setHint("Loan Id here...");
                        searchValueEdt.setInputType(InputType.TYPE_CLASS_NUMBER);
                        searchValueEdt.setFilters(new InputFilter[]{});
                        searchValueEdt.removeTextChangedListener(NicTextWatcher.getInstance());
                        break;

                    case 1:
                        searchValueEdt.setHint("NIC here...");
                        searchValueEdt.setInputType(InputType.TYPE_CLASS_TEXT);
                        searchValueEdt.setSelection(searchValueEdt.getText().toString().length());
                        searchValueEdt.setFilters(NicTextWatcher.getFilter());
                        searchValueEdt.addTextChangedListener(NicTextWatcher.getInstance());
                        break;
                }

            }
        });
        searchBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        searchByFPBtn.setOnClickListener(this);
        dbHandler = new DbHandler2(getContext());
        linearLayoutManager = new LinearLayoutManager(getContext());
        recView.setLayoutManager(linearLayoutManager);
        repayments = new ArrayList<>();
        recAdapter = new GenericAdapter<Repayment>(getContext(), repayments) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                Log.d(TAG, "setViewHolder: ");
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.row_item_repayment, parent, false);
                view.setOnClickListener(this);
                ItemViewHolder viewHolder = new ItemViewHolder(view);
                holderList.add(viewHolder);
                return viewHolder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, Repayment val, int position) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                double totalLoanAmount = Double.parseDouble(val.getTotalLoanAmount());
                double totalPaid = Double.parseDouble(val.getTotalpaid());
                double totalRepaymentPaid = dbHandler.getTotalRepaymentPaid(val.getLoanId());
                double remainingBalance = totalLoanAmount - (totalPaid + totalRepaymentPaid);
                Log.e(TAG, "onBindData: loanId: " + val.getLoanId() + " remainingBalance: " + remainingBalance + " totalRepaymentPaid: " + totalRepaymentPaid+" totalLoanAmount: "+ totalLoanAmount);
                double dueInstallment = Double.parseDouble(val.getCurrentRepaymentAmount());
                if (dueInstallment > remainingBalance)
                    dueInstallment = remainingBalance;
                itemViewHolder.custNameTxt.setText(val.getCustomerName());
                itemViewHolder.loanIdTxt.setText(val.getLoanId() + "");
                itemViewHolder.nicTxt.setText(val.getNICNumber());
                if (!val.getLastRepaymentDateTime().isEmpty()) {
                    itemViewHolder.lastRepDateTxt.setText(val.getLastRepaymentDateTime().substring(0, val.getLastRepaymentDateTime().indexOf(" ")));
                } else {
                    itemViewHolder.lastRepDateTxt.setText("");
                }
                itemViewHolder.remainingBalTxt.setText(remainingBalance + "");
                itemViewHolder.dueInstallmentTxt.setText(dueInstallment + "");


            }

            @Override
            public void onItemClick(View view) {

            }
        };

        recView.setAdapter(recAdapter);

        return view;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView custNameTxt, loanIdTxt, nicTxt, dueInstallmentTxt, remainingBalTxt, lastRepDateTxt;
        EditText repAmountEdt, penaltyEdt, processingFeesEdt;

        public ItemViewHolder(View itemView) {
            super(itemView);
            custNameTxt = (TextView) itemView.findViewById(R.id.custNameTxt);
            loanIdTxt = (TextView) itemView.findViewById(R.id.loanIdTxt);
            nicTxt = (TextView) itemView.findViewById(R.id.nicTxt);
            dueInstallmentTxt = (TextView) itemView.findViewById(R.id.dueInstallmentTxt);
            remainingBalTxt = (TextView) itemView.findViewById(R.id.remainingBalTxt);
            lastRepDateTxt = (TextView) itemView.findViewById(R.id.lastRepDateTxt);
            repAmountEdt = (EditText) itemView.findViewById(R.id.repAmountEdt);
            penaltyEdt = (EditText) itemView.findViewById(R.id.penaltyEdt);
            processingFeesEdt = (EditText) itemView.findViewById(R.id.processingFeesEdt);
        }
    }

    private boolean validatUI() {
        int listSize = holderList.size();
        int numOfEmptyMandFields = 1;
        for (ItemViewHolder holder : holderList) {
            double remainingBalance = Double.parseDouble(holder.remainingBalTxt.getText().toString());
            if (CommonMethod.checkEmpty(holder.repAmountEdt)) {

                if (numOfEmptyMandFields == listSize) {
//                    holder.repAmountEdt.setError("Empty Field");
                    toast.setText("Please fill atleast one mandatory field");
                    toast.show();
                    return false;
                }

                numOfEmptyMandFields++;

            } else if (Double.parseDouble(holder.repAmountEdt.getText().toString()) > remainingBalance) {
                holder.repAmountEdt.setError("Repayment Amount can't be > Remaining Balance");
                toast.setText("Invalid Value");
                toast.show();
                return false;
            }
        }
        return true;
    }

    private boolean checkSearchValueEdt() {
        if (CommonMethod.checkEmpty(searchValueEdt)) {
            searchValueEdt.setError("Empty Field");
            toast.setText("Empty Search Field");
            toast.show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == searchByFPBtn) {
            showRepaymentFPDialog();
            /*toast.setText("Unavailable Now!");
            toast.show();*/
        }
        if (view == searchBtn) {
            int checkedIndex = CommonMethod.getCheckedChildIndex(rgSearchType);
            switch (checkedIndex) {
                case -1:
                    searchValueEdt.setText("");
                    toast.setText("PLease select any search type");
                    toast.show();
                    break;

                case 0:
                    if (checkSearchValueEdt()) {
                        loadMore(0);
                    }
                    break;
                case 1:
                    if (checkSearchValueEdt()) {
                        loadMore(1);
                    }

                    break;
            }
        }
        if (view == submitBtn) {
//dateTime Format yyyy-MM-dd HH:mm:ss

            if (validatUI()) {
                Log.d(TAG, "onClick: data submitted");
                String dateTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Calendar.getInstance().getTime());
                Log.e(TAG, "onClick: holderList.size(): " + holderList.size());
                for (int i = 0; i < holderList.size(); i++) {

                    repayments.get(i).setRepaymentAmount(holderList.get(i).repAmountEdt.getText().toString());
                    repayments.get(i).setPenalty(holderList.get(i).penaltyEdt.getText().toString());
                    repayments.get(i).setProccessingFees(holderList.get(i).processingFeesEdt.getText().toString());
                    repayments.get(i).setRepaymentDateTime(dateTimeStr);

                    /*holderList.get(i).repAmountEdt.setText("");
                    holderList.get(i).penaltyEdt.setText("");
                    holderList.get(i).processingFeesEdt.setText("");*/
                    /*Log.d(TAG, "onClick: repayment.LoanId: " + repayments.get(i).getLoanId()
                            +" viewHolder.LoanId: "+ holderList.get(i).loanIdTxt.getText().toString());*/
                }
                new AsyncSubmit().execute();
            }

        }
    }

    public void showRepaymentFPDialog() {
        DialogRepaymentFPFragment dialogRepaymentFPFragment = new DialogRepaymentFPFragment();

        dialogRepaymentFPFragment.show(mFragmentManager, "DialogRepaymentFPFragment");

    }

    private class AsyncSubmit extends AsyncTask<Void, Void, Boolean> {
        //        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(getContext());
//            progressDialog.setMessage("Inserting repayment data...");
//            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return submitRepaymnetData();
        }

        @Override
        protected void onPostExecute(Boolean resFlag) {
            super.onPostExecute(resFlag);
//            progressDialog.dismiss();

            if (resFlag) {
                toast.setText("Repayment Successfully Added");
                toast.show();
                repayments.clear();
                recAdapter.notifyDataSetChanged();
                recView.removeAllViews();

            } else {
                toast.setText("Repayment UnSuccessful");
                toast.show();
            }


        }
    }

    private boolean submitRepaymnetData() {
        Log.e(TAG, "submitRepaymnetData: repayments: " + repayments.size());
        for (Repayment repayment : repayments) {
            RepaymentLog repaymentLog = new RepaymentLog();
            if (repayment.getRepaymentAmount() == null || repayment.getRepaymentAmount().equalsIgnoreCase("") || repayment.getRepaymentAmount().equalsIgnoreCase("0"))
                continue;
            repaymentLog.setCustomerGroupId(repayment.getCustomerGroupId() == null ? "" : repayment.getCustomerGroupId());
            repaymentLog.setLoanId(repayment.getLoanId());
            repaymentLog.setCustomerName(repayment.getCustomerName());
            repaymentLog.setNICNumber(repayment.getNICNumber());
            repaymentLog.setRepaymentAmount(repayment.getRepaymentAmount());
            repaymentLog.setRepaymentDateTime(repayment.getRepaymentDateTime());
            repaymentLog.setPenalty(repayment.getPenalty());
            repaymentLog.setProccessingFees(repayment.getProccessingFees());
            repaymentLog.setStationId(repayment.getStationId());
            long res = dbHandler.insertRepaymentLogData(repaymentLog);
            Log.d(TAG, "insertRepaymentLogData result: " + res);
            if (res == -1) {
                return false;
            }
            //TODO Previous Repayment Insertion Code
            /*long res = dbHandler.updateRepaymentData(repayment);
            Log.d(TAG, "updateRepaymentData result: " + res);
            if (res == 0) {
                return false;
            }*/
        }
        return true;
    }

    public void loadMore(int i) {
        repayments.clear();
        recView.removeAllViews();
        recView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        switch (i) {
            //    By LoanId
            case 0:
                repayments.addAll(dbHandler.getRepaymentDataByLoanId(Integer.parseInt(searchValueEdt.getText().toString())));
                break;
            //    By Customer NIC
            case 1:
                repayments.addAll(dbHandler.getRepaymentDataByNic(searchValueEdt.getText().toString()));
                break;

            //    By Finger Print
            case 2:
                repayments.addAll(dbHandler.getRepaymentDataByLoanId(loanId));
                break;
        }
        Log.d(TAG, "loadMore: repayments size: " + repayments.size());
        if (repayments.size() == 0) {
            recView.setVisibility(View.INVISIBLE);
            notAvailableTxt.setVisibility(View.VISIBLE);
        } else {
            recView.setVisibility(View.VISIBLE);
            notAvailableTxt.setVisibility(View.INVISIBLE);
        }
        holderList.clear();
        recAdapter.notifyDataSetChanged();
    }
}
