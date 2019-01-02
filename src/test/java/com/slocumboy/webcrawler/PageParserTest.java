package com.slocumboy.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PageParserTest {

    @Test
    public void testFirstPage() throws IOException {
        InputStream input = PageParserTest.class.getClassLoader().
                getResourceAsStream("sampleFirstPage.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

        String expectePage1Text = readFile("expectedTextFirstPage.txt");

        PageParser pageParser = new PageParser(doc);

        assertEquals(expectePage1Text, pageParser.getPageText());

        assertEquals("/cgi/ssd?id=loc.ark%3A%2F13960%2Ft9474jc00;page=ssd;view=plaintext;seq=6",
                pageParser.getNextPage());

    }

    @Test
    public void testMiddlePage() throws IOException {
        InputStream input = PageParserTest.class.getClassLoader().
                getResourceAsStream("sampleMiddlePage.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

        String expectePage1Text = readFile("expectedTextMiddlePage.txt");

        PageParser pageParser = new PageParser(doc);

        assertEquals(expectePage1Text, pageParser.getPageText());

        assertEquals("/cgi/ssd?id=loc.ark%3A%2F13960%2Ft9474jc00;page=ssd;view=plaintext;seq=20",
                pageParser.getNextPage());

    }

    @Test
    public void testLastPage() throws IOException {
        InputStream input = PageParserTest.class.getClassLoader().
                getResourceAsStream("sampleLastPage.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

        String expectePage1Text = readFile("expectedTextLastPage.txt");

        PageParser pageParser = new PageParser(doc);

        assertEquals(expectePage1Text, pageParser.getPageText());

        assertNull(pageParser.getNextPage());

    }

    public String readFile(String fileName) throws IOException {
        InputStream input = PageParserTest.class.getClassLoader().
                getResourceAsStream(fileName);
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }
}
