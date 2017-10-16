package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gc.materialdesign.views.ButtonFloat;
import com.shery.safco.R;

import activities.DocumentActivity;
import adapters.GenericAdapter;
import customclasses.Customer;
import databases.DBHandler;

/**
 * Created by shery on 12/15/2016.
 */

public class DocumentFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = DocumentFragment.class.getSimpleName();
    /*private DBHandler dbHandler;
    private ButtonFloat addBtn;
    private RecyclerView recView;*/
    /*private GenericAdapter<Customer> recAdapter;*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        /*recView = (RecyclerView) view.findViewById(R.id.recView);
        addBtn = (ButtonFloat) view.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);*/
        return view;
    }

    @Override
    public void onClick(View view) {
        /*if (view == addBtn){
            ((DocumentActivity)getActivity()).moveToAddDocFragment();

        }*/

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
