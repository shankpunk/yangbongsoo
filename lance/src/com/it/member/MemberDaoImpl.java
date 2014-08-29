package com.it.member;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

public class MemberDaoImpl implements MemberDao{
	private SqlSessionTemplate session;
	
	
	public MemberDaoImpl(SqlSessionTemplate session){
		this.session = session;
	}
	
	
	@Override
	public int insertMember(MemberVo vo) {
		int rst = 0;
		rst = session.insert("com.it.member.insertMember",vo);
		return rst;
	}
	
	@Override
	public String selectMember(String id){
		
		String rst = session.selectOne("com.it.member.selectDBPass", id);
		
		return rst;
		
	}


	@Override
	public String selectIdCheck(String id) {
		
		String rst = session.selectOne("com.it.member.selectIdCheck", id);
		
		return rst;
	}


	@Override
	public List<MemberVo> selectAllMember() {
		
		List<MemberVo> list = session.selectList("com.it.member.selectAllMember");
		return list;
	}

	
}
