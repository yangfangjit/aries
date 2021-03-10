package poi;

import org.apache.poi.ss.usermodel.Workbook;
import poi.impl.DefaultPoiWriter;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * PoiWriter代理类
 * PoiWriter内部默认实现采用DefaultPoiWriter
 *
 * @author 幽明
 * @serial 2018/8/27
 */
public class PoiWriterProxy implements PoiWriter {

    private PoiWriter poiWriter;

    private PoiWriterProxy() {
        this.poiWriter = new DefaultPoiWriter();
    }

    private PoiWriterProxy(PoiWriter poiWriter) {
        this.poiWriter = poiWriter;
    }

    public static PoiWriterProxy getInstance() {
        return new PoiWriterProxy();
    }

    public static PoiWriterProxy getInstance(PoiWriter poiWriter) {
        return new PoiWriterProxy(poiWriter);
    }

    @Override
    public void writeExcel(Workbook workbook, File file) throws IOException {
        poiWriter.writeExcel(workbook, file);
    }

    @Override
    public void writeExcel(Workbook workbook, String file) throws IOException {
        poiWriter.writeExcel(workbook, file);
    }

    @Override
    public void writeExcel(Workbook workbook, OutputStream ops) throws IOException {
        poiWriter.writeExcel(workbook, ops);
    }

    @Override
    public Workbook createWorkbook() {
        return poiWriter.createWorkbook();
    }

}
