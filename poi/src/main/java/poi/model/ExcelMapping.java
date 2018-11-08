package poi.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import poi.annotation.CellMapping;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Excel 与 Java对象的映射
 *
 * @author 幽明
 * @serial 2018/11/4
 */
public class ExcelMapping {

    private List<CellMapping> cellMappings;

    private Map<String, CellMapping> cellMappingMap;

    private Class mappingClazz;

    /**
     * 构造Excel与类的映射
     *
     * @param cellNumOfRow Excel中row对应的cell个数
     */
    public ExcelMapping(int cellNumOfRow, Class mappingClazz) {
        if (cellNumOfRow < 0) {
            throw new IllegalArgumentException("cell个数异常");
        }

        Objects.requireNonNull(mappingClazz, "关联的Java对象不允许为空");

        cellMappings = Lists.newArrayListWithCapacity(cellNumOfRow);
        cellMappingMap = Maps.newHashMapWithExpectedSize(cellNumOfRow);
        this.mappingClazz = mappingClazz;
    }

    /**
     * 根据Excel下标值获取Java Field与Excel cell的映射关系
     *
     * @param index row的下标
     * @return cell与Java POJO field的映射
     */
    public CellMapping getCellMapping(int index) {
        return cellMappings.get(index);
    }

    /**
     * 根据Java对象Field name获取Java Field与Excel cell的映射关系
     *
     * @param fieldName Java对象Field name
     * @return Java Field与Excel cell的映射关系
     */
    public CellMapping getCellMapping(String fieldName) {
        return cellMappingMap.get(fieldName);
    }

    /**
     * 设置Java Field与Excel cell的映射关系
     *
     * @param index row的下标值
     * @param cellMapping cell与Java POJO field的映射
     */
    private void setCellMapping(int index, CellMapping cellMapping) {
        if (index < 0 || index >= cellMappings.size()) {
            throw new IllegalArgumentException("参数异常");
        }

        cellMappings.add(index, cellMapping);
    }

    /**
     * 设置Java Field与Excel cell的映射关系
     *
     * @param fieldName Java对象Field name
     * @param cellMapping cell与Java POJO field的映射
     */
    public void setCellMapping(String fieldName, CellMapping cellMapping) {
        cellMappingMap.put(fieldName, cellMapping);
        setCellMapping(cellMapping.index(), cellMapping);
    }

    /**
     * 获取关联的class
     *
     * @return Class mapped with excel
     */
    public Class getMappingClazz() {
        return mappingClazz;
    }

}
