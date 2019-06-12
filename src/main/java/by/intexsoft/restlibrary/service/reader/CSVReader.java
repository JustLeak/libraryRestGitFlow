package by.intexsoft.restlibrary.service.reader;

import by.intexsoft.restlibrary.service.reader.api.ICSVReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.Reader;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReader implements ICSVReader, Closeable {
    private final InputStream in;

    public CSVReader(InputStream in) {
        this.in = in;
    }

    @Override
    public Map<Long, List<String>> readAllRecordsWithHeader(List<String> header) throws IOException {
        Map<Long, List<String>> records = new HashMap<>();
        Reader reader = new InputStreamReader(in);
        CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());
        for (CSVRecord record : parser) {
            List<String> values = new ArrayList<>();
            header.forEach(headerName -> values.add(record.get(headerName)));
            records.put(record.getRecordNumber(), values);
        }
        return records;
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}
