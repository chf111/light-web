package up.light.web.common;

/**
 * 分页工具类，需配合拦截器使用
 */
public abstract class PageHelper {
	private static final ThreadLocal<Page> LOCALS = new ThreadLocal<>();

	public static void setPage(int pageNo, int pageSize) {
		Page p = new Page();
		p.setPageNo(pageNo);
		p.setPageSize(pageSize);
		LOCALS.set(p);
	}

	public static Page getPage() {
		return LOCALS.get();
	}

	static void deleteLocal() {
		LOCALS.set(null);
	}

}
