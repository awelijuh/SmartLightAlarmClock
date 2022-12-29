package com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.awelijuh.smartlightalarmclock.databinding.ItemAlarmBinding;
import com.awelijuh.smartlightalarmclock.core.domain.AlarmClockItem;
import com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.AlarmViewModel;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.qualifiers.ActivityContext;
import lombok.Setter;


@Module
@InstallIn(FragmentComponent.class)
public class AlarmClockRecyclerAdapter extends RecyclerView.Adapter<AlarmClockRecyclerAdapter.ViewHolder> {

    private final Context context;

    @Setter
    private Consumer<AlarmClockItem> onItemClickListener;

    private List<AlarmClockItem> items;

    @Inject
    AlarmViewModel alarmViewModel;

    @Inject
    AlarmClockRecyclerAdapter(@ActivityContext Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemAlarmBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateList(List<AlarmClockItem> r) {
        if (Objects.equals(items, r)) {
            return;
        }
        items = r;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemAlarmBinding binding;

        public ViewHolder(ItemAlarmBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(AlarmClockItem alarmClockItem) {
            binding.alarmItemActive.setChecked(alarmClockItem.isActive());
            binding.alarmItemTime.setText(alarmClockItem.getTime().toString());
            binding.alarmItemPeriod.setText(alarmClockItem.getPeriodText(context));

            binding.getRoot().setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.accept(alarmClockItem);
                }
            });

            binding.alarmItemActive.setOnCheckedChangeListener((compoundButton, b) -> {
                alarmClockItem.setActive(b);
                alarmViewModel.saveAlarmItem(alarmClockItem);
            });

        }

    }

}
