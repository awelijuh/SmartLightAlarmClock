package com.awelijuh.smartlightalarmclock.view.fragments.light.adapter;

import androidx.recyclerview.widget.DiffUtil;

import com.awelijuh.smartlightalarmclock.core.domain.Light;

import java.util.List;
import java.util.Objects;

public class LightDiffCallback extends DiffUtil.Callback {

    private final List<Light> oldList;
    private final List<Light> newList;

    public LightDiffCallback(List<Light> oldList, List<Light> newList) {
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
