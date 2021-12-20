package com.example.ckltdd.Retrofit2;

public class APIUtils {
    public static final String Base_Url = "https://app-quanlysv.herokuapp.com/api/";

    public static APIServices getAPIService() {
        return RetrofitClient.getClient(Base_Url).create(APIServices.class);
    }
}
