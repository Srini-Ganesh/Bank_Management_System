import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.io.*;
public class MobileTransaction extends Bank{
	static Scanner read1=new Scanner(System.in);
	static int sAc_no,rAc_no,Pin,amt;
	static int sBalance,rBalance;
	static Scanner read;
	static File sPassbook,rPassbook;
	public static void main(String[] args) {
		loadAc_nos();
		System.out.println("\t......MOBILE TRANSACTION......");
		System.out.print("\nEnter five digit account number: ");
		sAc_no=read1.nextInt();
		if(Ac_nos.contains(sAc_no))
		{
			System.out.println(".................................");
			System.out.println("1.Using account number\n2.Using mobile number\n3.Using UPI id\n4.Exit");
			System.out.println(".................................");
			System.out.print("Select your choice: ");
			int choice=read1.nextInt();
			read1.nextLine();
			switch(choice)
			{
			case 1:
				System.out.print("Enter receiver's account number:");
				rAc_no=read1.nextInt();
				if(Ac_nos.contains(rAc_no))
				{
					System.out.print("Enter amount to sent:");
					amt=read1.nextInt();
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","12345");
						Statement statement=con.createStatement();
						String query="select * from bankaccounts where Ac_no="+sAc_no+";";
						ResultSet result=statement.executeQuery(query);
						result.next();
						Pin=result.getInt(2);
						sBalance=result.getInt(3);
						query="select * from bankaccounts where Ac_no="+rAc_no+";";
						ResultSet result1=statement.executeQuery(query);
						result1.next();
						rBalance=result1.getInt(3);
						System.out.print("Enter your four digit pin: ");
						int pin=read1.nextInt();
						if(Pin==pin)
						{
							if(amt<=sBalance)
							{
								sBalance-=amt;
								query="update bankaccounts set Balance="+sBalance+" where Ac_no="+sAc_no+";";
								statement.executeUpdate(query);
								rBalance+=amt;
								query="update bankaccounts set Balance="+rBalance+" where Ac_no="+rAc_no+";";
								statement.executeUpdate(query);
								updatePassbook(sAc_no,sBalance,rAc_no,rBalance);
							}
							else
							{
								System.out.println("Insufficient Balance...");
								System.out.print("You have "+sBalance+"rs only...");
							}
						}
						else
						{
							System.out.println("Invalid pin...");
						}
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
						e.printStackTrace();
					}				
					System.out.print("\nTransaction Successfull...");
				}
				else
				{
					System.out.println("Invalid account number...");
				}
		        break;	
			case 2:
				System.out.print("Enter receiver's mobile number:");
				String mobile=read1.nextLine();
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","12345");
					Statement statement=con.createStatement();
					String query="select * from bankaccounts where Mobile_no="+mobile+";";
					ResultSet result=statement.executeQuery(query);
					result.next();
					rAc_no=result.getInt(1);
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				if(Ac_nos.contains(rAc_no))
				{
					System.out.print("Enter amount to sent:");
					amt=read1.nextInt();
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","12345");
						Statement statement=con.createStatement();
						String query="select * from bankaccounts where Ac_no="+sAc_no+";";
						ResultSet result=statement.executeQuery(query);
						result.next();
						Pin=result.getInt(2);
						sBalance=result.getInt(3);
						query="select * from bankaccounts where Ac_no="+rAc_no+";";
						ResultSet result1=statement.executeQuery(query);
						result1.next();
						rBalance=result1.getInt(3);
						System.out.print("Enter your four digit pin: ");
						int pin=read1.nextInt();
						if(Pin==pin)
						{
							if(amt<=sBalance)
							{
								sBalance-=amt;
								query="update bankaccounts set Balance="+sBalance+" where Ac_no="+sAc_no+";";
								statement.executeUpdate(query);
								rBalance+=amt;
								query="update bankaccounts set Balance="+rBalance+" where Ac_no="+rAc_no+";";
								statement.executeUpdate(query);
								updatePassbook(sAc_no,sBalance,rAc_no,rBalance);
							}
							else
							{
								System.out.println("Insufficient Balance...");
								System.out.print("You have "+sBalance+"rs only...");
							}
						}
						else
						{
							System.out.println("Invalid pin...");
						}
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
						e.printStackTrace();
					}				
					System.out.print("\nTransaction Successfull...");
				}
				else
				{
					System.out.println("Invalid mobile number...");
				}
				break;
			case 3:
				System.out.print("Enter receiver's UPI id:");
				String upi=read1.nextLine();
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","12345");
					Statement statement=con.createStatement();
					String query="select * from bankaccounts where UPI_id='"+upi+"';";
					ResultSet result=statement.executeQuery(query);
					result.next();
					rAc_no=result.getInt(1);
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				
				if(Ac_nos.contains(rAc_no))
				{
					System.out.print("Enter amount to sent:");
					amt=read1.nextInt();
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","12345");
						Statement statement=con.createStatement();
						String query="select * from bankaccounts where Ac_no="+sAc_no+";";
						ResultSet result=statement.executeQuery(query);
						result.next();
						Pin=result.getInt(2);
						sBalance=result.getInt(3);
						query="select * from bankaccounts where Ac_no="+rAc_no+";";
						ResultSet result1=statement.executeQuery(query);
						result1.next();
						rBalance=result1.getInt(3);
						System.out.print("Enter your four digit pin: ");
						int pin=read1.nextInt();
						if(Pin==pin)
						{
							if(amt<=sBalance)
							{
								sBalance-=amt;
								query="update bankaccounts set Balance="+sBalance+" where Ac_no="+sAc_no+";";
								statement.executeUpdate(query);
								rBalance+=amt;
								query="update bankaccounts set Balance="+rBalance+" where Ac_no="+rAc_no+";";
								statement.executeUpdate(query);
								updatePassbook(sAc_no,sBalance,rAc_no,rBalance);
							}
							else
							{
								System.out.println("Insufficient Balance...");
								System.out.print("You have "+sBalance+"rs only...");
							}
						}
						else
						{
							System.out.println("Invalid pin...");
						}
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
						e.printStackTrace();
					}				
					System.out.print("\nTransaction Successfull...");
				}
				else
				{
					System.out.println("Invalid UPI id...");
				}
				break;
			case 4:
				System.out.print("Exited...");
				return;
			default:
				System.out.println("Invalid input...");
			}
		}
		else
		{
			System.out.println("Invalid account number...");
		}
	}
	private static void updatePassbook(int sAc_no2, int sBalance2, int rAc_no2, int rBalance2) {
		 try {
			 sPassbook=new File("E:\\bank project\\src\\customer passbooks\\"+sAc_no+".txt");
			 rPassbook=new File("E:\\bank project\\src\\customer passbooks\\"+rAc_no+".txt");
			 FileWriter swriter=new FileWriter(sPassbook,true);
			 swriter.append("\n"+LocalDate.now()+"\t"+amt+"rs debited"+"\t\t"+sBalance);
			 FileWriter rwriter=new FileWriter(rPassbook,true);
			 rwriter.append("\n"+LocalDate.now()+"\t"+amt+"rs credited"+"\t\t"+rBalance);
			 swriter.close();
			 rwriter.close();
		 }
		 catch(Exception e)
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		
	}
}
	