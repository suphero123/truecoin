package org.truechain;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.loadconfig.configuration.ConfigurableProcessor;
import com.loadconfig.configuration.Property;

/**
 * 配置
 * @author ln
 *
 */
public final class Configure {

	private static Logger log = LoggerFactory.getLogger(Configure.class);
	
	private static final String CONFIG_FILE = "config.conf";
	
	/**
	 * 数据存储目录
	 */
	@Property(key="data.dir",defaultValue="./data")
	public static String DATA_DIR;
	/**
	 * 账户存储目录
	 */
	public static String DATA_ACCOUNT;
	/**
	 * 区块存储目录
	 */
	public static String DATA_BLOCK;
	/**
	 * 区块状态存储目录
	 */
	public static String DATA_CHAINSTATE;
	/**
	 * 与帐户有关的交易存储目录
	 */
	public static String DATA_TRANSACTION;
	
	static {
		load();
		DATA_ACCOUNT = DATA_DIR+File.separator+"account";
		DATA_BLOCK = DATA_DIR+File.separator+"block";
		DATA_CHAINSTATE = DATA_DIR+File.separator+"chainstate";
		DATA_TRANSACTION = DATA_DIR+File.separator+"transaction";
	}
	
	public static void load() {

		Properties properties = new Properties();
		InputStream stream = null;

		try {

			ClassLoader loader = Configure.class.getClassLoader();

			stream = loader.getResourceAsStream(CONFIG_FILE);

			properties.load(stream);

			ConfigurableProcessor.process(Configure.class, properties);

			if (log.isDebugEnabled()) {
				log.debug("加载配置文件完毕！");
			}

		} catch (IOException e) {
			log.error("加载配置文件出错，程序将退出！", e);
			System.exit(-1);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
