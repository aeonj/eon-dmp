package eon.hg.fap.security.session;

import eon.hg.fap.common.properties.PropertiesBean;
import eon.hg.fap.core.cache.RedisPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

public class SessionUserCache implements UserCache {

    @Autowired
    private PropertiesBean propertiesBean;
    @Autowired(required = false)
    private RedisPool redisPool;

    /**
     * Obtains a {@link UserDetails} from the cache.
     *
     * @param username the {@link User#getUsername()} used to place the user in the cache
     * @return the populated <code>UserDetails</code> or <code>null</code> if the user
     * could not be found or if the cache entry has expired
     */
    @Override
    public UserDetails getUserFromCache(String username) {
        if (redisPool!=null) {
            return redisPool.hget("eon.session.user",username);
        }
        return null;
    }

    /**
     * Places a {@link UserDetails} in the cache. The <code>username</code> is the key
     * used to subsequently retrieve the <code>UserDetails</code>.
     *
     * @param user the fully populated <code>UserDetails</code> to place in the cache
     */
    @Override
    public void putUserInCache(UserDetails user) {
        if (redisPool!=null) {
            redisPool.hset("eon.session.user",user.getUsername(),user,3600);
        }
    }

    /**
     * Removes the specified user from the cache. The <code>username</code> is the key
     * used to remove the user. If the user is not found, the method should simply return
     * (not thrown an exception).
     * <p>
     * Some cache implementations may not support eviction from the cache, in which case
     * they should provide appropriate behaviour to alter the user in either its
     * documentation, via an exception, or through a log message.
     *
     * @param username to be evicted from the cache
     */
    @Override
    public void removeUserFromCache(String username) {
        if (redisPool!=null) {
            redisPool.del("eon.session.user");
        }
    }
}
