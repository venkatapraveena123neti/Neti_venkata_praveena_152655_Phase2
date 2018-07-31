package com.cg.walletapplicationphase2.repo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cg.walletapplicationphase2.bean.Customer;
import com.cg.walletapplicationphase2.exception.IWalletException;
import com.cg.walletapplicationphase2.exception.WalletException;
import com.cg.walletapplicationphase2.util.DButil;



public class WalletRepoImpl implements IWalletRepo {
	/*******************************************************************************************************
	 - Class Name	    :	<WalletRepoImpl>
	 - Input Parameters	:	<null>
	 - Return Type		:	<database details>
	 - Throws			:  	<null>
	 - Author			:	<Neti_Venkata_Praveena>
	 - Creation Date	:	31/07/2018
	 - Description		:	implement interface methods for getting details
	 ********************************************************************************************************/ 
	Connection connection = null;

	public WalletRepoImpl() {
		connection = DButil.getConnection();
	}

	public String addCustomer(Customer customer) {
		String result = null;
		int flag = 0;
		try {
			String sql = "insert into customer_wallet values (?,?,?,?,?)";
			PreparedStatement ptst = connection.prepareStatement(sql);
			ptst.setString(1, customer.getMobileNumber());
			ptst.setString(2, customer.getEmailId());
			ptst.setString(3, customer.getName());
			ptst.setString(4, customer.getPassword());
			ptst.setBigDecimal(5, customer.getWallet().getBalance());
			flag = ptst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (flag == 1)
			result = customer.getMobileNumber();
		return result;
	}

	public Customer showBalance(String mobileNum, String password) throws SQLException {
		Customer result = null;
		String sql = "select * from customer_wallet where mobile_no = '" + mobileNum + "'";
		PreparedStatement ptst = connection.prepareStatement(sql);
		ResultSet rs = ptst.executeQuery();
		if (rs.next()) {
			String newPassword = rs.getString("CUSTOMER_PASSWORD");
			if (newPassword.equals(password)) {

				result = new Customer();
				result.getWallet().setBalance(rs.getBigDecimal("balance"));

			}

		}
		return result;

	}

	public Customer findCustomer(String mobileNum, String password) throws SQLException {

		Customer customer = null;

		String sql = "select * from customer_wallet where mobile_no = '" + mobileNum + "'";
		PreparedStatement ptst = connection.prepareStatement(sql);
		ResultSet rs = ptst.executeQuery();
		if (rs.next()) {
			String actualPassword = rs.getString("CUSTOMER_PASSWORD");
			if (actualPassword.equals(password)) {
				customer = new Customer();
				customer.setMobileNumber(rs.getString("MOBILE_NO"));
				customer.setName(rs.getString("CUSTOMER_NAME"));
				customer.setEmailId(rs.getString("EMAIL_ID"));
				customer.setPassword(rs.getString("CUSTOMER_PASSWORD"));
				customer.getWallet().setBalance(rs.getBigDecimal("BALANCE"));
			}
		}

		return customer;
	}

	public void deposit(Customer customer, BigDecimal amount)
			throws SQLException, ClassNotFoundException, WalletException {
		String sql = "select * from customer_wallet where mobile_no = '" + customer.getMobileNumber() + "'";
		PreparedStatement ptst = connection.prepareStatement(sql);
		ResultSet rs = ptst.executeQuery();
		BigDecimal existingBalance = BigDecimal.valueOf(0.0);
		if (rs.next())
			existingBalance = rs.getBigDecimal("BALANCE");
		BigDecimal newBal = existingBalance.add(amount);
		String updateQuery = "update customer_wallet set balance='" + newBal + "' where mobile_no = '"
				+ customer.getMobileNumber() + "'";
		ptst = connection.prepareStatement(updateQuery);
		int output = ptst.executeUpdate();
		if (output == 1) {
			String query = "Insert Into Transactions VALUES (?,?,?,?,?)";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, getTransactionId());
			pstmt.setString(2, customer.getMobileNumber());
			java.util.Date today = new java.util.Date();
			pstmt.setTimestamp(3, new java.sql.Timestamp(today.getTime()));
			pstmt.setString(4, "Credited");
			pstmt.setBigDecimal(5, amount);
			pstmt.executeUpdate();
		} else {
			throw new WalletException(IWalletException.transactionfailError);
		}

	}

	private int getTransactionId() throws ClassNotFoundException, WalletException {
		// TODO Auto-generated method stub
		int empId = 0;
		String sql = "SELECT transaction_sequence.NEXTVAL FROM DUAL";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system",
					"Capgemini123");
			PreparedStatement pstmt = connection.prepareStatement(sql);
			ResultSet res = pstmt.executeQuery();
			if (res.next()) {
				empId = res.getInt(1);
			}
		} catch (SQLException e) {
			throw new WalletException(IWalletException.sqlException);
		}
		return empId;
	}

	public boolean withdraw(Customer customer, BigDecimal amount)
			throws ClassNotFoundException, SQLException, WalletException {
		boolean result = false;
		String sql = "select * from customer_wallet where mobile_no = '" + customer.getMobileNumber() + "'";
		PreparedStatement ptst = connection.prepareStatement(sql);
		ResultSet rs = ptst.executeQuery();
		if (rs.next()) {
			if (rs.getBigDecimal("BALANCE").subtract(amount).compareTo(BigDecimal.valueOf(0.0)) >= 0) {
				BigDecimal existingBal = rs.getBigDecimal("BALANCE");
				BigDecimal newBal = existingBal.subtract(amount);
				String updateQuery = "update customer_wallet set balance='" + newBal + "' where mobile_no = '"
						+ customer.getMobileNumber() + "'";
				ptst = connection.prepareStatement(updateQuery);
				int i = ptst.executeUpdate();
				customer.getWallet().setBalance(newBal);
				String query = "Insert Into Transactions VALUES (?,?,?,?,?)";
				PreparedStatement pstmt = connection.prepareStatement(query);
				pstmt.setInt(1, getTransactionId());
				pstmt.setString(2, customer.getMobileNumber());
				java.util.Date today = new java.util.Date();
				pstmt.setTimestamp(3, new java.sql.Timestamp(today.getTime()));
				pstmt.setString(4, "Debited");
				pstmt.setBigDecimal(5, amount);
				int j = pstmt.executeUpdate();
				if (j == 1)
					result = true;
			} else
				throw new WalletException(IWalletException.insufficientFunds);
		}
		return result;
	}

	public boolean customerExists(String receiverMobile) throws SQLException {
		boolean result = false;
		Customer customer = null;
		String sql = "select * from customer_wallet where mobile_no = '" + receiverMobile + "'";
		PreparedStatement ptst = connection.prepareStatement(sql);
		ResultSet rs = ptst.executeQuery();
		if (rs.next()) {
			customer = new Customer();
			customer.setMobileNumber(rs.getString("MOBILE_NO"));
			customer.setName(rs.getString("CUSTOMER_NAME"));
			customer.setEmailId(rs.getString("EMAIL_ID"));
			customer.setPassword(rs.getString("CUSTOMER_PASSWORD"));
			customer.getWallet().setBalance(rs.getBigDecimal("BALANCE"));
		}
		if (customer != null)
			result = true;
		return result;
	}

	public Customer transfer(String senderMobile, String receiverMobile, BigDecimal amount)
			throws ClassNotFoundException, SQLException, WalletException {
		boolean result = false;
		String sqlsender = "select * from customer_wallet where mobile_no = '" + senderMobile + "'";
		String sqlreciever = "select * from customer_wallet where mobile_no = '" + receiverMobile + "'";
		PreparedStatement ptst = connection.prepareStatement(sqlsender);
		ResultSet rs = ptst.executeQuery();
		Customer senderCustomer = null;
		if (rs.next()) {
			senderCustomer = new Customer();
			senderCustomer.setMobileNumber(rs.getString("MOBILE_NO"));
			senderCustomer.setName(rs.getString("CUSTOMER_NAME"));
			senderCustomer.setEmailId(rs.getString("EMAIL_ID"));
			senderCustomer.setPassword(rs.getString("CUSTOMER_PASSWORD"));
			senderCustomer.getWallet().setBalance(rs.getBigDecimal("BALANCE"));
		}
		ptst = connection.prepareStatement(sqlreciever);
		rs = ptst.executeQuery();
		Customer receiverCustomer = null;
		if (rs.next()) {
			receiverCustomer = new Customer();
			receiverCustomer.setMobileNumber(rs.getString("MOBILE_NO"));
			receiverCustomer.setName(rs.getString("CUSTOMER_NAME"));
			receiverCustomer.setEmailId(rs.getString("EMAIL_ID"));
			receiverCustomer.setPassword(rs.getString("CUSTOMER_PASSWORD"));
			receiverCustomer.getWallet().setBalance(rs.getBigDecimal("BALANCE"));
		}

		if (withdraw(senderCustomer, amount)) {

			deposit(receiverCustomer, amount);
			result = true;
		}
		return senderCustomer;
	}

	public String printTransactions(Customer customer) throws ClassNotFoundException, SQLException {
		String query = "Select * from transactions where Mobile_no = '" + customer.getMobileNumber() + "' order by id";
		PreparedStatement pstmt = connection.prepareStatement(query);
		ResultSet resultSet = pstmt.executeQuery();
		StringBuilder builder = new StringBuilder();
		while (resultSet.next()) {
			builder.append(resultSet.getTimestamp("TIMESTAMPOFTRANS") + " " + resultSet.getString("TYPE") + " "
					+ resultSet.getBigDecimal("AMOUNT"));
			builder.append(",");
		}
		return builder.toString();
	}

}
