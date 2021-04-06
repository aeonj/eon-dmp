package eon.hg.fap.support.session;

import org.springframework.core.NamedThreadLocal;

public class UserContextHolder {
    private static final NamedThreadLocal<UserContext> userContext =  new NamedThreadLocal<>("Current User Info");

    public static void setUserContext(UserContext uc) {
        if (uc==null) {
            remove();
        } else {
            userContext.set(uc);
        }
    }

    public static UserContext getUserContext() {
        return userContext.get();
    }

    public static void remove() {
        userContext.remove();
    }

}
