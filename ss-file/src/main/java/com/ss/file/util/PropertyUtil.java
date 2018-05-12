package com.ss.file.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p>属性配置类</p>
 * <p>根据属性key，获取属性value</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * @author liuwz
 * @date 2016年07月8日
 */
public class PropertyUtil {	
	/**
	 * 系统上下文名称
	 */
	public static String SYSTEM_CONTEXT = "system_context";
	
	/**
	 * 用户登录cookie名称
	 */
	public static String COOKIE_NAME = "cookie_name";
	
	/**
	 * 根据属性key，获取属性value
	 */
	public static String getProperties(String param) {
		Properties p = new Properties();
		try {
			InputStream in = PropertyUtil.class.getResourceAsStream("resource/resource.properties");
			p.load(in);
			in.close();
			return p.getProperty(param) != null ? p.getProperty(param) : "";
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static String loadProperties(String name) throws IOException {
        InputStream input = null;
        try {
            input = Class.forName("com.ss.file.util.PropertyUtil").getResourceAsStream("/resource/resource.properties");

            //also can be this way:
            //input = this.getClass().getResourceAsStream("/resources/config.properties");
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return printProperties(input, name);
    }
	
	private static String printProperties(InputStream input, String name) throws IOException {
        Properties properties = new Properties();
        properties.load(input);
        return properties.getProperty(name);
    }

	public static void main(String[] args) {
		System.out.println(PropertyUtil.getProperties("mid"));
	}
}