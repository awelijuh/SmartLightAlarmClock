package com.awelijuh.smartlightalarmclock.providers;

import android.content.Context;

import com.awelijuh.smartlightalarmclock.fragments.alarmclocklist.AlarmViewModel;
import com.awelijuh.smartlightalarmclock.fragments.creator.AlarmCreatorViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.qualifiers.ActivityContext;

@Module
@InstallIn(ActivityComponent.class)
public class ViewModelProviderModule {

    @Provides
    AlarmViewModel provideAlarmViewModel(@ActivityContext Context context) {
        return createViewModel(context, AlarmViewModel.class);
    }

    @Provides
    AlarmCreatorViewModel provideAlarmCreatorViewModel(@ActivityContext Context context) {
        return createViewModel(context, AlarmCreatorViewModel.class);
    }

    private <T extends ViewModel> T createViewModel(Context context, Class<T> tClass) {
        return new ViewModelProvider((ViewModelStoreOwner) context).get(tClass);
    }

}
