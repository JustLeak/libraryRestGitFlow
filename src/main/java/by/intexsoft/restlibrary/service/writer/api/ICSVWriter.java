package by.intexsoft.restlibrary.service.writer.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public interface ICSVWriter {

    InputStream toInputStream(String[] header, Collection<Collection<String>> lines) throws IOException;
}
