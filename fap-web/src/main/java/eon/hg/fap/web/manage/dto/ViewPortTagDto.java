package eon.hg.fap.web.manage.dto;

public class ViewPortTagDto {
	private String northTitle = "";
	private String westTitle = "系统导航";
	private String centerTitle = "业务系统";
	/**
	 * @return the northTitle
	 */
	public String getNorthTitle() {
		return northTitle;
	}
	/**
	 * @param northTitle the northTitle to set
	 */
	public void setNorthTitle(String northTitle) {
		this.northTitle = northTitle;
	}
	/**
	 * @return the westTitle
	 */
	public String getWestTitle() {
		return westTitle;
	}
	/**
	 * @param westTitle the westTitle to set
	 */
	public void setWestTitle(String westTitle) {
		this.westTitle = westTitle;
	}
	/**
	 * @return the centerTitle
	 */
	public String getCenterTitle() {
		return centerTitle;
	}
	/**
	 * @param centerTitle the centerTitle to set
	 */
	public void setCenterTitle(String centerTitle) {
		this.centerTitle = centerTitle;
	}

}
