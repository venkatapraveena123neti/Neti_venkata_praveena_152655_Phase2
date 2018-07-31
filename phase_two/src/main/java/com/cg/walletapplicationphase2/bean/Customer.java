package com.cg.walletapplicationphase2.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;





@Entity

@Table(name="Customer_Wallet")
public class Customer implements Serializable {
	/**
	 * 
	 */
	/*******************************************************************************************************
	 - Class Name	    :	<Customer>
	 - Input Parameters	:	<null>
	 - Return Type		:	<database details>
	 - Throws			:  	<null>
	 - Author			:	<Neti_Venkata_Praveena>
	 - Creation Date	:	31/07/2018
	 - Description		:	details to be stored into database if connected
	 ********************************************************************************************************/ 
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="Mobile_No",length=10)
	private String mobileNumber;
	@Column(name="Customer_Name",length=10)
	private String name;
	@Column(name="Customer_Password",length=10)
	private String password;
	@Column(name="Email_ID",length=10)
	private String EmailId;
	@Embedded
	private Wallet wallet ;
	
	public Customer() {
		wallet = new Wallet();
		
	}
	



	public Customer(String mobileNumber, String name, String password, String emailId, Wallet wallet) {
		super();
		this.mobileNumber = mobileNumber;
		this.name = name;
		this.password = password;
		EmailId = emailId;
		this.wallet = wallet;
		
	}




	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getEmailId() {
		return EmailId;
	}

	public void setEmailId(String emailId) {
		EmailId = emailId;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}


	

	
	
	



}



