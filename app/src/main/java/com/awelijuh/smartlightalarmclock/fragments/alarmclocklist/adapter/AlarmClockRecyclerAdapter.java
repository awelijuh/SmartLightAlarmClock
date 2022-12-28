package com.awelijuh.smartlightalarmclock.fragments.alarmclocklist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.awelijuh.smartlightalarmclock.databinding.ItemAlarmBinding;
import com.awelijuh.smartlightalarmclock.models.AlarmClockItem;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.qualifiers.ActivityContext;


@Module
@InstallIn(FragmentComponent.class)
public class AlarmClockRecyclerAdapter extends RecyclerView.Adapter<AlarmClockRecyclerAdapter.ViewHolder> {

    private final Context context;
    private List<AlarmClockItem> items;

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
        items = r;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemAlarmBinding binding;

        public ViewHolder(ItemAlarmBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(AlarmClockItem alarmClockItem) {
//            binding.setContext(context);
//            binding.setAlarm(alarmClockItem);
        }

    }

}
