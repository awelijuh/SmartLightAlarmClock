package com.awelijuh.smartlightalarmclock.view.fragments.accounts.edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.awelijuh.schemagenerator.GeneratedUI;
import com.awelijuh.smartlightalarmclock.R;
import com.awelijuh.smartlightalarmclock.databinding.FragmentEditAccountBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AccountEditFragment extends Fragment {

    @Inject
    AccountEditViewModel accountEditViewModel;

    private FragmentEditAccountBinding binding;

    private GeneratedUI generatedUI;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.generatedUI = new GeneratedUI(requireContext(), binding.account);
        accountEditViewModel.credentials.observe(getViewLifecycleOwner(), v -> generatedUI.setValue(v));

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();
                menu.add("ok").setIcon(R.drawable.ic_baseline_check_24).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getTitle() != null && "ok".contentEquals(menuItem.getTitle())) {
                    accountEditViewModel.setCredentials(generatedUI.getResult(accountEditViewModel.getLed().getCredentialsClass()));
                    accountEditViewModel.saveCredentials();
                    NavHostFragment.findNavController(AccountEditFragment.this)
                            .navigateUp();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner());
    }
}
