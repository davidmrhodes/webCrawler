package com.slocumboy.webcrawler;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.Assert.fail;


public class BookCrawlerTest extends  AbstractTest {
    private String bookTestDir;

    private String outputTestDir;

    private static final String PAGE_1 = "RECORDS OF THE COLONY OF RHODE ISLAND AND PROVIDENCE PLANTATIONS, I N NEW ENGLAND PRINTED BY OKDEE OF THE GENERAL 4SSEMBLy. EDITED ET JOHN RUSSELL BARTLETT, SECRETARY OF STATE. VOL. 11. 1664 TO 16 7 7. PROVIDENCE, R. I.: A. CRAWFORD GREENE AND BROTHER, STATE PRINTEfilS, 1857. ■[/K'lVERrifTY";
    private static final String PAGE_2 = "A- bli>3 •*» UNiVEf?SST¥ UBRApV 4";
    private static final String PAGE_3 = "REMARKS The Second Volume of the Colonial Records of Ehode Island commences with the adoption of the Charter of Charles the Second, and the organization of the govern- ment under the same, in March 1663 — 1664, and extends to the close of the year 1677, thereby including fourteen years of the Colonial Annals. The Records of the proceedings of the General Assem- bly are printed verbatim from the original manuscript copy in the Archives of the State. In addition to these, there are inserted in their proper places, the records of the \" Proceedings of the Grovernor and Council,\" which held frequent meetings, between ftie Sessions of the Greneral Assembly, during certain periods, and whose proceedings are of equal historical importance with those of the former body. These are also printed from the original book of records. The great Charter of Charles the Second precedes the Records, and is an exact reprint from the original docu- ment. Two important events in the history of the Colony took";
    private static final String PAGE_4 = "IV. REMARKS. place during the period included in this volume. These are the dispute with the Colony of Connecticut for the juris- diction of the Narragansett country, and the difiSculties arising therefrom, and King Philip's war. The corres- pondence and proceedings of the two Colonies, connected with their claims to the Narragansett country, are printed at length, for the first time, in their proper places in this volume. Much of this correspondence was copied a few years since from the records and files in the public Ar- chives of Connecticut, for the Rhode Island Historical Society. From those copies these documents have now been printed. A number of valuable documents have been taken from the manuscript collection of John Carter Brown, Esquire, of Providence, who kindly placed this collection at my dis- posal, for the use of the State. Other documents of value have been obtained from the office of the Secretary of State, of the State of New York. J. R. B. Providence, May, 1857.";

    @Before
    public void setup() throws Exception {
        bookTestDir = System.getProperty("com.slocumboy.BOOK_TEST_DIR");

        outputTestDir = System.getProperty("com.slocumboy.OUT_TEST_DIR");

        deleteFilesInDir(outputTestDir);

    }

    @Test
    public void test() throws Exception {

        String[] argv = {
                "-baseUrl", bookTestDir,
                "-firstPage", "page1.html",
                "-outputDir", outputTestDir
        };

        BookCrawler.main(argv);

        String page1 = getPageContent(outputTestDir, 1);
        assertPageContent(page1, PAGE_1,1);

        String page2 = getPageContent(outputTestDir, 2);
        assertPageContent(page2, PAGE_2, 2);

        String page3 = getPageContent(outputTestDir, 3);
        assertPageContent(page3, PAGE_3, 3);

        String page4 = getPageContent(outputTestDir, 4);
        assertPageContent(page4, PAGE_4, 4);

        assertFullContent(outputTestDir, page1 + "\n" + page2 + "\n" + page3 + "\n" + page4);
    }

    private void deleteFilesInDir(String directory) throws IOException {
        try (Stream<Path> entries = Files.list(Paths.get(directory))) {
            entries.forEach(entry -> {
                try {
                    Files.delete(entry);
                } catch (IOException e) {
                    fail("Could not clean directory");
                }
            });
        }
    }
}
