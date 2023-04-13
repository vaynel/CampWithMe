package com.trillon.camp.util.file;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository {

    @Insert("insert into campinghome_imagefile(origin_file_name, rename_file_name, save_path, gn_idx)"
            + " values(#{originFileName},#{renameFileName},#{savePath}, #{gnIdx})")
    @Options(useGeneratedKeys = true, keyProperty = "fileIdx")
    void insertFileInfo(FileInfo fileInfo);

    @Select("select * from campinghome_imagefile where gn_idx = #{bdIdx}")
    List<FileInfo> selectFileWithGroup(int bdIdx);

    //@Select("SELECT * FROM test2 where (gn_idx=) = (select min(file_idx) FROM test2);")
    List<FileInfo> selectFirstFile();

    //@Delete("delete * from test2 where rename_file_name = #{renameFileName}")
    
    
    
    
    // comeWithMe와 관련된 것들
    @Insert("insert into file_info(origin_file_name, rename_file_name, save_path, groupName, group_idx,bd_idx)"
			+ " values(#{originFileName},#{renameFileName},#{savePath},#{groupName},#{groupIdx},#{bdIdx})")
	void insertFileInfoFromComeWithMe(FileInfo fileInfo);
	
	@Select("select * from file_info where is_del = 0 and fl_idx=#{flIdx}")
	FileInfo selectFileInfo(String flIdx);

	@Select("select * from file_info where is_del = 0 and bd_idx = #{bdIdx}")
	List<FileInfo> selectFileWithGroupFromComeWithMe(int bdIdx);
}
