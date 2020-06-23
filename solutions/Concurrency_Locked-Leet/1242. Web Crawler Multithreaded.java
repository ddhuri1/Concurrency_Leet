/*
Given a url startUrl and an interface HtmlParser, implement a Multi-threaded web crawler to crawl all links that are under the same hostname as startUrl. 

Return all urls obtained by your web crawler in any order.

Your crawler should:

Start from the page: startUrl
Call HtmlParser.getUrls(url) to get all urls from a webpage of given url.
Do not crawl the same link twice.
Explore only the links that are under the same hostname as startUrl.

For simplicity sake, you may assume all urls use http protocol without any port specified. For example, the urls http://leetcode.com/problems and http://leetcode.com/contest are under the same hostname, while urls http://example.org/test and http://example.com/abc are not under the same hostname.

The HtmlParser interface is defined as such: 

interface HtmlParser {
  // Return a list of all urls from a webpage of given url.
  // This is a blocking call, that means it will do HTTP request and return when this request is finished.
  public List<String> getUrls(String url);
}
Note that getUrls(String url) simulates performing a HTTP request. You can treat it as a blocking function call which waits for a HTTP request to finish. It is guaranteed that getUrls(String url) will return the urls within 15ms.  Single-threaded solutions will exceed the time limit so, can your multi-threaded web crawler do better?

Below are two examples explaining the functionality of the problem, for custom testing purposes you'll have three variables urls, edges and startUrl. Notice that you will only have access to startUrl in your code, while urls and edges are not directly accessible to you in code.

*/

/**
 * // This is the HtmlParser's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface HtmlParser {
 *     public List<String> getUrls(String url) {}
 * }
 */
class Solution
{
    public List<String> crawl(String startUrl, HtmlParser htmlParser) 
    {
        int idx = startUrl.indexOf("/", 7);
        String hostname = (idx == -1) ? startUrl : startUrl.substring(0, idx);
        Crawler crawler = new Crawler(hostname, startUrl, htmlParser);
        crawler.result = (new ConcurrentHashMap<>()).newKeySet();
        Thread thread = new Thread(crawler);
        thread.start();
        Crawler.joinThread(thread);
        return new ArrayList<>(crawler.result);
    }
}

 public class Crawler implements Runnable 
 {
        String hostname;
        String startUrl;
        HtmlParser htmlParser;
        public static Set<String> result = (new ConcurrentHashMap<>()).newKeySet();
        Crawler(String host, String url, HtmlParser html) 
        {
            this.hostname = host;
            this.startUrl = url;
            this.htmlParser = html;
        }
        
        @Override
        public void run()
        {
            if(this.startUrl.startsWith(hostname) && result.add(this.startUrl)) 
            {
                List<String> urls = this.htmlParser.getUrls(this.startUrl);
                List<Thread> threads = new ArrayList<>();
                for(String url : urls) 
                {
                    Crawler crawler = new Crawler(this.hostname, url, this.htmlParser);
                    Thread thread = new Thread(crawler);
                    thread.start();
                    threads.add(thread);
                }
                for(Thread th : threads) 
                    joinThread(th);
            }
        }
        
        public static void joinThread(Thread thread) 
        {
            try 
            {
                thread.join();
            } catch(InterruptedException e) 
            {
                e.printStackTrace();
            }
        }
    }