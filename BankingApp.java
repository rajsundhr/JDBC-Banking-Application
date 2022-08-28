import java.util.*;

public class BankingApp {
	int choice;
	int flag;
	DAO jdbc;
	Scanner scan = new Scanner(System.in);
	BankingApp() throws Exception{
		jdbc = new DAO();
	}
	
	ArrayList <Customer> customers = new ArrayList<>();

	public void banking()  {
		
		//initial Customer List
		addCustomer(new Customer("Raj","bcBC23",10000));//"bcBC23" is the encrypted password of "abAB12"
		addCustomer(new Customer("Anand","bcBC23",20000));
		addCustomer(new Customer("Srinivasan","bcBC23",30000));
		addCustomer(new Customer("manick","bcBC23",40000));
		addCustomer(new Customer("velu","bcBC23",50000));
		
		System.out.println("Welcome to ABC Banking Application");
		do {
		System.out.println("**********************************************************************************************");
		System.out.println("1. Show All Customers");
		System.out.println("2. Add new Customer ");
		System.out.println("3. Login ");
		System.out.println("4. Show Top N Customers by Balance ");
		System.out.println("5. Exit ");
		System.out.println("\nPlease Enter your option");
		choice = scan.nextInt();
		scan.nextLine();
		switch (choice) {
			case 1: showAllCustomers(); break;
			case 2: addCustomer(); break;
			case 3: int c_id;
			        String password; int count=3;
			        Customer customer;
			        System.out.println("**********************************************************************************************");
					System.out.println("Welcome to Login Portal");
					System.out.println("Enter your Customer Id");
					c_id = scan.nextInt();
					customer = jdbc.getCustomer(c_id);
					if (customer==null) {
						System.out.println("Invalid Customer Id");
						break;
					}
					while(count!=0) {
					System.out.println("Enter your password");
					password = scan.next();
					password = Encryption(password);
					if (password.equals(customer.en_pwd)) 
							break;
					else {
						count--;
						if(count==0) {
							System.out.println("Sorry! Try Login later");
							break;
						}
						else 
							System.out.println("Oh! Wrong Password: "+(count)+" attempts only left");
					  } 
					}
					
					if(count==0)
						break;
			        Login(customer);
			        break;
			case 4: Topncustomers(); 
				     break;
			case 5: System.out.println("Thank You for using our Banking Services");
				    return ;
			
			default: System.out.println("Invalid input\nTry again");
		}
		
		} while(choice!=5);
	}

	private void Topncustomers()  {
		int n;
		System.out.println("Enter the n value");
		n=scan.nextInt();
		if(n<=0) {
			System.out.println("Invalid n value");
			return;
		}
		System.out.println("**********************************************************************************************");
		System.out.println("Top "+n+" Customers by Balance:");
		List<Customer>customers = jdbc.topNCustomer(n);
		for(Customer c: customers )
		c.show();
	}

	private void Login(Customer customer) {
		int choice;
		long account;
		String password="";
		String type;
		Customer transact;
		double amount;
		do {
		System.out.println("**********************************************************************************************");
		System.out.println("1. Show Account Details");
		System.out.println("2. ATM Withdrawal");
		System.out.println("3. Cash Deposit ");
		System.out.println("4. Money Transfer ");
		System.out.println("5. Show All Transactions ");
		System.out.println("6. Password Change ");
		System.out.println("7. Logout and go to Main Menu ");
		System.out.println("\nPlease Enter your option");
		choice = scan.nextInt();
		scan.nextLine();
		switch (choice) {
			case 1: System.out.println("**********************************************************************************************");
			        System.out.println("Your Account Details "); 
			        customer.show();
			        break;
			case 2: System.out.println("Enter an amount to withdraw");
			        amount = scan.nextDouble();
			        if(customer.balance-amount<2000) {
			        	System.out.println("Entered Amount is incorrect, Minbalance Rs.2000 should be maintained\nTry again");
			        }
			        else {
			        	customer.balance -= amount;
			        	jdbc.Addtransaction(customer,"ATM Withdrawal",amount);
			        	System.out.println("Rs."+amount+" withdrawn Successfully"+"\nNewbalance is "+customer.balance);
			        	Check_update(customer);}
			        break;
			case 3: System.out.println("Enter an amount to Deposit");
	                amount = scan.nextDouble();
	        	    customer.balance += amount;
	        	    jdbc.Addtransaction(customer,"Cash Deposit",amount);
	        	    System.out.println("Rs."+amount+" Deposited Successfully"+"\nNewbalance is "+customer.balance);
	        	    Check_update(customer);
	        	    break;
			case 4: System.out.println("Enter Account number to which money transferred");
	                account = scan.nextLong();
	                transact = jdbc.getCustomer(account);
					if (transact==null) {
						System.out.println("Wrong Account Number\nTry again");
					    break; }
	                System.out.println("Enter an amount to transfer");
			        amount = scan.nextDouble();
	                if(customer.balance-amount<2000) {
	        	    System.out.println("Entered Amount is incorrect, Minbalance Rs.2000 should be maintained\nTry again");
	                 }
	                else {
	        	    customer.balance -= amount;
	        	    transact.balance += amount;
	        	    type = "CashTransfer To "+transact.AcNo;
	        	    jdbc.Addtransaction(customer,type,amount);
	        	    type = "CashTransfer From "+customer.AcNo;
	        	    jdbc.Addtransaction(transact, type,amount);
	        	    System.out.println("Rs."+amount+" Deposited Successfully to "+transact.AcNo+"\nYour Newbalance is "+customer.balance);
	        	    Check_update(customer);
	        	    }
	                break;
			case 5: System.out.println("**********************************************************************************************");
	                System.out.println("Customer Name : "+customer.name);
	                System.out.println("Account Number: "+customer.AcNo);
	                System.out.println("Balance       : "+customer.balance);
	                System.out.println("**********************************************************************************************");
	                System.out.println("All your Transactions  : ");
	                List<Transaction> t_list = jdbc.getAllTransaction(customer);
	                int t_No=1;
	                for(Transaction t:t_list) {
	                	System.out.format("Transaction :%3d   ", t_No++);
	                	t.show();
	                }  
			        break;
			case 6:  int count =3;
			        while(count!=0) {
				    System.out.println("Enter your password");
			        password = scan.next();
			        password = Encryption(password);
			        if (password.equals(customer.en_pwd)) 
					break;
			        else {
				    count--;
				    if(count==0) {
					System.out.println("Sorry! Try Login later");
					break;
				    }
				    else 
					System.out.println("Oh! Wrong Password: "+(count)+" attempts only left");
			        } }
			        if(count==0)
				    break;
			        scan.nextLine();
			        password=(Encryption(getpwd()));
			        customer.en_pwd=password;
			        jdbc.changepwd(customer);
			        System.out.println("Password changed Successfully");
	        	    break;
			case 7: System.out.println("Logout Successfully\nRedirect to Main Menu");
    	            return;     
			default: System.out.println("Invalid input\nTry again");
		     }
	  }while(choice!=7);
	}

	private void Check_update(Customer customer) {
		int count = jdbc.check_transaction(customer);
		if(count!=0 && count%3==0) {//actually 10 transactions,but for testing easiness,it is assumed to be 3
			System.out.println("Maintanance Fee of Rs.100 deducted");
			customer.balance -= 100;
			jdbc.Addtransaction(customer, "Maintenance Fee", 100);
		}
		
	}

	private void addCustomer()  {
		String name,password;
		System.out.println("Welcome to Add customer portal");
		System.out.println("Enter Customer Name: ");
		name = scan.nextLine();
		password = getpwd();
		//System.out.println(Encryption(password));
		Customer customer=new Customer(name,Encryption(password));
		jdbc.addCustomer(customer);
		jdbc.Addtransaction(customer,"Opening",customer.balance);
		System.out.println("Customer Added Successfully");
		customer.show();
	}
	private void addCustomer(Customer customer)  {
		jdbc.addCustomer(customer);
		jdbc.Addtransaction(customer,"Opening",customer.balance);
		
	}

	private String Encryption(String password) {
		StringBuilder res = new StringBuilder();
		for(char c: password.toCharArray()) {
			if (c>='a'&& c<='z') {
				if(c=='z')
					res.append('a');
				else
					res.append((char)(c+1)); //encryption is changing each character to next character in ASCII table
			}
			else if (c>='A'&& c<='Z') {
				if(c=='Z')
					res.append('A');
				else
					res.append((char)(c+1));
			}
			else if (c>='0'&& c<='9') {
				if(c=='9')
					res.append('0');
				else
					res.append((char)(c+1));
			}
		}
		return res.toString();
	}

	private String getpwd() {
		String ps1,ps2,res="";
		int flag=0;
		while(flag==0)  {
		System.out.println("Enter the password (0-9) (a-z) (A-Z)");
		ps1=scan.nextLine();
		if(checkpwd(ps1)==0) {
			System.out.println("Oh!");
			System.out.println("Password Should contains atleast 2 alphabet, 2 lowercase letter and 2 upper case letter");
			System.out.println("No Special Character allowed\nTry again ");
			System.out.println("**********************************************************************************************");
			continue;
		}
		else {
		System.out.println("ReEnter the password");
		ps2=scan.nextLine();
		if(ps1.equals(ps2)) {
			res =  ps1; 
			flag=1;
		}
		else {
			System.out.println("Oh! Passwords are not match, Try again");
			flag=0;
		    }
		    }
		}
		return res;
	}

	private int checkpwd(String ps1) {
		//checking Password Should contains atleast 2 alphabet, 2 lowercase letter,2 upper case letter
		int n=0,l=0,u=0;
		for(char c:ps1.toCharArray()) {
			if(c>='a'&&c<='z')
				l++;
			else if (c>='A'&&c<='Z')
				u++;
			else if (c>='0'&&c<='9')
				n++;
			else 
				return 0;
		}
		if( l<2 || n<2 || u<2 )
		    return 0;
		else 
			return 1;
	}

	private void showAllCustomers()  {
		
		System.out.println("**********************************************************************************************");
		System.out.println("All Customer Details ");
		List<Customer>customers = jdbc.getAllCustomer();
		for(Customer c: customers )
		c.show();
	}

}
