package com.slocumboy.webcrawler;

import java.io.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class AbstractTest {

    public String readResourceFile(String fileName) throws IOException {
        InputStream input = PageParserTest.class.getClassLoader().
                getResourceAsStream(fileName);
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

    public String readFile(String fileName) throws IOException {
        InputStream input = new BufferedInputStream(new FileInputStream(fileName));

        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

    protected String getPageContent(String directory, int pageNumber) throws IOException {
        String fileName = directory + File.separator + "page_" + pageNumber + ".txt";

        return readFile(fileName);
    }

    protected void assertPageContent(String pageContent, String expectedPageContent, int pageNumber) {

        String expectedFullPageContent = createdExpectedPage(expectedPageContent, pageNumber);

        assertEquals(expectedFullPageContent, pageContent);
    }

    private String createdExpectedPage(String expectedPageContent, int pageNumber) {
        return "@Page_" + pageNumber + "\n\n" + expectedPageContent + "\n";
    }

    protected void assertFullContent(String directory, String expectedFullContent) throws IOException {
        String fileName = directory + File.separator + "full.txt";

        String fullContent = readFile(fileName);

        assertEquals(expectedFullContent, fullContent);
    }
}
