package com.awelijuh.smartlightalarmclock.view.modules;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.awelijuh.smartlightalarmclock.view.fragments.accounts.AccountsViewModel;
import com.awelijuh.smartlightalarmclock.view.fragments.accounts.edit.AccountEditViewModel;
import com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.AlarmViewModel;
import com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.edit.AlarmEditViewModel;
import com.awelijuh.smartlightalarmclock.view.fragments.bulb.BulbViewModel;
import com.awelijuh.smartlightalarmclock.view.fragments.checklight.CheckLightViewModel;
import com.awelijuh.smartlightalarmclock.view.fragments.bulb.edit.BulbEditViewModel;

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
    AlarmEditViewModel provideAlarmCreatorViewModel(@ActivityContext Context context) {
        return createViewModel(context, AlarmEditViewModel.class);
    }

    @Provides
    BulbViewModel provideLightViewModel(@ActivityContext Context context) {
        return createViewModel(context, BulbViewModel.class);
    }

    @Provides
    BulbEditViewModel provideLightCreateViewModel(@ActivityContext Context context) {
        return createViewModel(context, BulbEditViewModel.class);
    }

    @Provides
    AccountsViewModel provideAccountsViewModel(@ActivityContext Context context) {
        return createViewModel(context, AccountsViewModel.class);
    }

    @Provides
    AccountEditViewModel provideAccountEditViewModel(@ActivityContext Context context) {
        return createViewModel(context, AccountEditViewModel.class);
    }

    @Provides
    CheckLightViewModel provideCheckLightViewModel(@ActivityContext Context context) {
        return createViewModel(context, CheckLightViewModel.class);
    }

    private <T extends ViewModel> T createViewModel(Context context, Class<T> tClass) {
        return new ViewModelProvider((ViewModelStoreOwner) context).get(tClass);
    }

}
