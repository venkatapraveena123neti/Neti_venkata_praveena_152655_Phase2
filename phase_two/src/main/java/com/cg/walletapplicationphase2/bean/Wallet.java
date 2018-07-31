package com.cg.walletapplicationphase2.bean;

import java.io.Serializable;
import java.math.BigDecimal;



public class Wallet implements Serializable{
	/*******************************************************************************************************
	 - Class Name	    :	<Wallet>
	 - Input Parameters	:	<null>
	 - Return Type		:	<database details>
	 - Throws			:  	<null>
	 - Author			:	<Neti_Venkata_Praveena>
	 - Creation Date	:	31/07/2018
	 - Description		:	details to be stored into database if connected
	 ********************************************************************************************************/ 
	private static final long serialVersionUID = 1L;

	private BigDecimal balance;
	
	public Wallet() {
		this.balance=BigDecimal.valueOf(0.0);
	}

	
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
