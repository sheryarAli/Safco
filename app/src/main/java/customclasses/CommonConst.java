package customclasses;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by shery on 12/18/2016.
 */

public class CommonConst {
    private static final String TAG = CommonConst.class.getSimpleName();
    public static final String RESPONSE_SUCCESS = "SUCCESS";
    public static final String KEY_IMAGE = "image";
    public static final String VIEW_TYPE = "view_type";
    public static final String KEY_CUR_ADD_DATE = "added_date_time";
    public static SimpleDateFormat DateFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
}
