package com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.adapter;

import androidx.recyclerview.widget.DiffUtil;

import com.awelijuh.smartlightalarmclock.core.domain.AlarmClockItem;

import java.util.List;
import java.util.Objects;

public class AlarmClockDiffCallback extends DiffUtil.Callback {

    private final List<AlarmClockViewDto> oldList;
    private final List<AlarmClockViewDto> newList;

    public AlarmClockDiffCallback(List<AlarmClockViewDto> oldList, List<AlarmClockViewDto> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        if (oldList == null) {
            return 0;
        }
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        if (newList == null) {
            return 0;
        }
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(oldList.get(oldItemPosition).getId(), newList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }
}
