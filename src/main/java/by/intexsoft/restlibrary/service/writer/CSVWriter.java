package by.intexsoft.restlibrary.service.writer;

import by.intexsoft.restlibrary.service.writer.api.ICSVWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.util.Collection;

public class CSVWriter implements ICSVWriter {
    @Override
    public InputStream toInputStream(String[] header, Collection<Collection<String>> lines) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputStreamWriter outWriter = new OutputStreamWriter(out);
        try (CSVPrinter printer = new CSVPrinter(outWriter, CSVFormat.DEFAULT.withHeader(header))) {
            printer.printRecords(lines);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}
