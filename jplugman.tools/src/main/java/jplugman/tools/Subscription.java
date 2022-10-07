package jplugman.tools;

import jplugman.tools.impl.IdentitySubscription;
import lombok.NonNull;

public interface Subscription {

    void unsubscribe();


    default @NonNull Subscription andThen(@NonNull Subscription after) {
        return () -> {
            this.unsubscribe();
            after.unsubscribe();
        };
    }

    static Subscription identity() {
        return IdentitySubscription.INSTANCE;
    }
}
