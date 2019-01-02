package com.slocumboy.webcrawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageParser {

    private Document document;

    private String pageText;

    private String nextPage;


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
        Element mdpPage = document.getElementById("mdpPage");
        Elements pNodes = mdpPage.getElementsByTag("p");
        pageText = pNodes.get(0).text();

        Element pNodeForNavigation = pNodes.get(1);
        nextPage = getNextFromNavNode(pNodeForNavigation);

    }

    private String getNextFromNavNode(Element pNodeForNavigation) {
        Elements links = pNodeForNavigation.getElementsByTag("a");
        for (Element link : links) {
            if (link.text().equals("Next Page")) {
                return link.attr("href");
            }
        }
        return null;
    }
}
