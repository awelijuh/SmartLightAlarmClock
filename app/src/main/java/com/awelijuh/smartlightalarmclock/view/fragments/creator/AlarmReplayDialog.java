package com.awelijuh.smartlightalarmclock.view.fragments.creator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import com.awelijuh.smartlightalarmclock.R;
import com.awelijuh.smartlightalarmclock.view.utils.AlarmUtils;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AlarmReplayDialog extends DialogFragment {

    private final List<Integer> replays = List.of(
            R.string.once_period,
            R.string.every_day_period,
            R.string.weekdays_period,
            R.string.custom_period
    );

    private final Map<Integer, Set<DayOfWeek>> periodMap = Map.of(
            R.string.once_period, Set.of(),
            R.string.every_day_period, Set.of(DayOfWeek.values()),
            R.string.weekdays_period, AlarmUtils.WEEKDAYS
    );

    @Inject
    AlarmCreatorViewModel alarmCreatorViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var builder = new AlertDialog.Builder(requireActivity());
        CharSequence[] replayTexts = replays.stream().map(e -> requireContext().getString(e)).collect(Collectors.toList()).toArray(new CharSequence[replays.size()]);

        Integer selected = periodMap.entrySet().stream()
                .filter(e -> (e.getValue().isEmpty() && alarmCreatorViewModel.period.getValue() == null)
                        || e.getValue().equals(alarmCreatorViewModel.period.getValue()))
                .map(Map.Entry::getKey).findAny().orElse(R.string.custom_period);

        builder.setSingleChoiceItems(replayTexts, replays.indexOf(selected), (dialogInterface, i) -> {
            if (replays.get(i).equals(R.string.custom_period)) {
                CustomChoiceReplayDialog customChoiceReplayDialog = new CustomChoiceReplayDialog();
                customChoiceReplayDialog.show(getParentFragmentManager(), "customChoiceReplayDialog");
            } else {
                alarmCreatorViewModel.period.setValue(periodMap.get(replays.get(i)));
            }
            dialogInterface.dismiss();
        });


        return builder.create();
    }
}
