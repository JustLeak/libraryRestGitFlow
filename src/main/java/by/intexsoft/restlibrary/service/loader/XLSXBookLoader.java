package by.intexsoft.restlibrary.service.loader;

import by.intexsoft.restlibrary.service.reader.ExcelReader;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

public class XLSXBookLoader extends ExcelBookLoader {
    public XLSXBookLoader(InputStream inputStream) throws IOException {
        super();
        excelReader = new ExcelReader(new XSSFWorkbook(inputStream));
    }
}
