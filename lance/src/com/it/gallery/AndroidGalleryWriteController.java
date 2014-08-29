package com.it.gallery;

import org.springframework.stereotype.Controller;

@Controller
public class AndroidGalleryWriteController {
	private static GalleryDao galleryDaoImpl; 
	
	public AndroidGalleryWriteController(GalleryDao galleryDaoImpl){
		this.galleryDaoImpl = galleryDaoImpl;
	}
	
	public static void androidGalleryWrite(String id, String uuid, String title, String content){
		
		//���⼭ ��� ���� ���Ѿߵ� !! 
		GalleryVo vo = new GalleryVo();
		
		vo.setWriter(id);
		vo.setTitle(title);
		vo.setContent(content);
		vo.setLink1("/gallery/"+id+"/"+uuid);
		
		galleryDaoImpl.insertOne(vo);
	}
}
