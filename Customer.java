import java.io.File;
class Customer {
	int Ac_no;
	String Name;
	String Mobile_no;
	File Passbook;
	public Customer(int Ac_no,String Name,String Mobile_no,File Passbook)
	{
		this.Ac_no=Ac_no;
		this.Name=Name;
		this.Mobile_no=Mobile_no;
		this.Passbook=Passbook;
	}
}
