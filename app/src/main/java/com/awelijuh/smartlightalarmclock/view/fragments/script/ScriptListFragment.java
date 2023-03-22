package com.awelijuh.smartlightalarmclock.view.fragments.script;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.awelijuh.smartlightalarmclock.databinding.FragmentScriptsListBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ScriptListFragment extends Fragment {

    private FragmentScriptsListBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentScriptsListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

}
