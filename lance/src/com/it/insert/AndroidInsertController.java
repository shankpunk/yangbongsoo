package com.it.insert;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import com.it.member.MemberDao;
import com.it.member.MemberVo;

@Controller
public class AndroidInsertController {
	
	private static MemberDao memberDaoImpl;
	
	public AndroidInsertController(MemberDao memberDaoImpl){
		this.memberDaoImpl = memberDaoImpl;
	}
	
	public static String androidInsertData(String id, String pass, String name, String phone_number, String gender){
		
		MemberVo vo = new MemberVo();
		vo.setId(id);
		vo.setPass(pass);
		vo.setName(name);
		vo.setPhone_number(phone_number);
		vo.setGender(gender);
		
		String dbId = memberDaoImpl.selectIdCheck(vo.getId());
		
		if(dbId == null){ //���� ������ ���̵� �����ϴϱ� ��� �������� 
			memberDaoImpl.insertMember(vo);//��� �����ϰ� 
			
			//���⼭ ���κ� ������ ����������� 
			String path = "D:\\here\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\lance\\gallery";
			File parent = new File(path+"/"+id); //���κ��� ������ ����� 
			if(!parent.exists()){ //���丮�� ������ 
				parent.mkdir(); //���丮 ����� 
			}	
			System.out.println("(�ȵ���̵�) ���̵� ��밡��");
			return "possi";
		}
		else{
			System.out.println("(�ȵ���̵�) ���̵� �ߺ�");
			return "dupli";
		}
	}
	
	public static String androidIdCheck(String id){

		String dbId = memberDaoImpl.selectIdCheck(id);
		
		if(dbId == null){ 
			System.out.println("(�ȵ���̵�) ���̵� ��밡��");
			return "possicheck";
		}
		else{
			System.out.println("(�ȵ���̵�) ���̵� �ߺ�");
			return "duplicheck";
		}
	}
	
}
