package com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.awelijuh.smartlightalarmclock.core.domain.AlarmClockItem;
import com.awelijuh.smartlightalarmclock.databinding.ItemAlarmBinding;
import com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.AlarmViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.qualifiers.ActivityContext;


@Module
@InstallIn(FragmentComponent.class)
public class AlarmClockRecyclerAdapter extends RecyclerView.Adapter<AlarmClockRecyclerAdapter.ViewHolder> {

    private final Context context;

    @Inject
    AlarmViewModel alarmViewModel;

    @Inject
    AlarmClockMapper alarmClockMapper;

    private List<AlarmClockViewDto> items = new ArrayList<>();

    private Map<String, AlarmClockItem> idToAlarmMap = new HashMap<>();

    private boolean selectedMode = false;

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

    public void update() {
        var alarms = alarmClockMapper.map(alarmViewModel.alarms.getValue());
        var callback = new AlarmClockDiffCallback(items, alarms);
        var diffResult = DiffUtil.calculateDiff(callback, false);

        items.clear();
        items.addAll(alarms);
        idToAlarmMap = alarmViewModel.alarms.getValue().stream().collect(Collectors.toMap(AlarmClockItem::getId, Function.identity()));
        diffResult.dispatchUpdatesTo(this);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemAlarmBinding binding;

        public ViewHolder(ItemAlarmBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(AlarmClockViewDto alarmClockViewDto) {
            var alarmClockItem = idToAlarmMap.get(alarmClockViewDto.getId());

            binding.setAlarm(alarmClockViewDto);

            binding.getRoot().setOnClickListener(v -> {
                alarmViewModel.selectAlarmItem(alarmClockItem);
            });
            binding.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
                alarmViewModel.checkAlarm(alarmClockItem, b);
            });

            binding.getRoot().setOnLongClickListener(v -> {
                alarmViewModel.setSelectedMode(alarmClockItem);
                return true;
            });

            binding.alarmItemActive.setOnCheckedChangeListener((compoundButton, b) -> {
                alarmClockItem.setActive(b);
                alarmViewModel.saveAlarmItem(alarmClockItem);
            });

        }

    }

}
