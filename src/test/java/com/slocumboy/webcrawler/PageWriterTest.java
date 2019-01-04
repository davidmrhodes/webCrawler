package com.slocumboy.webcrawler;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.Assert.fail;

public class PageWriterTest extends AbstractTest {

    private String pageTestDir;

    private final String PAGE_1 = "DMR First Page Text\nDavid M. Rhodes";

    private final String PAGE_2 = "DMR Second Page Text\nDavid M. Rhodes";

    private final String PAGE_3 = "DMR Third Page Text\nDavid M. Rhodes";

    @Before
    public void setup() throws Exception {
        pageTestDir = System.getProperty("com.slocumboy.PAGE_TEST_DIR");

        try (Stream<Path> entries = Files.list(Paths.get(pageTestDir))) {
            entries.forEach(entry -> {
                try {
                    Files.delete(entry);
                } catch (IOException e) {
                    fail("Could not clean directory");
                }
            });
        }
    }

    @Test
    public void testPageWrite() throws Exception {

        PageWriter pageWriter = new PageWriter(pageTestDir);
        try (BufferedWriter bufferedWriter = pageWriter.initializeBufferWritter()) {

            pageWriter.writePage(PAGE_1);

            pageWriter.writePage(PAGE_2);

            pageWriter.writePage(PAGE_3);
        }

        String page1 = getPageContent(1);

        assertPageContent(page1, PAGE_1, 1);

        String page2 = getPageContent(2);

        assertPageContent(page2, PAGE_2, 2);

        String page3 = getPageContent(3);

        assertPageContent(page3, PAGE_3, 3);

        assertFullContent(page1 + "\n" + page2 + "\n" + page3);
    }

    private void assertFullContent(String expectedFullContent) throws IOException {
        assertFullContent(pageTestDir, expectedFullContent);
    }

    private String getPageContent(int pageNumber) throws IOException {
       return getPageContent(pageTestDir, pageNumber);
    }
}
