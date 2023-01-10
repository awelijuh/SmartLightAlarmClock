package com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.creator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import com.awelijuh.smartlightalarmclock.R;
import com.awelijuh.smartlightalarmclock.view.utils.AlarmUtils;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CustomChoiceReplayDialog extends DialogFragment {

    @Inject
    AlarmCreatorViewModel alarmCreatorViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        builder.setTitle(R.string.replay);

        CharSequence[] days = getResources().getStringArray(R.array.weekdays_full);
        Set<DayOfWeek> period = alarmCreatorViewModel.period.getValue();
        boolean[] selected = AlarmUtils.toPrimitiveArray(Arrays.stream(DayOfWeek.values()).map(e -> period != null && period.contains(e)).collect(Collectors.toList()));

        builder.setMultiChoiceItems(days, selected, (dialogInterface, i, b) -> selected[i] = b);

        builder.setPositiveButton(R.string.ok_text, (dialogInterface, i) -> {
            List<DayOfWeek> week = Arrays.stream(DayOfWeek.values()).collect(Collectors.toList());
            alarmCreatorViewModel.period.setValue(week.stream().filter(e -> selected[week.indexOf(e)]).collect(Collectors.toSet()));
            dialogInterface.dismiss();
        });

        builder.setNegativeButton(R.string.cancel_text, (dialogInterface, i) -> dialogInterface.dismiss());


        return builder.create();
    }
}
