package customclasses;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;

/**
 * Created by shery on 12/6/2016.
 */

public class NicTextWatcher implements TextWatcher {
    private static final String TAG = "NicTextWatcher";
    private static InputFilter[] filter;
    private int prevL = 0;
    private static NicTextWatcher nicTextWatcher;


    public static NicTextWatcher getInstance() {
        if (nicTextWatcher == null) {
            nicTextWatcher = new NicTextWatcher();
        }

        return nicTextWatcher;
    }

    public static InputFilter[] getFilter() {
        if (filter == null) {
            filter = new InputFilter[]{new InputFilter.LengthFilter(15)};
        }
        return filter;

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        prevL = charSequence.length();
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


    }

    @Override
    public void afterTextChanged(Editable editable) {

        if ((prevL < editable.length()) && (editable.length() == 5 || editable.length() == 13)) {

            editable.append("-");
        }
    }

}
