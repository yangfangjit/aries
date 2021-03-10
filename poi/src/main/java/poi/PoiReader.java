package poi;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author yexiaoyi
 * @date 2018/6/2
 */
public interface PoiReader {

    /**
     * 读取excel
     *
     * @param file 文件
     * @return workbook
     * @throws FileNotFoundException 异常
     */
    Workbook readFile(String file) throws FileNotFoundException;

    /**
     * 读取excel
     *
     * @param file 文件
     * @return workbook
     * @throws FileNotFoundException 异常
     */
    Workbook readFile(File file) throws FileNotFoundException;

    /**
     * 读取excel
     *
     * @param ips 文件输入流
     * @return workbook
     * @throws IOException 异常
     */
    Workbook readFile(InputStream ips) throws IOException;

    /**
     * 读取workbook
     *
     * @param workbook workbook
     * @return sheet list
     * @throws IOException 异常
     */
    List<Sheet> readWorkbook(Workbook workbook) throws IOException;

    /**
     * 读取sheet
     *
     * @param sheet sheet
     * @return row list
     * @throws IOException 异常
     */
    List<Row> readSheet(Sheet sheet) throws IOException;

    /**
     * 加载rows 并返回对应数据
     *
     * @param <T>  返回值类型
     * @param t    返回值类型
     * @param rows 行值
     * @return 行值转换后对象
     */
    <T> List<T> loadRows(Class<T> t, List<Row> rows);

}

