package com.cg.walletapplicationphase2.service;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.cg.walletapplicationphase2.bean.Customer;
import com.cg.walletapplicationphase2.exception.WalletException;


public interface IWalletService {

	/*******************************************************************************************************
	 - Interface Name	:	<IWalletService>
	 - Input Parameters	:	<null>
	 - Return Type		:	<null>
	 - Throws			:  	<null>
	 - Author			:	<Neti_Venkata_Praveena>
	 - Creation Date	:	31/07/2018
	 - Description		:	methods declaration
	 ********************************************************************************************************/ 
	

	public String addCustomer(Customer customer) throws WalletException;

	public Customer showBalance(String mobileNum, String password) throws WalletException, SQLException;

	public Customer check(String mobileNum, String password) throws WalletException, SQLException;

	public void deposit(Customer customer, BigDecimal amount) throws ClassNotFoundException, SQLException, WalletException;

	public boolean withDraw(Customer customer, BigDecimal amount) throws WalletException, ClassNotFoundException, SQLException;

	public boolean isFound(String receiverMobile) throws WalletException, SQLException;

	public Customer transfer(String senderMobile, String receiverMobile, BigDecimal amount) throws InterruptedException, WalletException, ClassNotFoundException, SQLException;

	public String printTransactions(Customer customer) throws ClassNotFoundException, SQLException;

	public void checkName(String name) throws WalletException;

	public void checkMobileNumber(String mobileNumber) throws WalletException;

	public void checkPassword(String password) throws WalletException;

	public void checkEmail(String emailId) throws WalletException;

}
