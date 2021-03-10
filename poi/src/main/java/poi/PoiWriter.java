package poi;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author yexiaoyi
 * @date 2018/6/2
 */
public interface PoiWriter {

    /**
     * workbook内容写入文件系统
     *
     * @param workbook workbook
     * @param file 文件
     * @throws IOException
     */
    void writeExcel(Workbook workbook, File file) throws IOException;

    /**
     * workbook 内容写入 文件系统
     *
     * @param workbook workbook
     * @param file 文件名
     * @throws IOException
     */
    void writeExcel(Workbook workbook, String file) throws IOException;

    /**
     * workbook 内容写入 IO
     *
     * @param workbook workbook
     * @param ops 输出流
     * @throws IOException IOException
     */
    void writeExcel(Workbook workbook, OutputStream ops) throws IOException;

    /**
     * @return a workbook entity
     */
    Workbook createWorkbook();

}

