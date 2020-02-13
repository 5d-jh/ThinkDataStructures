package com.allendowney.thinkdast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class WikiPhilosophy {

    final static List<String> visited = new ArrayList<String>();
    final static WikiFetcher wf = new WikiFetcher();

    /**
     * Tests a conjecture about Wikipedia and Philosophy.
     *
     * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
     *
     * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String destination = "https://en.wikipedia.org/wiki/Philosophy";
        String source = "https://en.wikipedia.org/wiki/Philosophy";

        testConjecture(destination, source, 10);
    }

    /**
     * Starts from given URL and follows first link until it finds the destination or exceeds the limit.
     *
     * @param destination
     * @param source
     * @throws IOException
     */
    public static void testConjecture(String destination, String source, int limit) throws IOException {
        WikiFetcher wf = new WikiFetcher();
        String url = source;

        for (int i = 0; i < limit; i++) {
            Elements src = wf.fetchWikipedia(url);
            WikiParser wp = new WikiParser(src);
            Element elem = wp.findFirstLink();

            if (elem == null) {
                System.out.println("No link available. Exiting..");
                return;
            }
            if (visited.contains(url)) {
                System.out.println("Parser is in loop. Exiting..");
                return;
            }
            if (visited.contains(destination)) {
                System.out.println("Philosophy found.");
                return;
            }

            System.out.print(elem.text()+" -> ");

            url = elem.attr("abs:href");
        }
    }
}
