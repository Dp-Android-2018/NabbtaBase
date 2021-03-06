package dp.com.nabbtabase.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.FragmentAccountBinding;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import dp.com.nabbtabase.viewmodel.AccountViewModel;

public class AccountFragment extends Fragment implements CallBackInterface {
    AccountViewModel viewModel;
    FragmentAccountBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewModel=ViewModelProviders.of(this).get(AccountViewModel.class);
        viewModel.setContext(getContext());
        viewModel.setCallBackInterface(this);
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_account,container,false);
        binding.setViewModel(viewModel);
        View view=binding.getRoot();
        if(CustomUtils.getInstance().getSaveUserObject(getActivity())==null){
            binding.tvLog.setText(getResources().getString(R.string.login));
        }else {
            binding.tvLog.setText(getResources().getString(R.string.logout));
        }
        return view;
    }

    @Override
    public void updateUi(int code) {

    }

    @Override
    public void errorMessage(String error) {
            Snackbar.make(binding.clRoot,error,Snackbar.LENGTH_LONG).show();
    }
}
