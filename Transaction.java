
public class Transaction {
         
		 int t_id;
         String type;
         double balance;
         double amount;
         
       Transaction (String type, double amount, double balance) {
     		this.t_id=-1;
     		this.type = type;
     		this.amount = amount;
     		this.balance = balance;
     	}
       
       Transaction (int t_id,String type, double amount, double balance) {
    	 //constructor to get each resultset from database as transaction object
    		this.t_id=t_id;
    		this.type = type;
    		this.amount = amount;
    		this.balance = balance;
    	}

		public void show() {
			
			System.out.format("Transaction Type:%-25s   ", type);
			System.out.format("Transaction Amount:%.4f   ", amount);
			System.out.format("Balance:%.2f   ", balance);
			System.out.println();
		}
       
         
}
