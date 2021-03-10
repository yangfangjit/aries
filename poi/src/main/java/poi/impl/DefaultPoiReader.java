package poi.impl;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import poi.PoiReader;
import poi.support.ExcelReadWriteHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author yexiaoyi
 * @date 2018/6/2
 */
public class DefaultPoiReader implements PoiReader {

    @Override
    public Workbook readFile(String file) throws FileNotFoundException {
        return readFile(new File(file));
    }

    @Override
    public Workbook readFile(File file) throws FileNotFoundException {
        try {
            return readFile(new FileInputStream(file));
        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }

    @Override
    public Workbook readFile(InputStream ins) throws IOException {
        try {
            return WorkbookFactory.create(ins);
        }  finally {
            ins.close();
        }
    }

    @Override
    public List<Sheet> readWorkbook(Workbook workbook) {
        // 兼容poi老版本 Workbook 没有继承 Iterable
        int sheetSize = workbook.getNumberOfSheets();
        List<Sheet> sheets = new ArrayList<>(sheetSize);
        for (int i = 0; i < sheetSize; ++i) {
            sheets.add(workbook.getSheetAt(i));
        }
        return sheets;
    }

    @Override
    public List<Row> readSheet(Sheet sheet) {
        // 兼容poi老版本 Sheet 没有继承 Iterable
        int rowSize = sheet.getPhysicalNumberOfRows();
        List<Row> rows = new ArrayList<>(rowSize);
        for (int i = 0; i < rowSize; ++i) {
            rows.add(sheet.getRow(i));
        }
        return rows;
    }

    @Override
    public <T> List<T> loadRows(Class<T> t, List<Row> rows) {
        Objects.requireNonNull(t, "clazz must not null");
        Objects.requireNonNull(rows, "rows must not null");
        List<T> targets = Lists.newArrayListWithCapacity(rows.size());

        try {
            for (Row row : rows) {
                T target = t.newInstance();
                ExcelReadWriteHelper.read(target, row);
                targets.add(target);
            }
            return targets;
        } catch (InstantiationException | IllegalAccessException e ) {
            throw new RuntimeException(e);
        }
    }

}