package com.trillon.camp.common.file;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


@Repository
public interface FileRepositoryYG {

	@Insert("insert into file_info(origin_file_name, rename_file_name, save_path, groupName, group_idx,bd_idx)"
			+ " values(#{originFileName},#{renameFileName},#{savePath},#{groupName},#{groupIdx},#{bdIdx})")
	void insertFileInfo(FileInfo fileInfo);
	
	@Select("select * from file_info where is_del = 0 and fl_idx=#{flIdx}")
	FileInfo selectFileInfo(String flIdx);

	@Select("select * from file_info where is_del = 0 and bd_idx = #{bdIdx}")
	List<FileInfo> selectFileWithGroup(int bdIdx);
	
	
}
