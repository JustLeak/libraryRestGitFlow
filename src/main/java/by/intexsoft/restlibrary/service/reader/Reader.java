package by.intexsoft.restlibrary.service.reader;

import by.intexsoft.restlibrary.service.reader.api.IReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Reader implements IReader {
    private final Scanner scanner;

    public Reader(InputStream in) {
        InputStreamReader reader = new InputStreamReader(in);
        scanner = new Scanner(reader);
    }

    @Override
    public Map<Integer, String> readAll() {
        Map<Integer, String> result = new HashMap<>();
        for (Integer line = 0; scanner.hasNextLine(); line++) {
            result.put(line, scanner.nextLine());
        }
        return result;
    }

    @Override
    public void close() {
        scanner.close();
    }
}
