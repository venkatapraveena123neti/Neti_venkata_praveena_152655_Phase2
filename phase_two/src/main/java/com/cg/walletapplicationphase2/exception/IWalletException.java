package com.cg.walletapplicationphase2.exception;

public interface IWalletException {
	/*******************************************************************************************************
	 - Interface Name	:	<IWalletException>
	 - Input Parameters	:	<null>
	 - Return Type		:	<null>
	 - Throws			:  	<null>
	 - Author			:	<Neti_Venkata_Praveena>
	 - Creation Date	:	31/07/2018
	 - Description		:	error messages description
	 ********************************************************************************************************/ 
	
	String nameError ="Enter Valid Name";
	String mobileNumError = "Enter Valid Mobile Number ";
	String passwordError ="Enter Valid Password";
	String emailError = " enter valid email id";
	String invalidDetails = "Given details are incorrect ";
	String insufficientFunds = "Insufficicnet account balance";
	String accountExistingError = "Account already exists with the given mobile number";
	String sqlException = "Cannot connect to database";
	String mobileNumberNotExists = "Account doesnt exists with given mobile number";
	String transactionfailError = "Network issue! Please try again";
}
