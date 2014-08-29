<%@page import="java.io.File"%>
<%@page import="com.it.gallery.AndroidGalleryWriteController"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	String dir = application.getRealPath("/gallery");
	int max = 7 * 1024 * 1024 ; //5MB  = 52242880bite 리미트가 7MB 	
	
	MultipartRequest m = new MultipartRequest(request,dir,max,"utf-8",new DefaultFileRenamePolicy());;
	
	String id = m.getParameter("id"); //안드로이드에서 내가 접속한 아이디 값 
	String uuid = m.getParameter("uuid"); //안드로이드에서 내가 보낸 파일 이름 
	String title = m.getParameter("title");//안드로이드에서 내가 보낸 제목 
	String content = m.getParameter("content"); // 안드로이드에서 내가 보낸 내용 
	
	File oldFile = new File(dir+"\\"+uuid); 
	File newFile = new File(dir+"\\"+id);
	oldFile.renameTo(new File(newFile,uuid));//파일을 해당 계정폴더로 이동시켜 !!
	
	  
	AndroidGalleryWriteController.androidGalleryWrite(id,uuid,title,content);  
%>
<html>
<body>

</body>
</html>