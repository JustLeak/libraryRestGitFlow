package by.intexsoft.restlibrary.service.reader.api;

import java.util.List;
import java.util.Map;

public interface IExcelReader {

    Map<Integer, List<String>> readSheet(Integer sheetNum);

    List<String> readRow(Integer sheetNumber, Integer rowNum);
}
