package com.github.xwanlion.lifeauctioneer.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.util.AndroidPermissionHelper;
import com.github.xwanlion.lifeauctioneer.util.Logger;

import java.util.List;

public class AuctionsListFragment<onRequestPermissionsResult> extends Fragment {

    private RecyclerView recyclerView;
    private AuctionsListAdapter activityListAdapter;
    private AuctionsListViewModel activityListViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.auctions_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // require permission, if not, can not query from database.
        AndroidPermissionHelper.requireExternalStoragePermissionIfNeeded(this.getActivity());

        recyclerView =  view.findViewById(R.id.rcyv_activity_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        activityListAdapter = new AuctionsListAdapter(AuctionsListFragment.this);
        recyclerView.setAdapter(activityListAdapter);
        this.activityListViewModel = ViewModelProviders.of(this).get(AuctionsListViewModel.class);
        activityListViewModel.listData().observe(getViewLifecycleOwner(), new Observer<List<RecyclerViewBaseMultiData>>() {
            @Override
            public void onChanged(List<RecyclerViewBaseMultiData> list) {
                activityListAdapter.setDataList(list);
                activityListAdapter.notifyDataSetChanged();
            }
        });

        view.findViewById(R.id.lyo_activity_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityListAdapter.invisibleMenu();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.txt_activity_list_fragment_title);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (activityListAdapter == null) return;

        activityListAdapter.onDestroy();
        activityListAdapter = null;
        activityListViewModel = null;
        recyclerView = null;
    }
}
