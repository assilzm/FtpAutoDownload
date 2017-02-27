package utils

import org.apache.log4j.Logger
import static utils.StringUtils.createHttpString

/**
 * 执行过程参数仓库类
 * User: WangTong<br>
 * Date: 13-6-4<br>
 * Time: 上午9:40<br>
 * To change this template use File | Settings | File Templates.<br>
 */
class TestContentManager {
    private final static Logger logger = LogUtils.getLogger(TestContentManager)

    static String APP_ADDRESS_VALUE = null
    final static String APP_ADDRESS = "host"

    /**
     * 全局属性的HashMap
     */
    private static Map<String, Object> ScenarioProperty = new HashMap()

    /**
     * 清空缓存数据
     */
    static void clearScenarioProperty() {
        ScenarioProperty.clear()
        ScenarioProperty.put(APP_ADDRESS, APP_ADDRESS_VALUE)
    }

    /**
     * 从map中添加全局属性键值对
     * @param name 键值对
     */
    static void setScenarioPropertyByMap(Map<String,Object> map) {
        map.each {String key,Object value->
            setScenarioProperty(key,value)
            
        }
    }

    /**
     * 添加全局属性键值对,key不区分大小写
     * @param name 全局属性名称
     * @param obj 全局属性值
     */
    static void setScenarioProperty(String key, Object value) {
        ScenarioProperty.put(key.toUpperCase(), value)
    }

    /**
     * 设置属性值为给定值的属性内容为null
     * @param defaultValue 属性值内容
     */
    static void setScenarioDefaultPropertyWithNull(Object defaultValue) {
        ScenarioProperty.keySet().each {
            if (ScenarioProperty.get(it) == defaultValue)
                ScenarioProperty.put(it, null)
        }
    }

    /**
     * 获取指定名称的全局属性的值,key不区分大小写<br>
     *     appAddress:应用程序的访问地址<br>
     *     remoteAddress:远程浏览器的地址
     * @param name 全局属性名称
     * @return the value of the key
     */
    static Object getScenarioProperty(String key) {
        init()
        ScenarioProperty.get(key.toUpperCase())
    }

    /**
     * 获取指定名称的全局属性的字符串值,key不区分大小写<br>
     *     appAddress:应用程序的访问地址<br>
     *     remoteAddress:远程浏览器的地址
     * @param name 全局属性名称
     * @return the value of the key
     */
    static String getScenarioPropertyToString(String key) {
        init()
        String value=ScenarioProperty.get(key.toUpperCase())
        return value
    }

    /**
     * 初始化appAddress和remoteAddress
     */
    static void init() {
        //只在初始状态时获取一次环境变量
        if (!ScenarioProperty.get(APP_ADDRESS) ) {
            APP_ADDRESS_VALUE = System.getenv(APP_ADDRESS)
            if (!APP_ADDRESS_VALUE) {
                APP_ADDRESS_VALUE = System.getProperty(APP_ADDRESS)
            }
            if (!APP_ADDRESS_VALUE) {
                APP_ADDRESS_VALUE = "http://127.0.0.1"
            }
        }
        ScenarioProperty.put(APP_ADDRESS.toUpperCase(), createHttpString(APP_ADDRESS_VALUE))
    }




}
