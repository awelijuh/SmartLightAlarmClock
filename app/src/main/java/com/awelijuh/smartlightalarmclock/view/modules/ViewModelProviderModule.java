package com.awelijuh.smartlightalarmclock.view.modules;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.awelijuh.smartlightalarmclock.view.fragments.accounts.AccountsViewModel;
import com.awelijuh.smartlightalarmclock.view.fragments.accounts.edit.AccountEditViewModel;
import com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.AlarmViewModel;
import com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.creator.AlarmCreatorViewModel;
import com.awelijuh.smartlightalarmclock.view.fragments.light.LightViewModel;
import com.awelijuh.smartlightalarmclock.view.fragments.light.create.LightCreateViewModel;

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

    @Provides
    LightViewModel provideLightViewModel(@ActivityContext Context context) {
        return createViewModel(context, LightViewModel.class);
    }

    @Provides
    LightCreateViewModel provideLightCreateViewModel(@ActivityContext Context context) {
        return createViewModel(context, LightCreateViewModel.class);
    }

    @Provides
    AccountsViewModel provideAccountsViewModel(@ActivityContext Context context) {
        return createViewModel(context, AccountsViewModel.class);
    }

    @Provides
    AccountEditViewModel provideAccountEditViewModel(@ActivityContext Context context) {
        return createViewModel(context, AccountEditViewModel.class);
    }

    private <T extends ViewModel> T createViewModel(Context context, Class<T> tClass) {
        return new ViewModelProvider((ViewModelStoreOwner) context).get(tClass);
    }

}
