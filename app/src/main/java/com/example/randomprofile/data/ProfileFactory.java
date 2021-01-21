package com.example.randomprofile.data;

import com.example.randomprofile.data.response.LocationResponse;
import com.example.randomprofile.data.response.ProfilesResponse;
import com.example.randomprofile.entity.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProfileFactory {

    private static ProfileFactory instance;

    private ProfileFactory(){}

    public static ProfileFactory getInstance(){
        if(instance == null)
            instance = new ProfileFactory();

        return instance;
    }

    public List<Profile> prepareFromResponse(ProfilesResponse response){

        List<Profile> profiles = new ArrayList<>();

        response.getProfileResponses().forEach(rs ->
                profiles.add(new Profile(rs.getId().getValue(), rs.getName().getFirst(),
                                rs.getName().getLast(), rs.getEmail(), rs.getDobResponse().getAge(),
                                rs.getPhone(), buildAddress(rs.getLocationResponse()),
                                rs.getPictureResponse().getThumbnail(), rs.getPictureResponse().getLarge())));

        return profiles;
    }

    private String buildAddress(LocationResponse locationResponse){
        final String addressFormat = "%s %d, %s, %s, %s";

        return String.format(Locale.US, addressFormat, locationResponse.getStreetResponse().getName(),
                locationResponse.getStreetResponse().getNumber(), locationResponse.getCity(),
                locationResponse.getState(), locationResponse.getCountry());
    }
}