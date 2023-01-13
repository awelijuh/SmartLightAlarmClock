package com.awelijuh.smartlightalarmclock.view.fragments.accounts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awelijuh.smartlightalarmclock.R;
import com.awelijuh.smartlightalarmclock.databinding.FragmentAccountsBinding;
import com.awelijuh.smartlightalarmclock.view.fragments.accounts.adapter.AccountsRecyclerAdapter;
import com.awelijuh.smartlightalarmclock.view.fragments.accounts.edit.AccountEditViewModel;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AccountsFragment extends Fragment {

    @Inject
    AccountsViewModel accountsViewModel;

    @Inject
    AccountEditViewModel accountEditViewModel;

    @Inject
    AccountsRecyclerAdapter adapter;

    private FragmentAccountsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        accountsViewModel.clear();
        accountsViewModel.loadAccounts();
        binding.accounts.setAdapter(adapter);
        accountsViewModel.accounts.observe(getViewLifecycleOwner(), e -> adapter.update());

        accountsViewModel.editAccount.observe(getViewLifecycleOwner(), e -> {
            if (e == null) {
                return;
            }
            accountEditViewModel.key.setValue(e);
            NavHostFragment.findNavController(AccountsFragment.this)
                    .navigate(R.id.action_accountsFragment_to_accountEditFragment);
        });
    }

}
