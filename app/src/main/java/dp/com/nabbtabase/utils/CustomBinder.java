package dp.com.nabbtabase.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import dp.com.nabbtabase.R;

public class CustomBinder {

    @BindingAdapter({"bind:imageUrl"})
    public static void setImageUrl(ImageView imageView, String url){
        System.out.println("image url : "+url);
        if (url!=null && !url.equals(""))
            Picasso.with(imageView.getContext()).load(url).placeholder(R.drawable.product_icon).into(imageView);
    }

    @BindingAdapter({"bind:recyclerListener"})
    public static void onrecyclerListener(RecyclerView view, RecyclerView.OnScrollListener listener){
        view.addOnScrollListener(listener);
    }


}
