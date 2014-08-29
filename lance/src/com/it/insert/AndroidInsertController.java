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
		
		if(dbId == null){ //내가 선택한 아이디가 가능하니까 디비 저장으로 
			memberDaoImpl.insertMember(vo);//디비에 삽입하고 
			
			//여기서 개인별 폴더를 생성해줘야함 
			String path = "D:\\here\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\lance\\gallery";
			File parent = new File(path+"/"+id); //개인별로 폴더를 만들어 
			if(!parent.exists()){ //디렉토리가 없으면 
				parent.mkdir(); //디렉토리 만들어 
			}	
			System.out.println("(안드로이드) 아이디 사용가능");
			return "possi";
		}
		else{
			System.out.println("(안드로이드) 아이디 중복");
			return "dupli";
		}
	}
	
	public static String androidIdCheck(String id){

		String dbId = memberDaoImpl.selectIdCheck(id);
		
		if(dbId == null){ 
			System.out.println("(안드로이드) 아이디 사용가능");
			return "possicheck";
		}
		else{
			System.out.println("(안드로이드) 아이디 중복");
			return "duplicheck";
		}
	}
	
}
