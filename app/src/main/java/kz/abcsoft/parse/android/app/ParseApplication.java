package kz.abcsoft.parse.android.app;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "Qk83t0MQ2uYWKXhHTU3wLQroNquFnKldnuVrl6bf", "uYEhkctlJGFxzDxZvj6T01bQEatuAqxQHjm0PfGK");
    }
}
