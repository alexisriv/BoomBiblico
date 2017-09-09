package com.sixelasavir.www.boombiblico;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sixelasavir.www.boombiblico.greendao.model.GamerRecord;

import java.util.ArrayList;
import java.util.List;

public class ListRecordFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<GamerRecord> gamerRecords = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public ListRecordFragment() {}

    public static ListRecordFragment newInstance(List<GamerRecord> gamerRecords) {
        ListRecordFragment fragment = new ListRecordFragment();
        fragment.setGamerRecords(gamerRecords);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_record, container, false);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.list_record_recycler_view);
        this.recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AdapterRecord(view.getContext(), gamerRecords);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public List<GamerRecord> getGamerRecords() {
        return gamerRecords;
    }

    public void setGamerRecords(List<GamerRecord> gamerRecords) {
        this.gamerRecords = gamerRecords;
    }

    public interface OnFragmentInteractionListener {

    }
}
