package up.light.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.ResultHandler;

import up.light.web.entity.RowDO;

public interface IRowDao {

	int insertRow(RowDO row);

	int updateRow(RowDO row);

	int deleteRow(@Param("roid") int roid, @Param("mid") int mid);

	List<RowDO> selectRowByPage(int mid);

	void forEachRow(int mid, ResultHandler<RowDO> handler);

}
