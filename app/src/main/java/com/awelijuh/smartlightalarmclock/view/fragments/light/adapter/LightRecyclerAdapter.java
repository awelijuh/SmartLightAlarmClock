package com.awelijuh.smartlightalarmclock.view.fragments.light.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.awelijuh.smartlightalarmclock.core.domain.Light;
import com.awelijuh.smartlightalarmclock.databinding.ItemLightBinding;
import com.awelijuh.smartlightalarmclock.view.fragments.light.LightViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;
import lombok.NoArgsConstructor;

@NoArgsConstructor(onConstructor = @__(@Inject))
public class LightRecyclerAdapter extends RecyclerView.Adapter<LightRecyclerAdapter.ViewHolder> {

    @Inject
    @ActivityContext
    Context context;

    @Inject
    LightViewModel lightViewModel;

    private List<Light> items = new ArrayList<>();

    private Map<String, Light> idToAlarmMap = new HashMap<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemLightBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void update() {
        var lights = lightViewModel.lights.getValue();
        var callback = new LightDiffCallback(items, lights);
        var diffResult = DiffUtil.calculateDiff(callback, false);

        items.clear();
        items.addAll(lights);
        diffResult.dispatchUpdatesTo(this);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemLightBinding binding;

        public ViewHolder(ItemLightBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Light light) {
            binding.setLight(light);
        }

    }

}
