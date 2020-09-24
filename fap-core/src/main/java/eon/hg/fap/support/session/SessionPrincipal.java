package eon.hg.fap.support.session;

import eon.hg.fap.core.domain.entity.IdEntity;
import eon.hg.fap.db.model.virtual.OnlineUser;

public interface SessionPrincipal {

    public OnlineUser getOnlineUser();

    public IdEntity getCurrentUser();

}
