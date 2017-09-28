package up.light.web.http;

import java.util.concurrent.ThreadPoolExecutor;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import up.light.web.entity.FlowDO;
import up.light.web.entity.RowDO;

/**
 * 根据行数据向线程池中添加任务
 */
public class HttpTaskHandler implements ResultHandler<RowDO> {
	private ThreadPoolExecutor pool;

	public HttpTaskHandler(ThreadPoolExecutor pool) {
		this.pool = pool;
	}

	@Override
	public void handleResult(ResultContext<? extends RowDO> resultContext) {
	}

	public void buildFlow(FlowDO flow) {
		// TODO implement convert flow to http
	}

}
