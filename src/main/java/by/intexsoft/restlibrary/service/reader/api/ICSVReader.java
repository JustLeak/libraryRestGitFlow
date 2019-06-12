package by.intexsoft.restlibrary.service.reader.api;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ICSVReader extends Closeable {

    Map<Long, List<String>> readAllRecordsWithHeader(List<String> header) throws IOException;
}
