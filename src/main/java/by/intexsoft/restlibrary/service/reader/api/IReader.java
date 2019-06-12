package by.intexsoft.restlibrary.service.reader.api;

import java.io.Closeable;
import java.util.Map;

public interface IReader extends Closeable {

    Map<Integer, String> readAll();
}
