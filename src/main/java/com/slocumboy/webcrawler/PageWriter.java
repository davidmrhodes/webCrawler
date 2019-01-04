package com.slocumboy.webcrawler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class PageWriter {

    private String dirName;

    private int pageNumber;

    private String fullFile;

    BufferedWriter writer;

    public PageWriter(String dirName) throws IOException {
        this.dirName = dirName;
        pageNumber = 1;
        fullFile = dirName + File.separator + "full.txt";
        Files.createFile(Paths.get(fullFile));
    }

    public void writePage(String pageContent) throws IOException {

        String fileName = dirName + File.separator + "page_" + pageNumber + ".txt";

        String fullPageContent = "@Page_" + pageNumber + "\n\n" + pageContent + "\n\n";

        Files.write(Paths.get(fileName), fullPageContent.getBytes());

        writer.write(fullPageContent);

        pageNumber++;
    }

    public BufferedWriter initializeBufferWritter() throws IOException {
        writer = new BufferedWriter(new FileWriter(fullFile));
        return writer;
    }
}
