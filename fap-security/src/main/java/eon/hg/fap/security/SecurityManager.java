package eon.hg.fap.security;

/**
 * 权限管理接口
 * @author AEON
 *
 */
public interface SecurityManager {

	public void loadUrlAuthorities();
	
	public boolean isResetAuthorities();
	
	public void setResetAuthorities(boolean reset_flag);
	
}
