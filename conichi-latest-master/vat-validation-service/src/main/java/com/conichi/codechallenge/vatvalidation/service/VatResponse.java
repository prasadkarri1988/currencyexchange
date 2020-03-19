package com.conichi.codechallenge.vatvalidation.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties({"businessName","businessAddress","valid"})
public class VatResponse {
	
	private String countryCode;
	
    private int vatNumber;
    
    private boolean isValid;
    
    private String  businessName;
    
    private String businessAddress;
    
    public VatResponse() {
	}
    
	public VatResponse(String countryCode, int vatNumber, boolean isValid, String businessName,
			String businessAddress) {
		super();
		this.countryCode = countryCode;
		this.vatNumber = vatNumber;
		this.isValid = isValid;
		this.businessName = businessName;
		this.businessAddress = businessAddress;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public int getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(int vatNumber) {
		this.vatNumber = vatNumber;
	}

	public boolean getValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	@Override
	public String toString() {
		return "VatResponse [countryCode=" + countryCode + ", vatNumber=" + vatNumber + ", isValid=" + isValid
				+ ", businessName=" + businessName + ", businessAddress=" + businessAddress + "]";
	}
	
    
}
