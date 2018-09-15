package com.example.aleksandrmykhailenko.androidbindings.bindings;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.example.aleksandrmykhailenko.androidbindings.ui.split.ApplicationItem;
import com.example.aleksandrmykhailenko.androidbindings.ui.split.OnApplicationItemSelectionChangedListener;
import com.example.aleksandrmykhailenko.androidbindings.ui.split.SplitTunnelingRecyclerViewAdapter;

import java.util.List;

public class RecyclerViewItemsBindingAdapter {

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:apps")
    public static void setApplicationsList(RecyclerView recyclerView, List<ApplicationItem> apps) {
        SplitTunnelingRecyclerViewAdapter adapter = (SplitTunnelingRecyclerViewAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setApplicationsInfoList(apps);
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:not_allowed_apps")
    public static void setNotAllowedAppsList(RecyclerView recyclerView, List<String> notAllowedApps) {
        SplitTunnelingRecyclerViewAdapter adapter = (SplitTunnelingRecyclerViewAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setDisallowedApps(notAllowedApps);
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:selection_listener")
    public static void setSelectionChangedListener(RecyclerView recyclerView,
                                                   OnApplicationItemSelectionChangedListener listener) {
        SplitTunnelingRecyclerViewAdapter adapter = (SplitTunnelingRecyclerViewAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setSelectionChangedListener(listener);
        }
    }
}