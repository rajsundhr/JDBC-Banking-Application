import java.sql.*;
import java.util.*;

public class DAO {
	
		    Connection con=null;
		    
		    public void getconnection()  {
		        try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3307/Banking","root","1234");//(url,username,password) of mysql
			    } catch (Exception e) {
				System.out.println("Exception to connect to driver");
			    }
		    }
		    
		    public void closeconnection() {
		    	if(con!=null)
		    	try {
					con.close();
				} catch (SQLException e) {
					System.out.println("SQL Exception");
				}
		    }
		
		public void addCustomer(Customer c) {
			getconnection();
			try {
				PreparedStatement pst = con.prepareStatement("insert into  customer values (?,?,?,?,?)");
				pst.setInt(1, c.c_id);
				pst.setString(2, c.name);
				pst.setString(3, c.en_pwd);
				pst.setLong(4, c.AcNo);
				pst.setDouble(5, c.balance);
				pst.executeUpdate();
				pst.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				closeconnection();
			}
			
		}
		
		public List<Customer> getAllCustomer()  {
			
			getconnection();
			List <Customer> customers = new ArrayList<>();
			try {
				
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("Select c_id, name, en_pwd, AcNo, balance from customer");
				while(rs.next()) {
					Customer customer = new Customer(rs.getInt("c_id"),rs.getString("name"),rs.getString("en_pwd"),rs.getLong("AcNo"),rs.getDouble("balance"));
					customers.add(customer);
				}
				st.close();
			} catch (SQLException e) {
				System.out.println("SQL Exception");
			} finally {
				closeconnection();
			}
			
			return customers;
		}

		public List<Customer> topNCustomer(int n) {
			getconnection();
			List <Customer> customers = new ArrayList<>();
			try {
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("Select c_id, name, en_pwd, AcNo, balance from customer order by balance desc limit "+ n);
				while(rs.next()) {
					Customer customer = new Customer(rs.getInt("c_id"),rs.getString("name"),rs.getString("en_pwd"),rs.getLong("AcNo"),rs.getDouble("balance"));
					customers.add(customer);
				}
				st.close();
			} catch (SQLException e) {
				System.out.println("SQL Exception");
			}finally {
				closeconnection();
			}
			
			return customers;
		}

		public Customer getCustomer(int c_id)  {
			getconnection();
			Customer customer=null;
			try {
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("Select c_id, name, en_pwd, AcNo, balance from customer where c_id = "+c_id);
				rs.next();
			    customer = new Customer(rs.getInt("c_id"),rs.getString("name"),rs.getString("en_pwd"),rs.getLong("AcNo"),rs.getDouble("balance"));
				st.close();
			} catch (SQLException e) {
				System.out.println("SQL Exception");
			}finally {
				closeconnection();
			}
			
			return customer;
		}
		
		public Customer getCustomer(long AcNo)  {
			getconnection();
			Customer customer=null;
			try {
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("Select c_id, name, en_pwd, AcNo, balance from customer where AcNo = " + AcNo);
				rs.next();
			    customer = new Customer(rs.getInt("c_id"),rs.getString("name"),rs.getString("en_pwd"),rs.getLong("AcNo"),rs.getDouble("balance"));
				st.close();
			} catch (SQLException e) {
				System.out.println("SQL Exception");
			}finally {
				closeconnection();
			}
			
			return customer;
		}

		public void Addtransaction(Customer customer,String type, double amount) {
			getconnection();
			try {
				PreparedStatement pst = con.prepareStatement("insert into transaction(c_id,type,amount,balance) values (?,?,?,?)");
				pst.setInt(1, customer.c_id);
				pst.setString(2, type);
				pst.setDouble(3, amount);
				pst.setDouble(4, customer.balance);
				pst.executeUpdate();
				Statement st = con.createStatement();
				st.executeUpdate("Update customer set balance = "+customer.balance+"where c_id = "+customer.c_id);
				pst.close();st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				closeconnection();
			}
			
		}
		
		public int check_transaction(Customer customer) {
			getconnection();
			int count=0;
			try {
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("Select count(*) from transaction where c_id = "+ customer.c_id + " and type not in ('Opening','Maintenance Fee','CashTransfer From%')");
				rs.next();
				count = rs.getInt(1);
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				closeconnection();
			}
			return count;
			
		}

		public List<Transaction> getAllTransaction(Customer customer) {
			getconnection();
			List <Transaction> t_list = new ArrayList<>();
			try {
				
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("Select t_id, type, amount, balance from transaction where c_id = "+customer.c_id);
				while(rs.next()) {
					Transaction trans = new Transaction(rs.getInt("t_id"),rs.getString("type"),rs.getDouble("amount"),rs.getDouble("balance"));
					t_list.add(trans);
				}
				st.close();
			} catch (SQLException e) {
				System.out.println("SQL Exception");
			} finally {
				closeconnection();
			}
			
			return t_list;
		}

		public void changepwd(Customer customer) {
			getconnection();
			try {
				
				Statement st = con.createStatement();
				st.executeUpdate("Update customer Set en_pwd = '"+customer.en_pwd+"' where c_id = "+customer.c_id);
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				closeconnection();
			}
			
		}
		
}
