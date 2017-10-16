package fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFloat;
import com.shery.safco.R;

import activities.DocumentActivity;
import activities.LoanActivity;
import activities.QuestionnaireActivity;
import adapters.GenericAdapter;
import customclasses.CommonConst;
import customclasses.Loan;
import databases.DBHandler;

/**
 * Created by shery on 11/15/2016.
 */

public class LoanFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = LoanFragment.class.getSimpleName();
    private RecyclerView recView;
    private ButtonFloat addBtn;
    private GenericAdapter<Loan> recAdapter;
    private DBHandler dbHandler;
    private Toast toast;
    CharSequence menu[] = new CharSequence[]{"View", "Poverty Score Card"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan, container, false);
        dbHandler = new DBHandler(getContext());
        recView = (RecyclerView) view.findViewById(R.id.recView);
        addBtn = (ButtonFloat) view.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);
        toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        int customerId = ((LoanActivity) getActivity()).getCustomerId();
        recAdapter = new GenericAdapter<Loan>(getContext(), dbHandler.getLoanData(customerId)) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.asset_row_item, parent, false);
                view.setOnClickListener(this);
                ItemViewHolder viewHolder = new ItemViewHolder(view);

                return viewHolder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, Loan val, int position) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.cardView.setTag(position);

                itemViewHolder.text1.setText("Loan Id : " + val.getLoanId());
                itemViewHolder.text2.setText("Loan Term: " + val.getLoanTerm());
                /*
                itemViewHolder.text3.setText("Relation: " );*/
            }

            @Override
            public void onItemClick(View view) {

                Loan val = getItem(Integer.valueOf(view.getTag() + ""));
                showMenu(val);

            }
        };
        recView.setAdapter(recAdapter);

    }

    @Override
    public void onClick(View view) {
        if (view == addBtn) {
            if (recAdapter.getItemCount()>0){
                toast.setText("Loan Already In Proccess...");
                toast.show();
            }else {
                Bundle args = new Bundle();
                args.putString(CommonConst.VIEW_TYPE, "new");
                ((LoanActivity) getActivity()).moveToLoanFormFrag(args);
            }


        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView text1, text2, text3;


        public ItemViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            text1 = (TextView) itemView.findViewById(R.id.text1);
            text2 = (TextView) itemView.findViewById(R.id.text2);
            text3 = (TextView) itemView.findViewById(R.id.text3);
            /*text2.setVisibility(View.INVISIBLE);*/
            text3.setVisibility(View.INVISIBLE);
        }
    }

    public void showMenu(final Loan item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Action");
        builder.setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                
                switch (which) {
                    case 0:
                        Bundle args = new Bundle();
                        args.putSerializable("Loan",item);
                        ((LoanActivity) getActivity()).moveToLoanFormFrag(args);
                        break;
                    case 1:
                        intent = new Intent(getContext(), QuestionnaireActivity.class);
                        intent.putExtra("Loan",item);
                        startActivity(intent);
                        break;
                    /*case 2:
                        intent = new Intent(getContext(), DocumentActivity.class);
                        startActivity(intent);
                        break;*/
                }

                Log.e(TAG, "option: " + which);

            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

