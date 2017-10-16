package customclasses;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import activities.SyncUpActivity;
import fragments.CustomerInfoFragment;
import id.zelory.compressor.Compressor;

/**
 * Created by shery on 12/13/2016.
 */

public class CommonMethod {
    private static final String TAG = CommonMethod.class.getSimpleName();

    public static boolean checkEmpty(EditText edt) {
        return TextUtils.isEmpty(edt.getText().toString());
    }

    public static boolean isGpsOn(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(context, "Please Turn On Your GPS", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }

    public static void alert(Context context, String title, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public static int getCheckedChildIndex(RadioGroup radioButtonGroup) {
        int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
        View radioButton = radioButtonGroup.findViewById(radioButtonID);
        return radioButtonGroup.indexOfChild(radioButton);
    }

    public static void createDir(String filePath) {
        Log.e(TAG, "createDir Called");
        File folder = new File(Environment.getExternalStorageDirectory() + "/Safco Microfinance/" + filePath + "/");
        Log.e(TAG, "Dir Name: " + folder.getAbsolutePath());
        boolean success = true;
        if (!folder.exists()) {
            Log.e(TAG, "Dir Not Exist");
            success = folder.mkdirs();
            Log.e(TAG, "Dir Exist: " + success);
        }

        if (success) {
            Log.e(TAG, "Folder Successfuly Created");
            // Do something on success
        } else {
            // Do something else on failure
        }
    }

    public static String getFileNameFromPath(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    public static void alert(Context context, String title, String message, String posMsg) {
        AlertDialog alertDialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(posMsg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        alertDialog = builder.create();
        alertDialog.show();

    }

    public static Bitmap compressImage(File imgFile, Context context) {
        Bitmap compressedImgBitmap = new Compressor.Builder(context)
                .setMaxWidth(640)
                .setMaxHeight(480)
                .setCompressFormat(Bitmap.CompressFormat.PNG)
                .build()
                .compressToBitmap(imgFile);


        return compressedImgBitmap;
    }

    public static boolean checkIfColumnExits(SQLiteDatabase db, String tableName, String columnName) {

        ArrayList<String> columnNames = new ArrayList<>();
        String query = "PRAGMA table_info('" + tableName + "')";
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();
        do {
            columnNames.add(res.getString(res.getColumnIndex("name")));
        } while (res.moveToNext());
        res.close();
        return columnNames.contains(columnName);
    }

    public static String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 75, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string

        return Base64.encodeToString(byteFormat, Base64.DEFAULT);
    }

    public static void setSpinnerSelectedItem(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public static Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                int color = bmpOriginal.getPixel(x, y);
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = color & 0xFF;
                int gray = (r + g + b) / 3;
                color = Color.rgb(gray, gray, gray);
                bmpGrayscale.setPixel(x, y, color);
            }
        }
        return bmpGrayscale;
    }

    public static Bitmap getBitmap2(String imgString) {
        byte[] decodedStringByte = Base64.decode(imgString, Base64.DEFAULT);

        ByteArrayInputStream imageStream = new ByteArrayInputStream(
                decodedStringByte);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        imageStream.reset();


        return theImage;
    }

    /*static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break;  // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }*/
    public static void showUnavailableToast(Context context) {
        Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setText("Currently Unavailable!");
        toast.show();
    }

    public static Bitmap getBitmap(String imgString) {
        byte[] decodedStringByte = Base64.decode(imgString, Base64.DEFAULT);
        Log.e(TAG, "getBitmap: " + decodedStringByte.length);
        return BitmapFactory.decodeByteArray(decodedStringByte, 0, decodedStringByte.length);
    }

    public static Bitmap byteArrayToBitmap(byte[] byteImage) {
        return BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static void writeToFile(String data) {
        Log.e(TAG, "writeToFile: File about to be created");
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "safco_json_data.txt");

        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append(data);
            fileWriter.flush();
            fileWriter.close();
            Log.e(TAG, "writeToFile: file created: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "writeToFile: Exception");
        }
    }
    public static void retainFocus(EditText editText){
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    public static void pickDate(Context context, final EditText editText) {

        Calendar newCalender = Calendar.getInstance();
        DatePickerDialog mDatePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Date curDate = newDate.getTime();
                editText.setText(CommonConst.DateFormater.format(curDate));

            }
        }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.show();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean flag = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        if (!flag)
            alert(context, "Connectivity Error", "No Internet Connnection!");
        return flag;
    }


}
