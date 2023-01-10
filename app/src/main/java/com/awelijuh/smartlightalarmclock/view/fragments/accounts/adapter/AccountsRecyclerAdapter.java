package com.awelijuh.smartlightalarmclock.view.fragments.accounts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.awelijuh.smartlightalarmclock.databinding.ItemAccountBinding;
import com.awelijuh.smartlightalarmclock.view.fragments.accounts.AccountsViewModel;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;
import lombok.NoArgsConstructor;

@NoArgsConstructor(onConstructor = @__(@Inject))
public class AccountsRecyclerAdapter extends RecyclerView.Adapter<AccountsRecyclerAdapter.ViewHolder> {

    @Inject
    @ActivityContext
    Context context;

    @Inject
    AccountsViewModel accountsViewModel;

    private List<AccountItem> items;

    public void update() {
        var accounts = accountsViewModel.accounts.getValue();
        if (!Objects.equals(accounts, items)) {
            items = accounts;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemAccountBinding.inflate(LayoutInflater.from(context)));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemAccountBinding binding;

        ViewHolder(ItemAccountBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(AccountItem account) {
            binding.setAccount(account);
            binding.getRoot().setOnClickListener(v -> {
                accountsViewModel.editAccount.setValue(account.getKey());
            });
        }
    }
}
