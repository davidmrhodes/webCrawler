package com.slocumboy.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PageParserTest extends AbstractTest {

    @Test
    public void testFirstPage() throws IOException {
        InputStream input = PageParserTest.class.getClassLoader().
                getResourceAsStream("sampleFirstPage.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

        String expectePage1Text = readResourceFile("expectedTextFirstPage.txt");

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

        String expectePage1Text = readResourceFile("expectedTextMiddlePage.txt");

        PageParser pageParser = new PageParser(doc);

        assertEquals(expectePage1Text, pageParser.getPageText());

        assertEquals("/cgi/ssd?id=loc.ark%3A%2F13960%2Ft9474jc00;page=ssd;view=plaintext;seq=20",
                pageParser.getNextPage());

    }

    @Test
    public void testNoRecoverableTextPage() throws IOException {
        InputStream input = PageParserTest.class.getClassLoader().
                getResourceAsStream("sampleNoRecoverableText.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

        PageParser pageParser = new PageParser(doc);

        assertEquals("Text Empty", pageParser.getPageText());

        assertEquals("/cgi/ssd?id=coo1.ark%3A%2F13960%2Ft0vq3hw2r;page=ssd;view=plaintext;seq=11",
                pageParser.getNextPage());

    }

    @Test
    public void testPageContainsImage() throws IOException {
        InputStream input = PageParserTest.class.getClassLoader().
                getResourceAsStream("samplePageContainsImage.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

        PageParser pageParser = new PageParser(doc);

        String expectePag1Text = readResourceFile("expectedTextPageContainsImage.txt");

        assertEquals(expectePag1Text, pageParser.getPageText());

        assertEquals("/cgi/ssd?id=mdp.35112104867553;page=ssd;view=plaintext;seq=10;num=ii",
                pageParser.getNextPage());

    }

    @Test
    public void testLastPage() throws IOException {
        InputStream input = PageParserTest.class.getClassLoader().
                getResourceAsStream("sampleLastPage.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

        String expectePage1Text = readResourceFile("expectedTextLastPage.txt");

        PageParser pageParser = new PageParser(doc);

        assertEquals(expectePage1Text, pageParser.getPageText());

        assertNull(pageParser.getNextPage());

    }

}
