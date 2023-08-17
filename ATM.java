import java.awt.Desktop;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
public class ATM extends Bank{
	static Scanner read=new Scanner(System.in);
	static int Ac_no;
	static int Pin;
	static int Balance;
	static File Passbook;
	static File Receipt=new File("E:\\bank project\\src\\customer passbooks\\"+1+".txt");
	public static void main(String[] args) {
		loadAc_nos();
		System.out.println("\t......WELLCOME......");
		System.out.print("\nEnter five digit account number: ");
		Ac_no=read.nextInt();
		if(Ac_nos.contains(Ac_no))
		{
			try {          //load file Pin and Balance from DB
				Passbook=new File("E:\\bank project\\src\\customer passbooks\\"+Ac_no+".txt");
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","12345");
				Statement statement=con.createStatement();
				String query="select * from bankaccounts where Ac_no="+Ac_no+";";
				ResultSet resultset=statement.executeQuery(query);
				resultset.next();
				if(resultset.getInt(2)==0)
				{
					System.out.println("You are new to our bank...");
					generatePin(Ac_no);
					return;
				}
				else
				{
					Pin=resultset.getInt(2);
					Balance=resultset.getInt(3);
				}
				resultset.close();
				statement.close();
				con.close();
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
				return;
			}
			System.out.print("Enter your four digit pin: ");
			int pin=read.nextInt();
			if(Pin==pin)
			{
				System.out.println(".................................");
				System.out.println("1.Deposit\n2.Withdraw\n3.Balance enquiry\n4.Mini Statement\n5.Exit");
				System.out.println(".................................");
				System.out.print("Select your choice: ");
				int choice=read.nextInt();
				switch(choice)
				{
				case 1:
					deposit();
					System.out.print("\nTransaction Successfull...");
					break;
				case 2:
					withdraw();
					System.out.print("\nTransaction Successfull...");
					break;
				case 3:
					balanceEnquiry();
					break;
				case 4:
					miniStatement();
					break;
				case 5:
					System.out.print("Exited...");
					return;
				default:
					System.out.println("Invalid input...");
					
				}
			}
			else
			{
				System.out.println("Invalid Pin...");
			}
		}
		else
		{
			System.out.print("Invalid Account Number...");
		}
	}
	private static void miniStatement() {
		try {
			FileWriter rwriter = new FileWriter(Receipt);
			rwriter.write("\t\t.........IO BANK.........");
			rwriter.append("\n\nAccount number: "+Ac_no);//writing receipt file
			rwriter.append("\n....................................................");
			rwriter.append("\nDate      \tTransaction    \t\tBalance");
			rwriter.append("\n....................................................");
			Scanner read=new Scanner(Passbook);
			int i=1;
			while(i<9)
			{
				read.nextLine();
				i++;
			}
			while(read.hasNext())
			{
				rwriter.append("\n"+read.nextLine());
			}
			read.close();
			rwriter.close();
			System.out.print("\nColect your recepit...");
			Desktop.getDesktop().open(Receipt);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	private static void balanceEnquiry() {
		System.out.print("\nYou have "+Balance+"rs in your account...");
		System.out.print("\n\nPress '1' to generate receipt");
		System.out.print("\nDo you want receipt for this transaction: ");
		int choice=read.nextInt();
		if(choice==1) {
			try{
				FileWriter rwriter = new FileWriter(Receipt);
				rwriter.write("\t\t.........IO BANK.........");
				rwriter.append("\n\nAccount number: "+Ac_no+"\t\tAvailable Balance: "+Balance);//writing receipt file
				rwriter.append("\n.......................................................");
				rwriter.close();
				Desktop.getDesktop().open(Receipt);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	private static void withdraw() {
		System.out.print("\nEnter amount to withdraw: ");
		int amount=read.nextInt();
		if(Balance>=amount)
		{
			Balance-=amount;
			updateDB();
			System.out.print("Collect Your Cash...\nYour Balance: "+Balance);
			try {
				FileWriter writer=new FileWriter(Passbook,true);
				writer.append("\n"+LocalDate.now()+"\t"+amount+"rs debited"+"\t\t"+Balance);
				FileWriter rwriter = new FileWriter(Receipt);
				rwriter.write("\t\t.........IO BANK.........");
				rwriter.append("\n\nAccount number: "+Ac_no);//writing receipt file
				rwriter.append("\n....................................................");
				rwriter.append("\nDate      \tTransaction    \t\tBalance");
				rwriter.append("\n....................................................");
				rwriter.append("\n"+LocalDate.now()+"\t"+amount+"rs debited"+"\t\t"+Balance);
				rwriter.close();
				writer.close();
				System.out.print("\n\nPress '1' to generate receipt");
				System.out.print("\nDo you want receipt for this transaction: ");
				int choice=read.nextInt();
				if(choice==1) {Desktop.getDesktop().open(Receipt);}
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Insufficient Balance...");
			System.out.print("You have "+Balance+"rs only...");
		}
	}
	private static void deposit() {
		System.out.print("\nEnter amount to deposit: ");
		int amount=read.nextInt();
		Balance+=amount;
		updateDB();
		try{
			FileWriter writer = new FileWriter(Passbook,true);
			writer.append("\n"+LocalDate.now()+"\t"+amount+"rs credited"+"\t"+Balance);
			FileWriter rwriter = new FileWriter(Receipt);
			rwriter.write("\t\t.........IO BANK.........");
			rwriter.append("\n\nAccount number: "+Ac_no);//writing receipt file
			rwriter.append("\n....................................................");
			rwriter.append("\nDate      \tTransaction    \t\tBalance");
			rwriter.append("\n....................................................");
			rwriter.append("\n"+LocalDate.now()+"\t"+amount+"rs credited"+"\t"+Balance);
			rwriter.close();
			writer.close();
			System.out.println("\nAmount Deposited Successfully...\nYour Balance: "+Balance);
			System.out.print("\nPress '1' to generate receipt\n");
			System.out.print("Do you want receipt for this transaction: ");
			int choice=read.nextInt();
			if(choice==1) {Desktop.getDesktop().open(Receipt);}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void updateDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","12345");
			Statement statement=con.createStatement();
			String query="update bankaccounts set Balance="+Balance+" where Ac_no="+Ac_no+";";
			statement.executeUpdate(query);
			statement.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	private static void generatePin(int Ac_no)
	{
		System.out.print("Please Create your four digit pin and press enter: ");
		int pin=read.nextInt();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","12345");
			Statement statement=con.createStatement();
			String query="update bankaccounts set Pin="+pin+" where Ac_no="+Ac_no+";";
			statement.executeUpdate(query);
			query="update bankaccounts set Balance="+0+" where Ac_no="+Ac_no+";";
			statement.executeUpdate(query);
			Pin=pin;
			ATM.Balance=0;
			System.out.println("Pin Generated Successfully...\nYour Balance: "+Balance);
			statement.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
