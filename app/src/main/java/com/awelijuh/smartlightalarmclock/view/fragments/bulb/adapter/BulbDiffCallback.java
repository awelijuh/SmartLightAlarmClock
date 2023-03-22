package com.awelijuh.smartlightalarmclock.view.fragments.bulb.adapter;

import androidx.recyclerview.widget.DiffUtil;

import com.awelijuh.smartlightalarmclock.adapters.database.domain.Bulb;

import java.util.List;
import java.util.Objects;

public class BulbDiffCallback extends DiffUtil.Callback {

    private final List<Bulb> oldList;
    private final List<Bulb> newList;

    public BulbDiffCallback(List<Bulb> oldList, List<Bulb> newList) {
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
