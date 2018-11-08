package poi.model;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 类与Excel映射关系工厂
 *
 * @author 幽明
 * @serial 2018/11/5
 */
public class ExcelMappingFactory {

    /**
     * 存储类与Excel映射关系
     */
    private static Map<Class, ExcelMapping> mapping = Maps.newConcurrentMap();

    /**
     * 注册类与Excel映射关系
     * 一般用在相应的class中，class提供一个静态方法调用register
     *
     * @param clazz 类
     * @param excelMapping 映射关系
     */
    public static void register(Class clazz, ExcelMapping excelMapping) {
        mapping.put(clazz, excelMapping);
    }

    /**
     * 获取类与Excel映射关系
     *
     * @param clazz 类
     * @return 类与Excel映射关系
     */
    public static ExcelMapping getExcelMapping(Class clazz) {
        return mapping.get(clazz);
    }

    private ExcelMappingFactory() {
    }

}
