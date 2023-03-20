import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

class Page(var link:String, var listaLinkuri: MutableList<Page> = mutableListOf() )
{
    fun print()
    {
        if(listaLinkuri.isEmpty())
        {
            print(link+" ")
            return
        }

        print("\n"+link+ " URMATOARE: ->>>>>>>>")

        for(lll in listaLinkuri)
        {
            lll.print();
        }


    }
    fun generate()
    {
        val document = Jsoup.connect(link).get()
        //Get links from document object.
        val legaturi = document.select("a[href]")
        //Iterate links and print link attributes.
        for (iter in legaturi)
        {
            if(iter.attr("href").startsWith(link))
            {
                var auxPage = Page(iter.attr("href"))
                listaLinkuri.add(auxPage)
                val document2 = Jsoup.connect(iter.attr("href")).get()
                //Get links from document object.
                val legaturi2 = document.select("a[href]")
                //Iterate links and print link attributes.
                for (iter2 in legaturi)
                {
                    if (iter2.attr("href").startsWith(link)) {
                        var auxPage2 = Page(iter.attr("href"))
                        auxPage.listaLinkuri.add(auxPage2)
                    }
                }
            }
        }
    }


}




fun main(args: Array<String>)
{
    var myWebsite = Page("https://sso.tuiasi.ro/auth/realms/TUIASI/protocol/openid-connect/auth?client_id=edu.tuiasi.ro&response_type=code&redirect_uri=https%3A%2F%2Fedu.tuiasi.ro%2Fadmin%2Foauth2callback.php&state=%2Fauth%2Foauth2%2Flogin.php%3Fwantsurl%3Dhttps%253A%252F%252Fedu.tuiasi.ro%252F%26sesskey%3DhdKQriyzOQ%26id%3D3&scope=openid%20profile%20email")
    myWebsite.generate()
    myWebsite.print()
}
