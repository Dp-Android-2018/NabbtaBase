package dp.com.nabbtabase.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import dp.com.nabbtabase.MyViewModelFactory;
import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityProductDetailedBinding;
import dp.com.nabbtabase.servise.model.pojo.Comment;
import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.servise.repository.CreateCommentRepository;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.view.adapter.CommentsAdapter;
import dp.com.nabbtabase.view.adapter.ViewPagerAdapter;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import dp.com.nabbtabase.viewmodel.ProductDetailedViewModel;
import dp.com.nabbtabase.viewmodel.ProductItemViewModel;
import me.crosswall.lib.coverflow.CoverFlow;

public class ProductDetailedActivity extends AppCompatActivity implements CallBackInterface {
    ActivityProductDetailedBinding binding;
    private Product product;
    CommentsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product=(Product)getIntent().getSerializableExtra(ConfigurationFile.IntentConstants.PRODUCT_DATA);
        final ProductDetailedViewModel viewModel=ViewModelProviders.of(this,new MyViewModelFactory(this.getApplication(),product)).get(ProductDetailedViewModel.class);
        observeViewModel(viewModel);
        viewModel.setCallBackInterface(this,ProductDetailedActivity.this);
        binding=DataBindingUtil.setContentView(this, R.layout.activity_product_detailed);
        adapter=new CommentsAdapter();
        binding.rvComments.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.rvComments.setAdapter(adapter);
        CreateCommentRepository.getInstance().setCallBackInterface(this);
        binding.setViewModel(viewModel);
        initViewPager();
    }



    public void initViewPager(){
        binding.pagerContainer.setOverlapEnabled(true);
        final ViewPager viewPager=binding.pagerContainer.getViewPager();
        ViewPagerAdapter adapter=new ViewPagerAdapter(ProductDetailedActivity.this,product.getImageUrls());
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.setAdapter(adapter);

        new CoverFlow.Builder().with(viewPager)
                .scale(0.3f)
                .pagerMargin(-80)
                .spaceSize(0f)
                .build();
    }

    @Override
    public void updateUi(int code) {
        switch (code){
            case ConfigurationFile.Constants.FILL_ALL_DATA_ERROR_CODE:
            {
                Snackbar.make(binding.svRoot,R.string.fill_all_data_error_message,Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.SUCCESS_CODE_SECOND:
            {
                Snackbar.make(binding.svRoot,R.string.comment_successfully_message,Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.ALREADY_ACTIVATED:
            {
                Snackbar.make(binding.svRoot,R.string.comment_before_message,Snackbar.LENGTH_LONG).show();
                break;
            }

        }
    }

    @Override
    public void errorMessage(String error) {

    }

    private void observeViewModel(ProductDetailedViewModel viewModel){
        viewModel.getComments().observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(@Nullable List<Comment> comments) {
                if (comments!=null){
                    adapter.setComments(comments);
                }
            }
        });
    }
}
