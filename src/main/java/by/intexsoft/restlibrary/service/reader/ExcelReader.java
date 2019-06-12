package by.intexsoft.restlibrary.service.reader;

import by.intexsoft.restlibrary.service.reader.api.IExcelReader;
import org.apache.poi.ss.usermodel.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ExcelReader implements IExcelReader {
    private final Workbook workbook;

    public ExcelReader(Workbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public Map<Integer, List<String>> readSheet(Integer sheetNum) {
        if (sheetNum < 0 || sheetNum >= workbook.getNumberOfSheets()) {
            throw new IllegalArgumentException("Can't read sheet with number = " + sheetNum + " . Illegal sheet index.");
        }
        HashMap<Integer, List<String>> result = new HashMap<>();
        Sheet sheet = workbook.getSheetAt(sheetNum);
        Stream<Row> rowStream = StreamSupport.stream(sheet.spliterator(), true);
        rowStream.forEach(cells -> result.put(cells.getRowNum(), readRow(sheetNum, cells.getRowNum())));
        return result;
    }

    @Override
    public List<String> readRow(Integer sheetNum, Integer rowNum) {
        if (sheetNum < 0 || sheetNum >= workbook.getNumberOfSheets()) {
            throw new IllegalArgumentException("Can't read sheet with number = " + sheetNum + " . Illegal sheet index.");
        }
        Row row = workbook.getSheetAt(sheetNum).getRow(rowNum);
        if (row == null) {
            throw new IllegalArgumentException("Can't read row with number = " + rowNum + " . Illegal row index.");
        }
        Stream<Cell> cellStream = StreamSupport.stream(row.spliterator(), true);
        return cellStream.map(cell -> {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    return cell.getCellFormula();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue().toString();
                    } else {
                        return String.valueOf(cell.getNumericCellValue());
                    }
                case BLANK:
                    return null;
                default:
                    throw new IllegalArgumentException("Illegal CellType value. CellType: " + cell.getCellType() + ".");
            }
        }).collect(Collectors.toList());
    }
}
