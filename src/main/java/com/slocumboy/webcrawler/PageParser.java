package com.slocumboy.webcrawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.logging.Logger;

public class PageParser {

    private Document document;

    private String pageText;

    private String nextPage;

    private static Logger logger = Logger.getLogger(PageParser.class.getName());

    public PageParser(Document document) {
        this.document = document;
        parse();
    }

    public String getPageText() {
        return pageText;
    }

    public String getNextPage() {
        return nextPage;
    }

    private void parse() {

        logger.finer("Parsing Page");
        Element mdpPage = document.getElementById("mdpPage");
        Element mdpEmptyText = mdpPage.getElementById("mdpTextEmpty");
        Element pNodeForNavigation = null;

        Elements pNodes = null;

        if (mdpEmptyText == null) {
            pNodes = mdpPage.getElementsByTag("p");
            pageText = pNodes.get(0).text();
        } else {
            pageText = "Text Empty";
            pNodes = mdpPage.getElementsByTag("p");
        }

        nextPage = getNextFromNavNode(pNodes);
        logger.finer("Done Parsing Page");

    }

    private String getNextFromNavNode(Elements pNodes) {
        for (Element pNode : pNodes) {
            Elements links = pNode.getElementsByTag("a");
            for (Element link : links) {
                if (link.text().equals("Next Page")) {
                    return link.attr("href");
                }
            }
        }
        return null;
    }
}
