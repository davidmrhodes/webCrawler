package com.slocumboy.webcrawler;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

public class BookCrawler {

    private final PageWriter pageWriter;

    private final String baseUrl;

    private final String firstPage;

    private static Logger logger = Logger.getLogger(BookCrawler.class.getName());

    public static void main(String[] argv) {

        Args args = new Args();

        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(argv);

        try {
            BookCrawler bookCrawler = new BookCrawler(args.baseUrl, args.firstPage, args.outputDir);
            boolean debugEnabled = Boolean.parseBoolean(args.debug);
            if (debugEnabled) {
                bookCrawler.setDebugLogging();
            }
            bookCrawler.crawl();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public BookCrawler(String baseUrl, String firstPage, String outputDir) throws IOException {
        pageWriter = new PageWriter(outputDir);
        this.baseUrl = baseUrl;
        this.firstPage = firstPage;
    }

    public void crawl() throws IOException {

        String currentPage = firstPage;

        try (BufferedWriter writer = pageWriter.initializeBufferWritter()) {

            while (currentPage != null) {

                logger.fine("Processing Page " + currentPage);

                Document doc = getDocument(baseUrl, currentPage);
                PageParser pageParser = new PageParser(doc);

                pageWriter.writePage(pageParser.getPageText());

                currentPage = pageParser.getNextPage();
            }
        }
    }

    private Document getDocument(String baseUrl, String currentPage) throws IOException {

        Document doc = null;
        final String url = baseUrl + "/" + currentPage;
        logger.fine(() -> "Getting page " + url);

        if (baseUrl.startsWith("http://") || baseUrl.startsWith("https://")) {
            doc = Jsoup.connect(baseUrl + "/" + currentPage).get();
        } else {
            File input = new File(baseUrl + File.separator + currentPage);
            doc = Jsoup.parse(input, "UTF-8", "");
        }
        logger.fine(() -> "Got page " + url);
        return doc;
    }

    public static class Args {
        @Parameter
        private List<String> parameters = new ArrayList<>();

        @Parameter(names = { "-baseUrl"}, description = "Base Url for book", required = true)
        private String baseUrl;

        @Parameter(names = "-firstPage", description = "First page path for book", required = true)
        private String firstPage;

        @Parameter(names = "-outputDir", description = "output directory for book", required = true)
        private String outputDir;

        @Parameter(names = "-debug", description = "debug true|false")
        private String debug;
    }

    public void setDebugLogging() {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %4$-7s [%3$s] (%2$s) %5$s %6$s%n");

        final ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.FINEST);
        consoleHandler.setFormatter(new SimpleFormatter());

        final Logger app = Logger.getLogger("com.slocumboy.webcrawler");
        app.setLevel(Level.FINEST);
        app.addHandler(consoleHandler);
    }
}
