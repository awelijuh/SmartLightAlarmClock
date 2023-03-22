package com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.awelijuh.smartlightalarmclock.adapters.database.domain.AlarmClock;
import com.awelijuh.smartlightalarmclock.databinding.ItemAlarmBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;
import lombok.NoArgsConstructor;


@NoArgsConstructor(onConstructor = @__(@Inject))
public class AlarmClockRecyclerAdapter extends RecyclerView.Adapter<AlarmClockRecyclerAdapter.ViewHolder> {

    @ActivityContext
    @Inject
    Context context;

    private List<AlarmClockViewDto> items = new ArrayList<>();

    private boolean selectedMode = false;

    private Consumer<AlarmClockViewDto> onItemClick;

    private Consumer<AlarmClockViewDto> onItemActiveChanged;

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

    public void update(List<AlarmClockViewDto> alarms) {
        var callback = new AlarmClockDiffCallback(items, alarms);
        var diffResult = DiffUtil.calculateDiff(callback, false);

        items.clear();
        items.addAll(alarms);
        diffResult.dispatchUpdatesTo(this);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemAlarmBinding binding;

        public ViewHolder(ItemAlarmBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(AlarmClockViewDto alarmClockViewDto) {

            binding.setAlarm(alarmClockViewDto);

            binding.getRoot().setOnClickListener(v -> {
                Optional.ofNullable(onItemClick).ifPresent(e -> e.accept(alarmClockViewDto));
//                alarmViewModel.selectAlarmItem(alarmClockItem);
            });
            binding.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {

//                alarmViewModel.checkAlarm(alarmClockItem, b);
            });

            binding.getRoot().setOnLongClickListener(v -> {
//                alarmViewModel.setSelectedMode(alarmClockItem);
                return true;
            });


            binding.alarmItemActive.setOnCheckedChangeListener((compoundButton, b) -> {
                alarmClockViewDto.setActive(b);
                Optional.ofNullable(onItemActiveChanged).ifPresent(e -> e.accept(alarmClockViewDto));
//                alarmClockItem.setActive(b);
//                alarmViewModel.saveAlarmItem(alarmClockItem);
            });

        }

    }

}
