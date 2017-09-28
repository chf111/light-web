package up.light.web.dao;

import java.util.List;

import up.light.web.entity.ModuleDO;

public interface IModuleDao {

	int insertModule(ModuleDO module);

	int updateModule(ModuleDO module);

	int deleteModule(int mid);

	List<ModuleDO> selectModulesByProject(int pid);

	List<ModuleDO> selectModulesByDataSet(int dsid);

	List<String> selectColumns(int mid);

}
