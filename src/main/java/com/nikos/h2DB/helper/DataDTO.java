package com.nikos.h2DB.helper;

public class DataDTO {

	private String msisdn;
	private String shortCode;
	private String text;

	public DataDTO() {

	}

	public DataDTO(String msisdn, String shortCode, String text) {
		setMsisdn(msisdn);
		setShortCode(shortCode);
		setText(text);
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
