import java.util.Properties
import java.io.FileReader
import java.net._

object Main {
  def main(args : Array[String]) : Unit = {
    val DEFAULT_ENCODING = "UTF-8"
    val API_URL = "https://secure.diigo.com/api/v2/bookmarks"

    val properties = new Properties
    properties.load(new FileReader("default.properties"))
      
    val doc = xml.XML.load(System.in)
    val title = (doc \\ "title").text
    val url = "http://www.diigo.com"
    val tags = (doc \\ "span" filter ( _ attribute "class" exists (_.text == "snaptic-tag"))) map ((t: xml.Node) => URLEncoder.encode(t.text.tail, DEFAULT_ENCODING)) 
    val body = (doc \ "body" \ "div")(0).text
    
    val buf = new StringBuilder
    buf ++= "key=" ++= properties.getProperty("apikey")
    buf ++= "&title=" ++= URLEncoder.encode(title, DEFAULT_ENCODING)
    buf ++= "&url=" ++= URLEncoder.encode(url, DEFAULT_ENCODING)
    buf ++= "&shared=no"
    buf ++= "&tags="
    tags.addString(buf, ",")
    buf ++= "," ++= URLEncoder.encode(properties.getProperty("importtag"), DEFAULT_ENCODING)

    println(buf)
    println(body)

    val requestUrl = new URL(API_URL + "?" + buf)
    println(requestUrl)
    
//    val requestMethod = "POST"
    
    // TODO figure out API URL for notes
    // TODO submit API request
    
  }
}
