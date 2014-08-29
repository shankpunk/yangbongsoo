import java.util.ArrayList;
import java.util.Collections;

public class tester {
	
	static ArrayList<String> union_Phone_List_Name = new ArrayList<>();
	static ArrayList<String> union_Phone_List_Name2 = new ArrayList<String>();
	static String[] array2 ;//= new String[1000];
	
	public static void main(String[] args) {

		union_Phone_List_Name.add("¾çºÀ¼ö,01011111111");
		union_Phone_List_Name.add("°­¼öÁø,01022222222");
		union_Phone_List_Name.add("°ûºÀ¼ö,01033333333");
		union_Phone_List_Name.add("±è¾çºÀ,01044444444");
		union_Phone_List_Name.add("¹ÚºÀ¼ö,01055555555");
		union_Phone_List_Name.add("ÇÏºÀ¼ö,01066666666");
		union_Phone_List_Name.add("ÇÏÇÏ¼ö,01077777777");
		
		Collections.sort(union_Phone_List_Name);
		
		for(int i=0; i<union_Phone_List_Name.size();i++){
			array2 = union_Phone_List_Name.get(i).split(",");
			
			union_Phone_List_Name2.add(array2[0]);
			union_Phone_List_Name2.add(array2[1]);
			
		}
		
		
		//array2= union_Phone_List_Name.get(0).split(",");
		//union_Phone_List_Name2.add(0, array2[0]);
		//union_Phone_List_Name2.add(1, array2[1]);

		for(int i=0; i<union_Phone_List_Name2.size();i++){	
			System.out.println(union_Phone_List_Name2.get(i));
		}
	}

}
