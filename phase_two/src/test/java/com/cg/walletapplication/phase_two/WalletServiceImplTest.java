package com.cg.walletapplication.phase_two;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.junit.Test;

import com.cg.walletapplicationphase2.bean.Customer;
import com.cg.walletapplicationphase2.bean.Wallet;
import com.cg.walletapplicationphase2.exception.WalletException;
import com.cg.walletapplicationphase2.service.IWalletService;
import com.cg.walletapplicationphase2.service.WalletServiceImpl;




public class WalletServiceImplTest {
	/*******************************************************************************************************
	 - Class Name	    :	<WalletServiceImplTest>
	 - Input Parameters	:	<null>
	 - Return Type		:	<test results>
	 - Throws			:  	<null>
	 - Author			:	<Neti_Venkata_Praveena>
	 - Creation Date	:	31/07/2018
	 - Description		:	testing methods
	 ********************************************************************************************************/ 
	public static IWalletService iWalletService=new WalletServiceImpl();
    @Test
	public void addCustomerTestTrue() throws WalletException
	{
		Customer customer1 = new Customer("9685741253","ola2","ola2@123","ola2@gmail.com",new Wallet());
		assertEquals("9685741253",iWalletService.addCustomer(customer1));
			
	}
    @Test(expected = WalletException.class)
  	public void addCustomerTestFalse() throws WalletException
  	{

  		Customer customer2 = new Customer("9911574464","Pavan","Pavan@123","pavan123@gmail.com",new Wallet());
  		assertNotEquals("56968621",iWalletService.addCustomer(customer2));
  		
  	}
	

	@Test
	public void initBalanceTest() throws WalletException
	{
		Customer customer1 = new Customer("8574123096","ola3","ola@123","olaaa@gmail.com",new Wallet());
		iWalletService.addCustomer(customer1);
		assertEquals(BigDecimal.valueOf(0.0),customer1.getWallet().getBalance());
		
	}
	
	@Test
	public void depositMoneyTest() throws WalletException, ClassNotFoundException, SQLException
	{
		Customer customer2 = new Customer("7586953654","priya","Priya@123","priya@gmail.com",new Wallet());
		iWalletService.addCustomer(customer2);
		iWalletService.deposit(customer2, BigDecimal.valueOf(8500.00));
		Customer result = iWalletService.showBalance("7586953654", "Priya@123");
		System.out.println(result.getWallet().getBalance());
		assertEquals(BigDecimal.valueOf(8500.00),result.getWallet().getBalance());
	}
	@Test
	public void withdrawMoneyTestTrue() throws WalletException, ClassNotFoundException, SQLException
	{
		Customer customer2 = new Customer("8544754477","Nicky","Nicky@123","Nick123@gmail.com",new Wallet());
		iWalletService.addCustomer(customer2);
		iWalletService.deposit(customer2, BigDecimal.valueOf(500.00));
		assertTrue(iWalletService.withDraw(customer2, BigDecimal.valueOf(300.00)));
	}

	
	@Test(expected = WalletException.class)
	public void withdrawMoneyTestFalse() throws WalletException, ClassNotFoundException, SQLException
	{
		Customer customer2 = new Customer("9911574464","Pavan","Pavan@123","pavan123@gmail.com",new Wallet());
		iWalletService.addCustomer(customer2);
		iWalletService.deposit(customer2, BigDecimal.valueOf(500.00));
		assertFalse(iWalletService.withDraw(customer2, BigDecimal.valueOf(900.00)));
	}

}
