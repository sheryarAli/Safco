package retrofit;

import customclasses.ServerResponseParse2;
import customclasses.ServerResponseParser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by shery on 11/18/2016.
 */

public interface ApiInterface {

    /*@GET("employees/AndroidDataBackup/")
    paramters
    @Query("type") String type, @Query("token") String token, @Query("aui") String aui
    */
    @GET("employees/tabData/getData.php")
    Call<ServerResponseParser> getStationData();

    @GET("employees/tabData/CustomersSyncDownStationwise.php")
    Call<ServerResponseParse2> getRepaymentData(@Query("StationId") int stationId);
    /*
    *

    * */
}
