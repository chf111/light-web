package up.light.web.http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 存储所有测试执行过程中创建的线程池
 */
public abstract class ThreadPoolHolder {
	private static final Map<Integer, ThreadPoolExecutor> threadPools = new ConcurrentHashMap<>(64);
	private static final int MAX_TASK_NUM = 5;

	public static ThreadPoolExecutor getThreadPool(int testId) {
		return threadPools.get(testId);
	}

	public static void setThreadPool(int testId, ThreadPoolExecutor threadPool) {
		threadPools.put(testId, threadPool);
	}

	public static boolean isFull() {
		return threadPools.size() >= MAX_TASK_NUM;
	}

	public static void remove(int testId) {
		ThreadPoolExecutor exe = threadPools.get(testId);
		if (exe != null)
			exe.shutdown();
		threadPools.remove(testId);
	}

}
