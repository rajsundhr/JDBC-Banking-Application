
public class Customer {
	   static int id=1;
	   static long account=10001;
       int c_id;
       String name;
       String en_pwd;
       long AcNo;
       double balance;
       
       Customer(String name ,String en_pwd,double balance)   {
    	   this.name = name;
    	   this.balance = balance;
    	   this.en_pwd = en_pwd;
    	   this.c_id = id++;
    	   this.AcNo = account++;
       }
       
       Customer(String name,String en_pwd)   {
    	   this.name = name;
    	   this.balance = 10000; //default value
    	   this.en_pwd = en_pwd;
    	   this.c_id = id++;
    	   this.AcNo = account++;
       }
       
       Customer(int c_id,String name, String en_pwd,long AcNo,double balance)   {
    	   //constructor to get each resultset from database as customer object
    	   this.c_id = c_id;
    	   this.name = name;
    	   this.en_pwd = en_pwd;
    	   this.AcNo = AcNo; 
    	   this.balance = balance;	   
       }
	
	public void show() {
		System.out.format("Customer Id :%3d  ",c_id);
		System.out.format("Customer Name : %-10s  ",name);
		System.out.format("Account Number: %-10d",AcNo);
		System.out.format("Balance: %.2f",balance);
		System.out.println();
		
	}
       
}
