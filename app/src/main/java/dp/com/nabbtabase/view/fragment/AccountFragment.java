package dp.com.nabbtabase.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.FragmentAccountBinding;
import dp.com.nabbtabase.viewmodel.AccountViewModel;

public class AccountFragment extends Fragment {
    AccountViewModel viewModel;
    FragmentAccountBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewModel=ViewModelProviders.of(this).get(AccountViewModel.class);
        viewModel.setContext(getContext());
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_account,container,false);
        binding.setViewModel(viewModel);
        View view=binding.getRoot();
        return view;
    }
}
