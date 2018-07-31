package com.cg.walletapplicationphase2.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.walletapplicationphase2.bean.Customer;
import com.cg.walletapplicationphase2.exception.IWalletException;
import com.cg.walletapplicationphase2.exception.WalletException;
import com.cg.walletapplicationphase2.repo.IWalletRepo;
import com.cg.walletapplicationphase2.repo.WalletRepoImpl;


public class WalletServiceImpl implements IWalletService {
	/*******************************************************************************************************
	 - Class Name	    :	<WalletServiceImpl>
	 - Input Parameters	:	<null>
	 - Return Type		:	<database details>
	 - Throws			:  	<null>
	 - Author			:	<Neti_Venkata_Praveena>
	 - Creation Date	:	31/07/2018
	 - Description		:	implement interface methods for business logics
	 ********************************************************************************************************/ 
	static IWalletRepo iWalletDao = null;

	static {

		iWalletDao = new WalletRepoImpl();
	}

	public void checkName(String name) throws WalletException {
		Pattern pattern = Pattern.compile("[a-zA-Z]{1,}");
		Matcher matcher = pattern.matcher(name);
		if (!(matcher.matches())) {
			throw new WalletException(IWalletException.nameError);
		}
	}

	public void checkMobileNumber(String mobileNumber) throws WalletException {

		Pattern pattern = Pattern.compile("[7-9]{1}[0-9]{9}");
		Matcher matcher = pattern.matcher(mobileNumber);
		if (!(matcher.matches())) {
			throw new WalletException(IWalletException.mobileNumError);
		}
	}

	public void checkPassword(String password) throws WalletException {
		Pattern pattern = Pattern.compile(".*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*" + "");
		Matcher matcher = pattern.matcher(password);
		if (!(matcher.matches())) {
			throw new WalletException(IWalletException.passwordError);
		}

	}

	public void checkEmail(String emailId) throws WalletException {
		Pattern pattern = Pattern.compile("[a-z]{1}[a-z0-9._]{1,}@[a-zA-Z0-9]{1,}.com");
		Matcher matcher = pattern.matcher(emailId);
		if (!(matcher.matches())) {
			throw new WalletException(IWalletException.emailError);
		}

	}

	public String addCustomer(Customer customer) throws WalletException {
		String result = null;
		result = iWalletDao.addCustomer(customer);
		if(result==null)
		 throw new WalletException(IWalletException.accountExistingError);
		 
		return result;
	}

	public Customer showBalance(String mobileNum, String password) throws WalletException, SQLException {
		Customer result = null;
		result = iWalletDao.showBalance(mobileNum, password);

		if (result == null)
			throw new WalletException(IWalletException.invalidDetails);

		return result;
	}

	public Customer check(String mobileNum, String password) throws WalletException, SQLException {
		Customer result = null;
		result = iWalletDao.findCustomer(mobileNum, password);

		if (result == null)
			throw new WalletException(IWalletException.invalidDetails);
		return result;
	}

	public void deposit(Customer customer, BigDecimal amount)
			throws ClassNotFoundException, SQLException, WalletException {
	
		iWalletDao.deposit(customer, amount);
	

	}

	public boolean withDraw(Customer customer, BigDecimal amount)
			throws WalletException, ClassNotFoundException, SQLException {
		boolean result = false;
		
		result = iWalletDao.withdraw(customer, amount);
		
		if (result == false) {
			throw new WalletException(IWalletException.insufficientFunds);
		}
		return result;
	}

	public boolean isFound(String receiverMobile) throws WalletException, SQLException {
		boolean result = false;
		
		if (iWalletDao.customerExists(receiverMobile)) {
			result = true;
		}
		
		if (result == false)
			throw new WalletException(IWalletException.mobileNumberNotExists);

		return result;
	}

	public Customer transfer(String senderMobile, String receiverMobile, BigDecimal amount)
			throws InterruptedException, WalletException, ClassNotFoundException, SQLException {
		
		Customer result=null;
	
	
		result=iWalletDao.transfer(senderMobile,receiverMobile,amount);
		
		return result;
	}

	public String printTransactions(Customer customer) throws ClassNotFoundException, SQLException {
		
		
	    String builder = iWalletDao.printTransactions(customer);
	
		return builder;
	}



}
