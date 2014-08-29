package com.it.member;

import java.util.List;

public interface MemberDao {
	public abstract int insertMember(MemberVo vo); 
	public abstract String selectMember(String id);
	public abstract String selectIdCheck(String id);
	public abstract List<MemberVo>selectAllMember();
}
