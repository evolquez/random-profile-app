package com.example.randomprofile.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.randomprofile.R;
import com.example.randomprofile.entity.Profile;
import com.example.randomprofile.view.holder.SuggestionViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SuggestionsAdapter extends ArrayAdapter<Profile> {

    private List<Profile> profiles;
    private final LayoutInflater inflater;
    private ArrayList<Profile> suggestions;
    private final ProfilesAdapter.OnProfileClickHandler onProfileClickHandler;

    public SuggestionsAdapter(Context context, List<Profile> profiles, ProfilesAdapter.OnProfileClickHandler onProfileClickHandler) {

        super(context, 0, profiles);

        inflater        = LayoutInflater.from(context);
        this.profiles   = profiles;
        suggestions     = new ArrayList<>();

        this.onProfileClickHandler = onProfileClickHandler;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        SuggestionViewHolder vh;

        if(convertView == null){

            convertView = inflater.inflate(R.layout.suggestion, parent, false);

            Profile profile = suggestions.get(position);

            if(profile != null){
                vh = new SuggestionViewHolder(convertView, this.onProfileClickHandler);

                Picasso.get().load(profile.getThumbnailImage()).into(vh.getProfileImage());
                vh.getProfileName().setText(getFullName(profile));
                vh.getProfileDni().setText(profile.getId());

                vh.bind(profile);
            }
        }

        return convertView;
    }

    private String getFullName(Profile profile){
        return String.format(Locale.US, "%s %s", profile.getFirstName(), profile.getLastName());
    }

    @Override
    public Filter getFilter() {
        return profileFilter;
    }

    Filter profileFilter = new Filter() {
        public String convertResultToString(Object resultValue) {
            Profile rsProfile = (Profile) resultValue;

            return getFullName(rsProfile);
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for(Profile p : profiles){
                    if (getFullName(p).toLowerCase().contains(constraint.toString().toLowerCase())) {
                        if(!suggestions.contains(p))
                            suggestions.add(p);
                    }
                };

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            List<Profile> filteredList = (List<Profile>) results.values;

            if (results != null && results.count > 0) {

                clear();

                for (Profile p : filteredList)
                    add(p);

                notifyDataSetChanged();
            }
        }
    };
}