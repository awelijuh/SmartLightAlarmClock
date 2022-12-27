package com.awelijuh.smartlightalarmclock.fragments.alarm.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.awelijuh.smartlightalarmclock.databinding.ItemAlarmBinding;
import com.awelijuh.smartlightalarmclock.fragments.alarm.AlarmViewModel;
import com.awelijuh.smartlightalarmclock.models.AlarmClockItem;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dagger.Module;


@Module
public class AlarmClockListAdapter extends RecyclerView.Adapter<AlarmClockListAdapter.ViewHolder> {

    @Inject
    private AlarmViewModel alarmViewModel;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemAlarmBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(alarmViewModel.alarms.getValue().get(position));
    }

    @Override
    public int getItemCount() {
        return alarmViewModel.alarms.getValue().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemAlarmBinding binding;

        public ViewHolder(ItemAlarmBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(AlarmClockItem alarmClockItem) {

        }

    }

}
