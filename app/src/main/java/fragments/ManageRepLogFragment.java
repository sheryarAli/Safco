package fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
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

import java.util.ArrayList;

import adapters.GenericAdapter;
import customclasses.CommonMethod;
import customclasses.NicTextWatcher;
import customclasses.Repayment;
import customclasses.RepaymentLog;
import databases.DbHandler2;

/**
 * Created by shery on 9/27/2017.
 */

public class ManageRepLogFragment extends Fragment implements View.OnClickListener {
    private final String TAG = "ManageRepLogFragment";
    private Button searchBtn;
    private RadioGroup rgSearchType;
    private RecyclerView recView;
    private TextView notAvailableTxt;
    private EditText searchValueEdt;
    private GenericAdapter<RepaymentLog> recAdapter;
    private ArrayList<RepaymentLog> repaymentLogs;
    private LinearLayoutManager linearLayoutManager;
    private DbHandler2 dbHandler;
    private Toast toast;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_rep_log, container, false);
        searchBtn = (Button) view.findViewById(R.id.searchBtn);
        rgSearchType = (RadioGroup) view.findViewById(R.id.rgSearchType);
        recView = (RecyclerView) view.findViewById(R.id.recView);
        notAvailableTxt = (TextView) view.findViewById(R.id.notAvailableTxt);
        notAvailableTxt.setVisibility(View.INVISIBLE);
        searchValueEdt = (EditText) view.findViewById(R.id.searchValueEdt);
        searchBtn.setOnClickListener(this);
        toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        dbHandler = new DbHandler2(getContext());
        linearLayoutManager = new LinearLayoutManager(getContext());
        recView.setLayoutManager(linearLayoutManager);
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
                        CommonMethod.retainFocus(searchValueEdt);
                        searchValueEdt.setOnClickListener(null);
                        break;

                    case 1:
                        searchValueEdt.setHint("NIC here...");
                        searchValueEdt.setInputType(InputType.TYPE_CLASS_TEXT);
                        searchValueEdt.setSelection(searchValueEdt.getText().toString().length());
                        searchValueEdt.setFilters(NicTextWatcher.getFilter());
                        CommonMethod.retainFocus(searchValueEdt);
                        searchValueEdt.setOnClickListener(null);
                        searchValueEdt.addTextChangedListener(NicTextWatcher.getInstance());
                        break;
                    case 2:
                        searchValueEdt.setHint("Date here...");
                        searchValueEdt.setInputType(InputType.TYPE_CLASS_DATETIME);
                        searchValueEdt.setFocusable(false);
                        searchValueEdt.setOnClickListener(ManageRepLogFragment.this);
                        searchValueEdt.removeTextChangedListener(NicTextWatcher.getInstance());
                        break;
                }

            }
        });

        repaymentLogs = new ArrayList<>();

        recAdapter = new GenericAdapter<RepaymentLog>(getContext(), repaymentLogs) {

            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                Log.d(TAG, "setViewHolder: ");
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.row_item_repayment_log, parent, false);
                view.setOnClickListener(this);
                ItemViewHolder viewHolder = new ItemViewHolder(view);
                return viewHolder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, final RepaymentLog val, final int position) {
                final ItemViewHolder viewHolder = (ItemViewHolder) holder;
                viewHolder.custNameTxt.setText(val.getCustomerName());
                viewHolder.loanIdTxt.setText(val.getLoanId() + "");
                viewHolder.nicTxt.setText(val.getNICNumber());
                viewHolder.customRepAmountTextWatcher.previousRepAmount = val.getRepaymentAmount();
                viewHolder.repAmountEdt.setText(val.getRepaymentAmount());
                viewHolder.repDateTxt.setText(val.getRepaymentDateTime());
                viewHolder.penaltyTxt.setText(val.getPenalty());
                viewHolder.proccessingFeesTxt.setText(val.getProccessingFees());
                viewHolder.updateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "onClick: instanceOf Button:" + (view instanceof Button));
                        if (!CommonMethod.checkEmpty(viewHolder.repAmountEdt)) {
                            if (dbHandler.updateRepaymentAmountInLog(val.getId(), viewHolder.repAmountEdt.getText().toString())) {
                                ((Button) view).setEnabled(false);
                                val.setRepaymentAmount(viewHolder.repAmountEdt.getText().toString());
                                Toast.makeText(context, "Successfull Update", Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                });
            }

            @Override
            public void onItemClick(View view) {

            }
        };

        recView.setAdapter(recAdapter);
        return view;
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView custNameTxt, loanIdTxt, nicTxt, repDateTxt, penaltyTxt, proccessingFeesTxt;
        private EditText repAmountEdt;
        private Button updateBtn;
        private CustomRepAmountTextWatcher customRepAmountTextWatcher;

        public ItemViewHolder(View itemView) {
            super(itemView);
            custNameTxt = (TextView) itemView.findViewById(R.id.custNameTxt);
            loanIdTxt = (TextView) itemView.findViewById(R.id.loanIdTxt);
            nicTxt = (TextView) itemView.findViewById(R.id.nicTxt);
            repDateTxt = (TextView) itemView.findViewById(R.id.repDateTxt);
            penaltyTxt = (TextView) itemView.findViewById(R.id.penaltyTxt);
            proccessingFeesTxt = (TextView) itemView.findViewById(R.id.proccessingFeesTxt);
            repAmountEdt = (EditText) itemView.findViewById(R.id.repAmountEdt);
            updateBtn = (Button) itemView.findViewById(R.id.updateBtn);
            customRepAmountTextWatcher = new CustomRepAmountTextWatcher("", updateBtn);
            repAmountEdt.addTextChangedListener(customRepAmountTextWatcher);

        }

    }

    private class CustomRepAmountTextWatcher implements TextWatcher {
        private String previousRepAmount;
        private Button updateButton;

        public CustomRepAmountTextWatcher(String previousRepAmount, Button updateButton) {
            this.previousRepAmount = previousRepAmount;
            this.updateButton = updateButton;

        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!charSequence.toString().equalsIgnoreCase(previousRepAmount)) {
                updateButton.setEnabled(true);
            } else {
                updateButton.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
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
        if (view == searchBtn) {
            int checkedIndex = CommonMethod.getCheckedChildIndex(rgSearchType);
            switch (checkedIndex) {
                case -1:
                    searchValueEdt.setText("");
                    toast.setText("PLease select any search type");
                    toast.show();
                    break;
                //    Search By LoanId
                case 0:
                    if (checkSearchValueEdt()) {
                        loadMore(0);
                    }
                    break;
                //    Search By NIC
                case 1:
                    if (checkSearchValueEdt()) {
                        loadMore(1);
                    }
                    break;
                //    Search By Date
                case 2:
                    if (checkSearchValueEdt()) {
                        loadMore(2);
                    }
                    break;
            }
        } else if (view == searchValueEdt) {
            CommonMethod.pickDate(getContext(), searchValueEdt);
        }

    }

    public void loadMore(int i) {
        repaymentLogs.clear();
        switch (i) {
            //    By LoanId
            case 0:
                repaymentLogs.addAll(dbHandler.getRepaymentLogDataByLoanId(Integer.parseInt(searchValueEdt.getText().toString())));

                break;
            //    By Customer NIC
            case 1:
                repaymentLogs.addAll(dbHandler.getRepaymentLogDataByNic(searchValueEdt.getText().toString()));
                break;

            //    By Finger Print
            case 2:

                repaymentLogs.addAll(dbHandler.getRepaymentLogDataByDate(searchValueEdt.getText().toString()));
                break;
        }
        Log.d(TAG, "loadMore: repaymentLogs size: " + repaymentLogs.size());
        if (repaymentLogs.size() == 0) {
            recView.setVisibility(View.INVISIBLE);
            notAvailableTxt.setVisibility(View.VISIBLE);
        } else {
            recView.setVisibility(View.VISIBLE);
            notAvailableTxt.setVisibility(View.INVISIBLE);
        }
        recAdapter.notifyDataSetChanged();
    }
}
