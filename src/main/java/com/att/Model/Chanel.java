package com.att.Model;

public class Chanel {

	private String chanel;
	private String gpId;
	private  String dateOfBrowserSdk;
	private String sPlayerId;
	public String getChanel() {
		return chanel;
	}
	public void setChanel(String chanel) {
		this.chanel = chanel;
	}
	public String getGpId() {
		return gpId;
	}
	public void setGpId(String gpId) {
		this.gpId = gpId;
	}
	public String getDateOfBrowserSdk() {
		return dateOfBrowserSdk;
	}
	public void setDateOfBrowserSdk(String dateOfBrowserSdk) {
		this.dateOfBrowserSdk = dateOfBrowserSdk;
	}
	public String getsPlayerId() {
		return sPlayerId;
	}
	public void setsPlayerId(String sPlayerId) {
		this.sPlayerId = sPlayerId;
	}
	@Override
	public String toString() {
		return "Chanel [chanel=" + chanel + ", gpId=" + gpId + ", dateOfBrowserSdk=" + dateOfBrowserSdk + ", sPlayerId="
				+ sPlayerId + "]";
	}
	
	
}
