package com.example.aleksandrmykhailenko.androidbindings.ui.split;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.aleksandrmykhailenko.androidbindings.R;
import com.example.aleksandrmykhailenko.androidbindings.databinding.ApplicationItemBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SplitTunnelingRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int APP_ITEM = 0;
    private static final int DESCRIPTION_ITEM = 1;

    private List<ApplicationItem> allApps = new LinkedList<>();
    private Set<String> disallowedApps = new HashSet<>();
    private OnApplicationItemSelectionChangedListener listener;

    SplitTunnelingRecyclerViewAdapter() {
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return DESCRIPTION_ITEM;
        }
        return APP_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case APP_ITEM: {
                ApplicationItemBinding binding = ApplicationItemBinding.inflate(layoutInflater, parent, false);
                return new ApplicationInfoViewHolder(binding);
            }
            default: {
                return new DescriptionViewHolder(layoutInflater.inflate(R.layout.description_item, parent, false));
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ApplicationInfoViewHolder) {
            ((ApplicationInfoViewHolder) holder).bind(allApps.get(position - 1));
        }
    }

    @Override
    public int getItemCount() {
        return allApps.size() + 1;
    }

    public void setSelectionChangedListener(OnApplicationItemSelectionChangedListener listener) {
        this.listener = listener;
    }

    public void setApplicationsInfoList(List<ApplicationItem> infoList) {
        this.allApps = new ArrayList<>(infoList);
        Collections.sort(allApps, ApplicationItem.comparator);
        notifyDataSetChanged();
    }

    public void setDisallowedApps(List<String> disallowedApps) {
        this.disallowedApps = new HashSet<>(disallowedApps);
    }

    class ApplicationInfoViewHolder extends RecyclerView.ViewHolder
            implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

        private ApplicationItemBinding binding;
        private ApplicationItem applicationItem;

        ApplicationInfoViewHolder(ApplicationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ApplicationItem applicationItem) {
            this.applicationItem = applicationItem;
            binding.setApplication(applicationItem);
            boolean isChecked = !disallowedApps.contains(applicationItem.getPackageName());
            binding.checkbox.setChecked(isChecked);
            binding.checkbox.setOnCheckedChangeListener(this);
            binding.contentLayout.setOnClickListener(this);
            binding.executePendingBindings();
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isSelected) {
            if (isSelected) {
                disallowedApps.remove(applicationItem.getPackageName());
            } else {
                disallowedApps.add(applicationItem.getPackageName());
            }
            listener.onApplicationItemSelectionChanged(applicationItem, isSelected);
        }

        @Override
        public void onClick(View view) {
            boolean isNotAllowed = disallowedApps.contains(applicationItem.getPackageName());
            binding.checkbox.setChecked(isNotAllowed);
        }
    }

    class DescriptionViewHolder extends RecyclerView.ViewHolder {

        DescriptionViewHolder(View itemView) {
            super(itemView);
        }
    }
}