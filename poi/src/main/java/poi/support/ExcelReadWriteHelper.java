package poi.support;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import poi.annotation.CellMapping;
import poi.model.ExcelMappingFactory;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Objects;

/**
 * @author 幽明
 * @serial 2018/11/5
 */
public class ExcelReadWriteHelper {

    /**
     *
     *
     * @param target
     * @param row
     * @param <T>
     * @throws IllegalAccessException
     */
    public static <T> void read(T target, Row row) throws IllegalAccessException {
        Field[] fields = target.getClass().getDeclaredFields();
        for  (int i = 0; i < fields.length && i < row.getRowNum(); i++) {
            read(target, fields[i], row.getCell(i));
        }
    }

    /**
     *
     *
     * @param target
     * @param field
     * @param cell
     * @param <T>
     * @throws IllegalAccessException
     */
    public static <T> void read(T target, Field field, Cell cell) throws IllegalAccessException {
        if (Objects.isNull(cell)) {
            return;
        }

        CellMapping cellMapping = ExcelMappingFactory.getExcelMapping(target.getClass()).getCellMapping(field.getName());
        if (Objects.isNull(cellMapping)) {
            return;
        }

        field.setAccessible(true);
        Class type = field.getType();
        if (type.equals(Integer.TYPE)) {
            field.set(target, cell.getNumericCellValue());
        } if (type.equals(Long.TYPE)) {
            field.set(target, cell.getNumericCellValue());
        } else if (type.equals(String.class)) {
            field.set(target, cell.getStringCellValue());
        } else if (type.equals(Date.class)) {
            field.set(target, cell.getDateCellValue());
        }  else if (type.equals(Boolean.class)) {
            field.set(target, cell.getBooleanCellValue());
        }
    }

    /**
     *
     *
     * @param data
     * @param field
     * @param cell
     * @param <T>
     * @throws IllegalAccessException
     */
    public static <T> void write(T data, Field field, Cell cell) throws IllegalAccessException {
        CellMapping cellMapping = ExcelMappingFactory.getExcelMapping(data.getClass()).getCellMapping(field.getName());
        if (Objects.isNull(cellMapping)) {
            return;
        }

        Class type = field.getType();
        if (type.equals(Integer.TYPE)) {
            cell.setCellValue(field.getInt(data));
        } if (type.equals(Long.TYPE)) {
            cell.setCellValue(field.getLong(data));
        } else if (type.equals(String.class)) {
            cell.setCellValue(field.getByte(data));
        } else if (type.equals(Date.class)) {
            cell.setCellValue(field.getByte(data));
        }  else if (type.equals(Boolean.class)) {
            cell.setCellValue(field.getBoolean(data));
        }
    }

    private ExcelReadWriteHelper() {

    }

}
