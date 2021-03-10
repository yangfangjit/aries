package poi;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import poi.impl.DefaultPoiReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * PoiReader代理类
 * PoiReader内部实现默认采用DefaultPoiReader
 *
 * @author 幽明
 * @serial 2018/8/27
 */
public class PoiReaderProxy implements PoiReader {

    private PoiReader poiReader;

    private PoiReaderProxy() {
        poiReader = new DefaultPoiReader();
    }

    private PoiReaderProxy(PoiReader poiReader) {
        this.poiReader = poiReader;
    }

    public static PoiReaderProxy getInstance() {
        return new PoiReaderProxy();
    }

    public static PoiReaderProxy getInstance(PoiReader poiReader) {
        return new PoiReaderProxy(poiReader);
    }

    @Override
    public Workbook readFile(String file) throws FileNotFoundException {
        return poiReader.readFile(file);
    }

    @Override
    public Workbook readFile(File file) throws FileNotFoundException {
        return poiReader.readFile(file);
    }

    @Override
    public Workbook readFile(InputStream ips) throws IOException {
        return poiReader.readFile(ips);
    }

    @Override
    public List<Sheet> readWorkbook(Workbook workbook) throws IOException {
        return poiReader.readWorkbook(workbook);
    }

    @Override
    public List<Row> readSheet(Sheet sheet) throws IOException {
        return poiReader.readSheet(sheet);
    }

    @Override
    public <T> List<T> loadRows(Class<T> t, List<Row> rows) {
        return poiReader.loadRows(t, rows);
    }

}
