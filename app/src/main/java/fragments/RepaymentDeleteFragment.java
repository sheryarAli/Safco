package fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
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

import java.util.ArrayList;

import adapters.GenericAdapter;
import customclasses.CommonMethod;
import customclasses.NicTextWatcher;
import customclasses.Repayment;
import customclasses.RepaymentLog;
import databases.DbHandler2;

/**
 * Created by shery on 9/14/2017.
 */

public class RepaymentDeleteFragment extends Fragment implements View.OnClickListener {
    private final String TAG = "RepaymentDeleteFragment";
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
    private CharSequence menu[] = new CharSequence[]{"Delete"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_repayment, container, false);
        searchBtn = (Button) view.findViewById(R.id.searchBtn);
        rgSearchType = (RadioGroup) view.findViewById(R.id.rgSearchType);
        recView = (RecyclerView) view.findViewById(R.id.recView);
        notAvailableTxt = (TextView) view.findViewById(R.id.notAvailableTxt);
        notAvailableTxt.setVisibility(View.INVISIBLE);
        searchValueEdt = (EditText) view.findViewById(R.id.searchValueEdt);
        toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        searchBtn.setOnClickListener(this);
        dbHandler = new DbHandler2(getContext());
        linearLayoutManager = new LinearLayoutManager(getContext());
        recView.setLayoutManager(linearLayoutManager);
        repaymentLogs = new ArrayList<>();
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
                        searchValueEdt.addTextChangedListener(NicTextWatcher.getInstance());
                        CommonMethod.retainFocus(searchValueEdt);
                        searchValueEdt.setOnClickListener(null);
                        break;
                    case 2:
                        searchValueEdt.setHint("Date here...");
                        searchValueEdt.setInputType(InputType.TYPE_CLASS_DATETIME);
                        searchValueEdt.setFocusable(false);
                        searchValueEdt.setOnClickListener(RepaymentDeleteFragment.this);
                        searchValueEdt.removeTextChangedListener(NicTextWatcher.getInstance());
                        break;
                }

            }
        });

        recAdapter = new GenericAdapter<RepaymentLog>(getContext(), repaymentLogs) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                Log.d(TAG, "setViewHolder: ");
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.row_item_del_repayment, parent, false);
                view.setOnClickListener(this);
                ItemViewHolder viewHolder = new ItemViewHolder(view);

                return viewHolder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, RepaymentLog val, int position) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.cardView.setTag(position);
                itemViewHolder.custNameTxt.setText(val.getCustomerName());
                itemViewHolder.loanIdTxt.setText(val.getLoanId() + "");
                itemViewHolder.nicTxt.setText(val.getNICNumber());
                itemViewHolder.repAmountTxt.setText(val.getRepaymentAmount());
                itemViewHolder.repDateTxt.setText(val.getRepaymentDateTime());
                itemViewHolder.penaltyTxt.setText(val.getPenalty());
                itemViewHolder.proccessingFeesTxt.setText(val.getProccessingFees());

            }

            @Override
            public void onItemClick(View view) {
                showMenu(getItem(Integer.valueOf(view.getTag() + "")));
            }
        };

        recView.setAdapter(recAdapter);

        return view;
    }


    public void showDeleteAlert(final RepaymentLog item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dbHandler.deleteRepaymentLogData(item.getId());
                repaymentLogs.remove(item);
                recAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void showMenu(final RepaymentLog repaymentLog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Action");
        builder.setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        showDeleteAlert(repaymentLog);
                        break;
                }
            }
        });
        builder.show();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView custNameTxt, loanIdTxt, nicTxt, repDateTxt, repAmountTxt, penaltyTxt, proccessingFeesTxt;

        public ItemViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            custNameTxt = (TextView) itemView.findViewById(R.id.custNameTxt);
            loanIdTxt = (TextView) itemView.findViewById(R.id.loanIdTxt);
            nicTxt = (TextView) itemView.findViewById(R.id.nicTxt);
            repDateTxt = (TextView) itemView.findViewById(R.id.repDateTxt);
            repAmountTxt = (TextView) itemView.findViewById(R.id.repAmountTxt);
            penaltyTxt = (TextView) itemView.findViewById(R.id.penaltyTxt);
            proccessingFeesTxt = (TextView) itemView.findViewById(R.id.proccessingFeesTxt);

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
                //   Search By LoanId
                case 0:
                    if (checkSearchValueEdt()) {
                        loadMore(0);
                    }
                    break;
                //   Search By NIC
                case 1:
                    if (checkSearchValueEdt()) {
                        loadMore(1);
                    }
                    break;
                //   Search By Date
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
                repaymentLogs.addAll(dbHandler.getSyncedRepaymentLogDataByLoanId(Integer.parseInt(searchValueEdt.getText().toString())));
                Log.d(TAG, "loadMore: repaymentLogs size: " + repaymentLogs.size());
                break;
            //    By Customer NIC
            case 1:
                repaymentLogs.addAll(dbHandler.getSyncedRepaymentLogDataByNic(searchValueEdt.getText().toString()));
                break;

            //    By Date
            case 2:
                repaymentLogs.addAll(dbHandler.getSyncedRepaymentLogDataByDate(searchValueEdt.getText().toString()));
                break;
            /*case 2:
                repaymentLogs.addAll(dbHandler.getRepaymentDataByLoanId(loanId));
                break;*/
        }
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
