package com.theh.moduleuser.Config.Security;

import org.springframework.cglib.core.internal.LoadingCache;

public class LoginAttemptService {
    private final int MAX_ATTEMPT = 10;
    private LoadingCache<String, Integer,Integer> attemptsCache;

    public LoginAttemptService() {
        super();
    }

    //

    public void loginSucceeded(final String key) {

    }

    public void loginFailed(final String key) {

    }

    public boolean isBlocked(final String key) {
        return false;
    }
}
