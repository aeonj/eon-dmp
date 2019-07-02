package eon.hg.fap.core.security;

import eon.hg.fap.db.model.primary.User;

public interface SessionPrincipal {
    String getRgCode();
    User getCurrentUser();
}
