package com.example.ceylincovip.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ceylincovip.Modal.Policy;

import java.util.List;

public class PolicyArrayAdapter extends ArrayAdapter<Policy> {

    Context context;
    int resource;
    List<Policy> policyList;

    public PolicyArrayAdapter(Context context, int resource, List<Policy> policyList) {
        super(context, resource, policyList);

        this.context = context;
        this.resource = resource;
        this.policyList = policyList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(resource,parent,false);

        return row;
    }
}