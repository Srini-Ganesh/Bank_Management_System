import java.io.*;
import java.sql.*;
import java.util.*;
public class Officer extends Bank{
	static Scanner read=new Scanner(System.in);
	public static void main(String[] args) {
		loadAc_nos();
		System.out.print("1.Create new account\n2.Remove existing account\nSelect your choice: ");
		int choice=read.nextInt();
		switch(choice)
		{
		case 1:
			createAccount();
			System.out.print("\n...Action completed....");
			break;
		case 2:
			removeAccount();
			System.out.print("\n...Action completed....");
			break;
		default:
			System.out.print("invalid input");
			break;
		}
	}
	
	private static void createAccount()
	{
		int Ac_no;
		String Name;
		String Mobile_no;
		File Passbook;
		System.out.print("\nEnter five digit account number: ");
		Ac_no=read.nextInt();
		read.nextLine();
		if(Ac_nos.contains(Ac_no))
		{
			Random random=new Random();
			int preference=random.nextInt(90000)+10000; //random number 0-89999 
			System.out.println("Account number "+Ac_no+" already exist.\nNext time try with this number "+"'"+preference+"'");
			return;
		}
		System.out.print("Enter customer name: ");
		Name=read.nextLine();
		System.out.print("Enter ten digit mobile number: ");
		Mobile_no=read.nextLine();
		System.out.println("\n..........Check Details...........");
		System.out.println("Account number: "+Ac_no+"\nName: "+Name+"\nMobile number: "+Mobile_no);
		System.out.print("press \"1\" to create account:");
		if(read.nextInt()==1)
		{
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","12345");
				Statement statement=con.createStatement();
				String query="insert into bankaccounts(Ac_no,UPI_id,Mobile_no) values("+Ac_no+",'"+Mobile_no+"@IO'"+",'"+Mobile_no+"');";
				statement.executeUpdate(query);
				statement.close();
				con.close();
//				System.out.println("inserted successfully..");
				Passbook=new File(String.format("E:\\bank project\\src\\customer passbooks\\"+Ac_no+".txt"));//file creation
				Passbook.createNewFile();
				Customer c=new Customer(Ac_no,Name,Mobile_no,Passbook);
				FileWriter writer=new FileWriter(Passbook);
				writer.write("\t\t.........IO BANK.........");
				writer.write("\n\nAccount number: "+c.Ac_no+"\t\t\tBranch: "+Branch+"\nName: "+c.Name+"\nMobile number: "+c.Mobile_no);//writing file
				writer.append("\n....................................................");
				writer.append("\nDate      \tTransaction    \t\tBalance");
				writer.append("\n....................................................");
				writer.close();
				System.out.println("\nAccount number: "+c.Ac_no+"\nName: "+c.Name+"\nMobile number: "+c.Mobile_no);
				System.out.print("...Account Created Successfully...");
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		else
		{
			return;
		}
	}
	private static void removeAccount()
	{
		int Ac_no;
		System.out.print("\nEnter five digit account number to delete: ");
		Ac_no=read.nextInt();
		if(Ac_nos.contains(Ac_no))
		{
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","12345");
				Statement statement=con.createStatement();
				String query="delete from bankaccounts where Ac_no="+Ac_no+";";
				statement.executeUpdate(query);
				statement.close();
				con.close();
				File toremove=new File(String.format("E:\\bank project\\src\\customer passbooks\\"+Ac_no+".txt"));
				toremove.delete();
				System.out.println("...Account Deleted Successfully...");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Account number not exists...");
		}
	}
}
