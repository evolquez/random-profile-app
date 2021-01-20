package com.example.randomprofile;

import com.example.randomprofile.data.response.ProfilesResponse;
import com.example.randomprofile.data.service.ProfilesService;
import com.example.randomprofile.data.service.RetrofitBuilder;

import org.junit.Test;

import rx.Subscriber;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void hitApi(){
        ProfilesService service = RetrofitBuilder.getInstance().getRetrofit().create(ProfilesService.class);

        service.getProfiles().subscribe(new Subscriber<ProfilesResponse>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onNext(ProfilesResponse profilesResponse) {
                System.out.println("SIZE: "+ profilesResponse.getProfiles().size());
                assertTrue(profilesResponse.getProfiles().size() > 0);
            }
        });
    }
}