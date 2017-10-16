package activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gc.materialdesign.views.ProgressBarDeterminate;
import com.shery.safco.R;

import java.io.IOException;
import java.util.ArrayList;

import customclasses.ServerResponseParser;

import customclasses.Stations;
import databases.DBHandler;
import retrofit.ApiClient;
import retrofit.ApiInterface;
import retrofit2.Call;

/**
 * Created by shery on 11/29/2016.
 */

public class SyncDownActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressBarDeterminate pg1;
    private Button syncDownBtn;
    private DBHandler dbHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_down);
        dbHandler = new DBHandler(this);
        pg1 = (ProgressBarDeterminate) findViewById(R.id.pg1);
        syncDownBtn = (Button) findViewById(R.id.syncDownBtn);

    }

    @Override
    public void onClick(View view) {


    }

    public class AsyncGetData extends AsyncTask<Void, Void, ServerResponseParser>{

        @Override
        protected ServerResponseParser doInBackground(Void... voids) {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<ServerResponseParser> call = apiService.getStationData();
            try {
                ServerResponseParser serverResponseParser = call.execute().body();
                return serverResponseParser;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ServerResponseParser serverResponseParser) {
            super.onPostExecute(serverResponseParser);
            if (serverResponseParser!=null){
                insertStationData(serverResponseParser.getStationList());

            }
        }

    }

    public void insertStationData(ArrayList<Stations> stationList){
        pg1.setMax(stationList.size()-1);
        for (int i=0;i<stationList.size();i++){

            pg1.setProgress(i);

        }

    }
}
