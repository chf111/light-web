package up.light.web.dao;

import java.util.List;

import up.light.web.entity.FlowDO;

public interface IFlowDao {

	int insertFlow(FlowDO flow);

	// TODO implement update

	int deleteFlow(int fid);

	FlowDO selectFlowByModule(int mid);

	List<FlowDO> selectFlowByPage();

}
