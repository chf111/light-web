package up.light.web.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

/**
 * 分页拦截器，只拦截DAO中方法名为selectXXXByPage的方法，
 * 在调用查询前需先调用{@link PageHelper#setPage(int, int)}设置分页信息
 * 
 * ！！！注意：只使用与MySQL！！！
 */
@Intercepts({
		@Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class, Integer.class }) })
public class PageInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler handler = (StatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler = SystemMetaObject.forObject(handler);
		MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
		String methodName = mappedStatement.getId();

		// 只处理方法名为selectXXXByPage的方法
		if (methodName.matches(".+\\.select.+ByPage$")) {
			BoundSql boundSql = handler.getBoundSql();
			StringBuilder sql = deleteSemicolon(boundSql.getSql());
			Page page = PageHelper.getPage();

			// page不为null时才进行分页
			if (page != null) {
				setPageTotalRecord(page, sql, (Connection) invocation.getArgs()[0],
						(ParameterHandler) metaStatementHandler.getValue("delegate.parameterHandler"));
				metaStatementHandler.setValue("delegate.boundSql.sql", appendLimit(sql, page));
				PageHelper.deleteLocal();
			}
		}

		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	@Override
	public void setProperties(Properties properties) {
	}

	/*
	 * 删除SQL末尾分号
	 */
	private StringBuilder deleteSemicolon(String sql) {
		StringBuilder sb = new StringBuilder(sql);
		if (sql.endsWith(";"))
			sb.deleteCharAt(sb.length() - 1);
		return sb;
	}

	/*
	 * 查询记录总数
	 */
	private void setPageTotalRecord(Page page, StringBuilder sql, Connection conn, ParameterHandler ph)
			throws SQLException {
		String countSql = "select count(0) from (" + sql + ") c";
		PreparedStatement countStatement = null;
		ResultSet rs = null;
		try {
			countStatement = conn.prepareStatement(countSql);
			ph.setParameters(countStatement);
			rs = countStatement.executeQuery();
			if (rs.next()) {
				page.setTotalRecord(rs.getInt(1));
			}
		} finally {
			if (rs != null)
				rs.close();
			if (countStatement != null)
				countStatement.close();
		}
	}

	/*
	 * 在SQL末尾追加limit子句
	 */
	private String appendLimit(StringBuilder sql, Page page) {
		sql.append(" limit ").append(page.getStart()).append(", ").append(page.getPageSize());
		return sql.toString();
	}

}
