package com.cywl.launcher.mymvpproject.model.tools;

import com.cywl.launcher.mymvpproject.model.bean.User;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("users/{username}")
    Observable<User> getUser(@Path("username") String username);

}
