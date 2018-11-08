package poi.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import poi.PoiWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author yexiaoyi
 * @date 2018/6/2
 */
public class DefaultPoiWriter implements PoiWriter {

    @Override
    public void writeExcel(Workbook workbook, File file) throws IOException {
        if (file.exists()) {
            //这个逻辑把旧文件删除
            file.delete();
        }
        file.createNewFile();
        writeExcel(workbook, new FileOutputStream(file));
    }

    @Override
    public void writeExcel(Workbook workbook, String file) throws IOException {
        writeExcel(workbook, new File(file));
    }

    @Override
    public void writeExcel(Workbook workbook, OutputStream ops) throws IOException {

    }

    @Override
    public Workbook createWorkbook() {
        return new HSSFWorkbook();
    }

}
