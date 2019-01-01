package com.slocumboy.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.File;
import java.io.IOException;

public class PageParserTest {

    public void load() throws IOException {
        File input = new File("/tmp/input.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
    }
}
