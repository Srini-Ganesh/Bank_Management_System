import java.util.*;
import java.io.*;
public class Bank {
	static String Branch="Srirangam";
	static File directory;
	static Set<Integer> Ac_nos=new HashSet<Integer>();
	static void loadAc_nos()
	{
		directory=new File("E:\\bank project\\src\\customer passbooks");
		List<String> nos=Arrays.asList(directory.list());
		for(String n:nos)
		{
			Ac_nos.add(Integer.parseInt(n.substring(0,n.indexOf('.'))));
		}
	}
}
