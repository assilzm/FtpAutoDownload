package utils

import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.log4j.PropertyConfigurator

/**
 * Log类
 * User: WeiWei
 * Date: 12-10-25
 * Time: 下午5:35
 */
class LogUtils {
    final static String EnvName = "level"

    static Logger getLogger(String className) {
        Class.forName(className)
        config()
        def logger = Logger.getLogger(className)
        setEnvironmentLevelToLevel(logger)
        return logger
    }

	static Logger getLogger(Class className) {
		config()
		def logger = Logger.getLogger(className)
		setEnvironmentLevelToLevel(logger)
		return logger
	}

    private static void setEnvironmentLevelToLevel(Logger logger) {
        def level = System.getenv(EnvName)
        if (level != null) {
            logger.setLevel(Level.toLevel(level))
        } else {
            logger.setLevel(Level.DEBUG)
        }
    }

    private static void config() {
        Properties pro = new Properties();
        pro.put("log4j.rootLogger", "info,ConsoleLog")
        pro.put("log4j.appender.ConsoleLog", "org.apache.log4j.ConsoleAppender")
        pro.put("log4j.appender.ConsoleLog.Threshold", "debug")
        pro.put("log4j.appender.ConsoleLog.layout", "org.apache.log4j.PatternLayout")
        pro.put("log4j.appender.ConsoleLog.layout.ConversionPattern", "[%5p] %d{yyyy-MM-dd HH:mm:ss} %c - %m%n")
        PropertyConfigurator.configure(pro)
    }

}