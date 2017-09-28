package up.light.web.common;

public abstract class ConvertUtil {

	public static int parseIntOrDefault(String str, int defaultVal) {
		return str == null ? defaultVal : Integer.valueOf(str);
	}

}
