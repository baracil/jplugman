package jplugman.tools.impl;

import jplugman.tools.Subscription;
import lombok.NonNull;

public enum IdentitySubscription implements Subscription {
    INSTANCE;


    @Override
    public void unsubscribe() {}

    @Override
    public @NonNull Subscription andThen(@NonNull Subscription after) {
        return after;
    }


}
