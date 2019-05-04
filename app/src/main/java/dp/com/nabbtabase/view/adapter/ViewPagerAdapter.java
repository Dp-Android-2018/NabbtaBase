package dp.com.nabbtabase.view.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dp.com.nabbtabase.R;

public class ViewPagerAdapter extends PagerAdapter {
    Activity activity;
    List<String> imags;
    LayoutInflater inflater;

    public ViewPagerAdapter(Activity activity, List<String> imags) {
        this.activity = activity;
        this.imags = imags;
    }

    @Override
    public int getCount() {
        return imags.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_pager_item, container, false);

        ImageView imageView = view.findViewById(R.id.image);
        DisplayMetrics dis = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dis);

        int heigt = dis.heightPixels;
        int width = dis.widthPixels;
        imageView.setMinimumHeight(heigt);
        imageView.setMinimumWidth(width);

        Picasso.get().load(imags.get(position)).placeholder(R.drawable.product_icon).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
