package dp.com.nabbtabase.servise.repository;

import dp.com.nabbtabase.servise.model.request.CheckCodeRequest;
import dp.com.nabbtabase.servise.model.request.CreateCommentRequest;
import dp.com.nabbtabase.servise.model.request.ForgetPasswordRequest;
import dp.com.nabbtabase.servise.model.request.LoginRequest;
import dp.com.nabbtabase.servise.model.request.RegisterRequest;
import dp.com.nabbtabase.servise.model.request.ResetPasswordRequest;
import dp.com.nabbtabase.servise.model.response.CheckCodeResponse;
import dp.com.nabbtabase.servise.model.response.CountryResponse;
import dp.com.nabbtabase.servise.model.response.LoginRegisterResponse;
import dp.com.nabbtabase.servise.model.response.ProductCommentsResponse;
import dp.com.nabbtabase.servise.model.response.Products;
import dp.com.nabbtabase.servise.model.response.StringResponse;
import dp.com.nabbtabase.utils.ConfigurationFile;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EndPoints {

    // TODO: 12/3/2018    login
    @POST(ConfigurationFile.UrlConstants.LOGIN_URL)
    Observable<Response<LoginRegisterResponse>> login(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Body LoginRequest loginRequest);

    // TODO: 12/3/2018 register
    @POST(ConfigurationFile.UrlConstants.CLIENT_REGISTER_URL)
    Observable<Response<LoginRegisterResponse>> register(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept,@Body RegisterRequest request);

    // TODO: 12/3/2018 get countries
    @GET(ConfigurationFile.UrlConstants.COUNTRIES_URL)
    Observable<Response<CountryResponse>>getCountries(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept);

    // TODO: 12/4/2018 get products offers
    @GET(ConfigurationFile.UrlConstants.OFFERS)
    Observable<Response<Products>>getOffers(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept);

    // TODO: 12/4/2018 get products bset seller
    @GET(ConfigurationFile.UrlConstants.BESET_SELLER)
    Observable<Response<Products>>getBesetSeller(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept);

    // TODO: 12/5/2018 get products
    @GET(ConfigurationFile.UrlConstants.PRODUCTS_SEARCH)
    Observable<Response<Products>>getProducts(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Query("sort") String sort,@Query("category") String subCategoryId);

    // TODO: 12/10/2018 create comment
    @POST(ConfigurationFile.UrlConstants.CREATE_COMMENT)
    Observable<Response<StringResponse>>createComment(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization")String token, @Body CreateCommentRequest request);

    // TODO: 12/10/2018 get company comments
    @GET(ConfigurationFile.UrlConstants.GET_PRODUCT_COMMENTS)
    Observable<Response<ProductCommentsResponse>>getProductComments(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Path("id")int id);

    // TODO: 12/10/2018 forget password
    @POST(ConfigurationFile.UrlConstants.FORGET_PASSWORD_URL)
    Observable<Response<StringResponse>>forgetPassword(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept,@Body ForgetPasswordRequest request);

    // TODO: 12/12/2018 check code
    @POST(ConfigurationFile.UrlConstants.CHECK__CODE)
    Observable<Response<CheckCodeResponse>>checkCode(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Body CheckCodeRequest request);

    // TODO: 12/15/2018 reset password
    @POST(ConfigurationFile.UrlConstants.RESET_PASSWORD_URL)
    Observable<Response<StringResponse>>resetPassword(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Path("token")String token, @Body ResetPasswordRequest resetPasswordRequest);


}
