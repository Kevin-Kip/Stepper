package com.example.stepper;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.stepper.models.PlaceApi;

import java.util.ArrayList;

public class PlacesuggestionsAdapter extends ArrayAdapter implements Filterable {
    ArrayList<String> results;
    Context context;
    PlaceApi placeApi = new PlaceApi();
    int resource;

    public PlacesuggestionsAdapter(Context ctx, int resId) {
        super(ctx, resId);
        this.context = ctx;
        this.resource = resId;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    results = placeApi.autoComplete(constraint.toString(), context.getString(R.string.google_maps_key));

                    filterResults.values = results;
                    filterResults.count = results.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults r) {
                if (constraint != null && r.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }
}
