package com.awelijuh.smartlightalarmclock.view.fragments.accounts;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.awelijuh.smartlightalarmclock.core.ports.in.LedUseCase;
import com.awelijuh.smartlightalarmclock.view.fragments.accounts.adapter.AccountItem;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(onConstructor = @__(@Inject))
@HiltViewModel
public class AccountsViewModel extends ViewModel {

    public final MutableLiveData<List<AccountItem>> accounts = new MutableLiveData<>(List.of());

    public final MutableLiveData<String> editAccount = new MutableLiveData<>(null);

    @Inject
    List<LedUseCase> leds;

    public void loadAccounts() {
        accounts.setValue(leds.stream().map(this::mapLedToAccount).collect(Collectors.toList()));
    }

    private AccountItem mapLedToAccount(LedUseCase ledUseCase) {
        AccountItem accountItem = new AccountItem();
        accountItem.setKey(ledUseCase.getKey());
        accountItem.setName(ledUseCase.getName());

        return accountItem;
    }

}
