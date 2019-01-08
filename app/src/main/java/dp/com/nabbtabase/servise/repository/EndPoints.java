package dp.com.nabbtabase.servise.repository;

import android.databinding.ObservableField;
import android.support.annotation.StringRes;

import java.util.List;

import dp.com.nabbtabase.servise.model.request.AddToCartRequest;
import dp.com.nabbtabase.servise.model.request.ChangePasswordRequest;
import dp.com.nabbtabase.servise.model.request.CheckCodeRequest;
import dp.com.nabbtabase.servise.model.request.CreateCommentRequest;
import dp.com.nabbtabase.servise.model.request.CreateOrderRequest;
import dp.com.nabbtabase.servise.model.request.EditProfileRequest;
import dp.com.nabbtabase.servise.model.request.ForgetPasswordRequest;
import dp.com.nabbtabase.servise.model.request.LoginRequest;
import dp.com.nabbtabase.servise.model.request.RegisterRequest;
import dp.com.nabbtabase.servise.model.request.ResetPasswordRequest;
import dp.com.nabbtabase.servise.model.request.ServiceRequest;
import dp.com.nabbtabase.servise.model.request.ShippingAddressRequest;
import dp.com.nabbtabase.servise.model.request.UpdateCartItemRequest;
import dp.com.nabbtabase.servise.model.response.CartProductsResponse;
import dp.com.nabbtabase.servise.model.response.CategoryResponse;
import dp.com.nabbtabase.servise.model.response.CheckCodeResponse;
import dp.com.nabbtabase.servise.model.response.CountryResponse;
import dp.com.nabbtabase.servise.model.response.CreateOrderResponse;
import dp.com.nabbtabase.servise.model.response.ImageResponse;
import dp.com.nabbtabase.servise.model.response.LoginRegisterResponse;
import dp.com.nabbtabase.servise.model.response.ProductCommentsResponse;
import dp.com.nabbtabase.servise.model.response.Products;
import dp.com.nabbtabase.servise.model.response.ServicesResponse;
import dp.com.nabbtabase.servise.model.response.ShippingAddressResponse;
import dp.com.nabbtabase.servise.model.response.StringResponse;
import dp.com.nabbtabase.utils.ConfigurationFile;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
    Observable<Response<Products>>getProducts(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept,@Header("Authorization")String token, @Query("sort") String sort,@Query("category") String subCategoryId,@Query("page")String pageId);

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

    // TODO: 12/15/2018 update profile
    @PUT(ConfigurationFile.UrlConstants.EDIT_PROFILE_URL)
    Observable<Response<StringResponse>>editProfile(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization")String token, @Body EditProfileRequest request);

    // TODO: 12/16/2018 change password
    @POST(ConfigurationFile.UrlConstants.CHANGE_PASSWORD_URL)
    Observable<Response<StringResponse>>changePassword(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization")String token, @Body ChangePasswordRequest request);

    // TODO: 12/16/2018 shipping address
    @POST(ConfigurationFile.UrlConstants.SHIPPING_ADDRESS_URL)
    Observable<Response<ShippingAddressResponse>>shippingAddress(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization")String token, @Body ShippingAddressRequest request);

    // TODO: 12/17/2018 get services
    @GET(ConfigurationFile.UrlConstants.SERVICES_URL)
    Observable<Response<ServicesResponse>>getServices(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept);

    // TODO: 12/19/2018 get categories
    @GET(ConfigurationFile.UrlConstants.CATEGORIES_URL)
    Observable<Response<CategoryResponse>>getCategories(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept);

    // TODO: 12/22/2018 upload image
    @Multipart
    @POST(ConfigurationFile.UrlConstants.UPLOAD_IMAGE)
    Observable<Response<ImageResponse>>uploadImage(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept,@Part List<MultipartBody.Part> file);

    // TODO: 12/24/2018 request a service
    @POST(ConfigurationFile.UrlConstants.SERVICE_REQUEST)
    Observable<Response<StringResponse>>requestService(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization")String token, @Body ServiceRequest request);

    // TODO: 12/25/2018 add product to cart
    @POST(ConfigurationFile.UrlConstants.ADD_TO_CART_ULR)
    Observable<Response<StringResponse>>addProductToCart(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization")String token, @Body AddToCartRequest request);

    // TODO: 12/25/2018 get all cart products
    @GET(ConfigurationFile.UrlConstants.CART_PRODUCTS)
    Observable<Response<CartProductsResponse>>getCartProducts(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization")String token);

    // TODO: 12/26/2018 delete item from cart
    @DELETE(ConfigurationFile.UrlConstants.DELETE_ITEM_FROM_CART)
    Observable<Response<StringResponse>>deleteItemFromCart(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization")String token,@Path("id")int id);

    // TODO: 12/30/2018 update cart item product
    @PUT(ConfigurationFile.UrlConstants.UPDATE_CART_ITEM)
    Observable<Response<StringResponse>> updateCartItem(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization")String token, @Path("id")int id, @Body UpdateCartItemRequest request);

    // TODO: 12/30/2018 get Services History
    @GET(ConfigurationFile.UrlConstants.SERVICES_HISTORY)
    Observable<Response<StringResponse>>getServicesHistory(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization")String token);

    // TODO: 1/6/2019 create order
    @POST(ConfigurationFile.UrlConstants.CREATE_ORDER)
    Observable<Response<CreateOrderResponse>>createOrder(@Header("x-api-key") String key, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization")String token, @Body CreateOrderRequest request);


}
