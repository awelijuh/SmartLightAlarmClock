package com.awelijuh.smartlightalarmclock.view.fragments.bulb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.awelijuh.smartlightalarmclock.adapters.database.domain.Bulb;
import com.awelijuh.smartlightalarmclock.databinding.ItemBulbBinding;
import com.awelijuh.smartlightalarmclock.view.fragments.bulb.BulbViewModel;

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
    BulbViewModel bulbViewModel;

    private List<Bulb> items = new ArrayList<>();

    private Map<String, Bulb> idToAlarmMap = new HashMap<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemBulbBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void update(List<Bulb> bulbs) {
        var callback = new BulbDiffCallback(items, bulbs);
        var diffResult = DiffUtil.calculateDiff(callback, false);

        items.clear();
        items.addAll(bulbs);
        diffResult.dispatchUpdatesTo(this);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemBulbBinding binding;

        public ViewHolder(ItemBulbBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Bulb bulb) {
            binding.setBulb(bulb);

            binding.getRoot().setOnClickListener(v -> {
                bulbViewModel.selectedBulb.setValue(bulb);
            });

        }

    }

}
