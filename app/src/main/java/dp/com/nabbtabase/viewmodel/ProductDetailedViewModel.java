package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import android.content.Context;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableFloat;
import androidx.annotation.NonNull;
import android.view.View;

import java.util.List;

import dp.com.nabbtabase.application.MyApp;
import dp.com.nabbtabase.servise.model.pojo.Comment;
import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.servise.model.request.CreateCommentRequest;
import dp.com.nabbtabase.servise.model.response.StringResponse;
import dp.com.nabbtabase.servise.repository.AddToCartRepository;
import dp.com.nabbtabase.servise.repository.CreateCommentRepository;
import dp.com.nabbtabase.servise.repository.DeleteItemFromCartRepository;
import dp.com.nabbtabase.servise.repository.ProductCommentsRepository;
import dp.com.nabbtabase.servise.repository.RelatedProductsRepository;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.utils.ValidationUtils;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import retrofit2.Response;

public class ProductDetailedViewModel extends AndroidViewModel {

    public Product product;
    private Context context;
    public ObservableField<String> comment;
    public ObservableFloat rate;
    private CreateCommentRequest commentRequest;
    private CallBackInterface callBackInterface;
    private String token;
    private Application application;
    private final LiveData<List<Comment>> comments;
    private LiveData<Response<StringResponse>> addToCartResponse;
    private LiveData<List<Product>> relatedProducts;
    private LiveData<Integer> deleteItemCode;
    public ObservableBoolean inCart;

    public ProductDetailedViewModel(@NonNull Application application, Product product) {
        super(application);
        this.application = application;
        this.product = product;
        inCart = new ObservableBoolean();
        if (product.isInCart()) {
            inCart.set(true);
        } else {
            inCart.set(false);
        }
        System.out.println("is in cart is in observe  : " + inCart.get());
        System.out.println("is in cart is   : " + product.isInCart());


        comments = ProductCommentsRepository.getInstance().getComments(application, product.getId());
        relatedProducts = RelatedProductsRepository.getInstance().getRelatedProduct(application, product.getCategory().getId());
        //System.out.println("Comments size adapter : "+this.comments.getValue().size());
        initVariables();
    }

    public void initVariables() {
        comment = new ObservableField<>();
        rate = new ObservableFloat();
        commentRequest = new CreateCommentRequest();

        if (CustomUtils.getInstance().getSaveUserObject(application) != null) {
            token = "Bearer " + CustomUtils.getInstance().getSaveUserObject(application).getApiToken();
        } else {
            token = null;
        }
    }

    public String getSalePrice() {
        return String.valueOf(product.getSalePrice()) + " R.S";
    }


    public String getDimension() {
        return String.valueOf(product.getHeight()) + "cm x "
                + String.valueOf(product.getWidth()) + "cm";
    }

    public void setCallBackInterface(CallBackInterface callBackInterface, Context context) {
        this.callBackInterface = callBackInterface;
        this.context = context;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void createComment(View view) {
        if (rate.get() <= 0 || ValidationUtils.isEmpty(comment.get())) {
            callBackInterface.updateUi(ConfigurationFile.Constants.FILL_ALL_DATA_ERROR_CODE);
        } else if (token == null) {
            CustomUtils.getInstance().showRegisterAlertDialog(context);
            //callBackInterface.errorMessage(context.getResources().getString(R.string.you_must_be_Register));
        } else {
            commentRequest.setComment(comment.get());
            commentRequest.setRate(rate.get());
            commentRequest.setProductId(product.getId());
            CreateCommentRepository.getInstance().createComment(application, commentRequest, token);
        }
    }

    public LiveData<Response<StringResponse>> getAddToCartResponse() {
        addToCartResponse = AddToCartRepository.getInstance().addToCart(application, token, product.getId());
        return addToCartResponse;
    }


    public LiveData<List<Comment>> getComments() {
        return comments;
    }

    public LiveData<Integer> getDeleteItemCode() {

        return DeleteItemFromCartRepository.getInstance().deleteItemFromCart(MyApp.getInstance(), product.getId(), 0);
    }

    public LiveData<List<Product>> getRelatedProducts() {
        return relatedProducts;
    }
}
