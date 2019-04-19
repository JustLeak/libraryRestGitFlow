package by.intexsoft.restlibrary.service.loader;

import by.intexsoft.restlibrary.service.reader.ExcelReader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

public class XLSBookLoader extends ExcelBookLoader {
    public XLSBookLoader(InputStream inputStream) throws IOException {
        super();
        excelReader = new ExcelReader(new HSSFWorkbook(inputStream));
    }
}
