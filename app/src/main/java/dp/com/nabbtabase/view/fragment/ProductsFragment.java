package dp.com.nabbtabase.view.fragment;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.FragmentProductsBinding;
import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.servise.model.pojo.SubCategory;
import dp.com.nabbtabase.servise.model.response.Products;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.NetWorkConnection;
import dp.com.nabbtabase.view.adapter.CategoriesAdapter;
import dp.com.nabbtabase.view.adapter.ProductsAdapter;
import dp.com.nabbtabase.view.callback.CloseCountryDialogInterface;
import dp.com.nabbtabase.viewmodel.ProductsViewModel;
import retrofit2.Response;

public class ProductsFragment extends Fragment implements CloseCountryDialogInterface {
    FragmentProductsBinding binding;
    ProductsAdapter productAdapter;
    private String select;
    private Dialog dialog;
    CategoriesAdapter categoriesAdapter;
    private boolean countrySelected;
    List<SubCategory> subCategories;
    ProductsViewModel viewModel;
    private List<Product> productList;
    private String nextPageUrl = null;
    private String pageId = "0";
    private boolean isLoading = false;
    private String categoryId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false);
        initVariables();
        binding.ivGrid.setImageResource(R.drawable.grid_icon);
        binding.ivLinear.setImageResource(R.drawable.list_icon_clicked);
        binding.rvProducts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rvProducts.addOnScrollListener(onScrollListener());
        recyclerViewSelector();
        binding.tvCategory.setOnClickListener(v -> selectCategory());
        binding.tvSubCategory.setOnClickListener(v -> selectSubcategory());
        return binding.getRoot();
    }

    private void resetRecyclerViewAdapter() {
        productList.clear();
        productAdapter.setProducts(productList);
        binding.rvProducts.setAdapter(productAdapter);
    }

    public void initVariables() {
        productList = new ArrayList<>();
        subCategories = new ArrayList<>();
        countrySelected = false;
        categoryId = null;
        productAdapter = new ProductsAdapter(false);

        categoriesAdapter = new CategoriesAdapter(this);
    }

    public void recyclerViewSelector() {
        binding.ivLinear.setOnClickListener(v -> {
            binding.ivGrid.setImageResource(R.drawable.grid_icon);
            binding.ivLinear.setImageResource(R.drawable.list_icon_clicked);
            productAdapter.setGrid(false);
            binding.rvProducts.setAdapter(productAdapter);
            binding.rvProducts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        });

        binding.ivGrid.setOnClickListener(v -> {
            binding.ivGrid.setImageResource(R.drawable.grid_icon_clicked);
            binding.ivLinear.setImageResource(R.drawable.list_icon);
            binding.rvProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));
            productAdapter.setGrid(true);
            binding.rvProducts.setAdapter(productAdapter);
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);
        observableViewModel(viewModel);
    }

    public void observableViewModel(ProductsViewModel viewModel) {
        viewModel.getProducts().observe(this, new Observer<Response<Products>>() {
            @Override
            public void onChanged(@Nullable Response<Products> productsResponse) {
                if (productsResponse != null) {
                    if (productsResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                        System.out.println("Load More Data Loaded Code:");
                        System.out.println("Data Object Response :"+new Gson().toJson(productsResponse));
                        if (pageId.equals("0")) {
                            System.out.println("Load More Data Reset:");
                            resetRecyclerViewAdapter();
                        }
                        if (!TextUtils.isEmpty(productsResponse.body().getLinks().getNext())) {
                            nextPageUrl = productsResponse.body().getLinks().getNext();
                            System.out.println("Load More Data Next Page Data :" + nextPageUrl);
                            pageId = nextPageUrl.substring(nextPageUrl.length() - 1);
                        }else {
                            nextPageUrl = null;
                        }
                        isLoading = false;
                        productList.addAll(productsResponse.body().getProducts());
                        productAdapter.notifyDataSetChanged();
                    }
                }
            }

        });

        viewModel.getCategories().

                observe(this, categories ->

                {
                    if (categories != null) {
                        categoriesAdapter.setCategories(categories);
                    }
                });
    }


    public void selectCategory() {
        if (NetWorkConnection.isConnectingToInternet(getContext())) {
            select = "country";
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = getLayoutInflater();
            View filter = inflater.inflate(R.layout.country_dialog, null);
            builder.setView(filter);
            builder.setCancelable(true);
            dialog = builder.create();
            Window window = dialog.getWindow();
            window.setBackgroundDrawableResource(R.color.transparent);
            TextView title = filter.findViewById(R.id.tv_title);
            RecyclerView recyclerView = filter.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            title.setText("Categories");
            //Log.i("Size in vm ",""+getCountries().getValue().size());
            categoriesAdapter.setCategories(viewModel.getCategories().getValue());
            recyclerView.setAdapter(categoriesAdapter);
            dialog.show();
        }
    }

    public void selectSubcategory() {
        // Log.i("Countries size vm",""+countries.getValue().size());
        if (!countrySelected) {
            Snackbar.make(binding.clProductRoot, R.string.select_category_message, Snackbar.LENGTH_LONG).show();
            return;
        }
        select = "city";
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View filter = inflater.inflate(R.layout.country_dialog, null);
        builder.setView(filter);
        builder.setCancelable(true);
        dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        TextView title = filter.findViewById(R.id.tv_title);
        RecyclerView recyclerView = filter.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        title.setText("Sub Category");
        categoriesAdapter.setSubCategories(subCategories);
        recyclerView.setAdapter(categoriesAdapter);
        dialog.show();
    }


    @Override
    public void onListItemClicked(int postion) {
        if (select.equals("country")) {
            binding.tvSubCategory.setText("");
            subCategories = (viewModel.getCategories().getValue().get(postion).getSubCategories());
            binding.tvCategory.setText(viewModel.getCategories().getValue().get(postion).getName());
            countrySelected = true;
        } else if (select.equals("city")) {
            binding.tvSubCategory.setText(subCategories.get(postion).getName());
            //productList.clear();
            categoryId = String.valueOf(subCategories.get(postion).getId());
            pageId = "0";
            viewModel.getProducts(categoryId, pageId);
            //registerRequest.setCityId(cities.get(postion).getId());
        }
        dialog.dismiss();
    }

    public RecyclerView.OnScrollListener onScrollListener(){
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() == (productList.size() - 1)) {
                    if (!TextUtils.isEmpty(nextPageUrl) && isLoading == false) {
                        System.out.println("Load More Data Success :"+pageId);
                        isLoading = true;
                        viewModel.getProducts(categoryId, pageId);
                    }
                }
            }
        };
    }


}
