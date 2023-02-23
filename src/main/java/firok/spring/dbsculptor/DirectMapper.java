package firok.spring.dbsculptor;

import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface DirectMapper
{
	@Insert("${sql}")
	int insert(@Param("sql") String sql);

	@Select("${sql}")
	List<Map<String, Object>> select(@Param("sql") String sql);

	@Update("${sql}")
	int update(@Param("sql") String sql);

	@Delete("${sql}")
	int delete(@Param("sql") String sql);
}
