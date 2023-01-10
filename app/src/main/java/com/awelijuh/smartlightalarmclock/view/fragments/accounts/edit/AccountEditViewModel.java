package com.awelijuh.smartlightalarmclock.view.fragments.accounts.edit;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.awelijuh.smartlightalarmclock.adapters.memory.CredentialsPreferenceAdapter;
import com.awelijuh.smartlightalarmclock.core.ports.in.LedUseCase;

import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.NoArgsConstructor;

@HiltViewModel
@NoArgsConstructor(onConstructor = @__(@Inject))
public class AccountEditViewModel extends ViewModel {

    public final MutableLiveData<String> key = new MutableLiveData<>(null);

    public final MutableLiveData<Object> credentials = new MutableLiveData<>(null);

    @Inject
    Map<String, LedUseCase> leds;

    @Inject
    CredentialsPreferenceAdapter credentialsPreferenceAdapter;

    public LedUseCase getLed() {
        return leds.get(key.getValue());
    }

    public void loadCredentials() {
        credentials.setValue(credentialsPreferenceAdapter.getCredentials(key.getValue()));
    }

    public void saveCredentials() {
        credentialsPreferenceAdapter.saveCredentials(key.getValue(), credentials.getValue());
    }

    public void setCredentials(Object credentials) {
        this.credentials.setValue(credentials);
    }
}
