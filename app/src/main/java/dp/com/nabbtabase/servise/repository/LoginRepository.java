package dp.com.nabbtabase.servise.repository;

import android.database.Observable;

import dp.com.nabbtabase.modules.ApiClient;
import dp.com.nabbtabase.modules.NetworkModule;
import dp.com.nabbtabase.servise.model.request.LoginRequest;
import dp.com.nabbtabase.servise.model.response.LoginRegisterResponse;
import dp.com.nabbtabase.utils.ConfigurationFile;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public abstract class LoginRepository {

    @POST(ConfigurationFile.UrlConstants.LOGIN_URL)
    abstract Observable<Response<LoginRegisterResponse>>login(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Body LoginRequest loginRequest);



}
