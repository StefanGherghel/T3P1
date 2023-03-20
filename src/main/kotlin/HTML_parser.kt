package ro.mike.tuiasi
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.File
/**
 * @param url - Uniform Resource Locator - address of an website
 * @return HTML content corresponding to the URL as a string
 */

class Item(var prop: Map<Elements,Elements> = mapOf<Elements,Elements>(), var link: Elements)
{
    fun print()
    {
        prop.forEach()
        {
            print("Titlu: ");
            println(it.key.text());
        }
        println("Link: ${link.text()}")

    }
}

fun testKhttpGetRequest(url: String) : String {
    val response = khttp.get(url)
    println("${response.statusCode}\t ${response.headers["Content-Type"]}")
    return response.text
}

fun testJsoup(source: String, url: String, baseURI: String?=null) {
    var htmlDocument: Document? = null
    htmlDocument = when(source) {
        "url" -> Jsoup.connect(url).get()
        "file" -> Jsoup.parse(File(url), "UTF-8", baseURI)
        "string" -> Jsoup.parse(url)
        else -> throw Exception("Unknown source")
    }
    val cssHeadlineSelector: String = "#khttp-http-without-the-bullshit h1"
    val cssParagraphSelector = "#khttp-http-without-the-bullshit p"
    val cssLinkSelector = "#khttp-http-without-the-bullshit > p > a"
    println(htmlDocument.title())
    println(htmlDocument.select(cssHeadlineSelector).text())
    val paragraphs: Elements = htmlDocument.select(cssParagraphSelector)
    for (paragraph in paragraphs) {
        println("\t${paragraph.text()}")
    }
    val links = htmlDocument.select(cssLinkSelector)
    println("-".repeat(100))
    for (link in links) {
        println("${link.text()}\n\t${link.absUrl("href")}")
    }
}



fun main() {
    var document = Jsoup.connect("http://rss.cnn.com/rss/edition.rss").get();
    println("Items: ")

    var items:Elements = document.getElementsByTag("Item")
    var ItemList: MutableList<Item> = mutableListOf();

    var contor = 0;
    for (item in items)
    {
        var titlu: Elements =  item.select("title");
        var descriere = item.select("description")
        var priorit : Map<Elements,Elements> = mapOf(titlu to descriere)

        var link: Elements = item.select("link")
        ItemList.add(contor, Item(priorit,link))
        contor++
    }
    for (item in ItemList)
    {
        item.print()
    }
}