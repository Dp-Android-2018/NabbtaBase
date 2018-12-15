package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.support.annotation.NonNull;
import android.view.View;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.servise.model.request.CreateCommentRequest;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.ValidationUtils;
import dp.com.nabbtabase.view.activity.ProductDetailedActivity;
import dp.com.nabbtabase.view.callback.CallBackInterface;

public class ProductItemViewModel {

    public Product product;
    private Context context;

    public ProductItemViewModel(Product product, Context context) {
        this.product=product;
        this.context=context;
    }



    public String getSalePrice(){
        return String.valueOf(product.getSalePrice());
    }

    public String getImage(){
        return product.getImageUrls().size()>0?product.getImageUrls().get(0):"";
    }

    public String getDimension(){
        return String.valueOf(product.getHeight())+"cm x "
                +String.valueOf(product.getWidth())+"cm";
    }

    public void onItemClick(View view){
        Intent intent=new Intent(context,ProductDetailedActivity.class);
        intent.putExtra(ConfigurationFile.IntentConstants.PRODUCT_DATA,product);
        context.startActivity(intent);
    }



}
