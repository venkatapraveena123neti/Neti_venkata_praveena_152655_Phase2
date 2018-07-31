package com.cg.walletapplicationphase2.repo;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.cg.walletapplicationphase2.bean.Customer;
import com.cg.walletapplicationphase2.exception.WalletException;


public interface IWalletRepo {
	/*******************************************************************************************************
	 - Interface Name	:	<IWalletRepo>
	 - Input Parameters	:	<null>
	 - Return Type		:	<null>
	 - Throws			:  	<null>
	 - Author			:	<Neti_Venkata_Praveena>
	 - Creation Date	:	31/07/2018
	 - Description		:	methods declaration
	 ********************************************************************************************************/ 
    public boolean withdraw(Customer customer, BigDecimal amount) throws ClassNotFoundException, SQLException, WalletException;

	public boolean customerExists(String receiverMobile) throws SQLException;

	public Customer transfer(String senderMobile, String receiverMobile, BigDecimal amount) throws ClassNotFoundException, SQLException, WalletException;

	public String printTransactions(Customer customer) throws ClassNotFoundException, SQLException;
	
	public String addCustomer(Customer customer);

	public Customer showBalance(String mobileNum, String password) throws SQLException;

	public Customer findCustomer(String mobileNum, String password) throws SQLException;

	public void deposit(Customer customer, BigDecimal amount) throws SQLException, ClassNotFoundException, WalletException;
	

}
