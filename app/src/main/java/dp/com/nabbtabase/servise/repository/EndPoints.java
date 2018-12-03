package dp.com.nabbtabase.servise.repository;

import dp.com.nabbtabase.servise.model.request.LoginRequest;
import dp.com.nabbtabase.servise.model.request.RegisterRequest;
import dp.com.nabbtabase.servise.model.response.CountryResponse;
import dp.com.nabbtabase.servise.model.response.LoginRegisterResponse;
import dp.com.nabbtabase.utils.ConfigurationFile;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface EndPoints {

    // TODO: 12/3/2018    login
    @POST(ConfigurationFile.UrlConstants.LOGIN_URL)
    Observable<Response<LoginRegisterResponse>> login(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Body LoginRequest loginRequest);

    // TODO: 12/3/2018 register
    @POST(ConfigurationFile.UrlConstants.CLIENT_REGISTER_URL)
    Observable<Response<LoginRegisterResponse>> register(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept,RegisterRequest request);

    // TODO: 12/3/2018 get countries
    @GET(ConfigurationFile.UrlConstants.COUNTRIES_URL)
    Observable<Response<CountryResponse>>getCountries(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept);

}
