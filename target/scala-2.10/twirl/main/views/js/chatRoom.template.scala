
package views.js

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object chatRoom_Scope0 {
import models._
import controllers._
import play.api.i18n._
import views.js._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._

class chatRoom extends BaseScalaTemplate[play.twirl.api.JavaScriptFormat.Appendable,Format[play.twirl.api.JavaScriptFormat.Appendable]](play.twirl.api.JavaScriptFormat) with play.twirl.api.Template1[String,play.twirl.api.JavaScriptFormat.Appendable] {

  /**/
  def apply/*1.2*/(username: String):play.twirl.api.JavaScriptFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.20*/("""

"""),format.raw/*3.1*/("""$(function() """),format.raw/*3.14*/("""{"""),format.raw/*3.15*/("""
    """),format.raw/*4.5*/("""var chatSocket = new WebSocket(""""),_display_(/*4.38*/routes/*4.44*/.Application.chat(username).webSocketURL(request)),format.raw/*4.93*/("""")

    var sendMessage = function() """),format.raw/*6.34*/("""{"""),format.raw/*6.35*/("""
        """),format.raw/*7.9*/("""chatSocket.send(JSON.stringify(
            """),format.raw/*8.13*/("""{"""),format.raw/*8.14*/("""nr: $("#nr").val()"""),format.raw/*8.32*/("""}"""),format.raw/*8.33*/("""
        """),format.raw/*9.9*/("""))
        $("#nr").val('')

    """),format.raw/*12.5*/("""}"""),format.raw/*12.6*/("""

    """),format.raw/*14.5*/("""var receiveEvent = function(event) """),format.raw/*14.40*/("""{"""),format.raw/*14.41*/("""
        """),format.raw/*15.9*/("""var data = JSON.parse(event.data)

        // Handle errors
        if(data.error) """),format.raw/*18.24*/("""{"""),format.raw/*18.25*/("""
            """),format.raw/*19.13*/("""chatSocket.close()
            $("#onError span").text(data.error)
            $("#onError").show()
            return
        """),format.raw/*23.9*/("""}"""),format.raw/*23.10*/(""" """),format.raw/*23.11*/("""else """),format.raw/*23.16*/("""{"""),format.raw/*23.17*/("""
            """),format.raw/*24.13*/("""$("#onChat").show()
        """),format.raw/*25.9*/("""}"""),format.raw/*25.10*/("""
        
        """),format.raw/*27.9*/("""// Create the message element       
	        var el = $('<div class="message"><p style="font-size:16px"></p></div>')
	        $("p", el).text(data.message)
	        $(el).addClass('me')
	        $('#messages').append(el) 
			$('#messages').animate("""),format.raw/*32.27*/("""{"""),format.raw/*32.28*/("""scrollTop: $('#messages').prop("scrollHeight")"""),format.raw/*32.74*/("""}"""),format.raw/*32.75*/(""", 700);
    """),format.raw/*33.5*/("""}"""),format.raw/*33.6*/("""

    """),format.raw/*35.5*/("""var handleReturnKey = function(e) """),format.raw/*35.39*/("""{"""),format.raw/*35.40*/("""
        """),format.raw/*36.9*/("""if(e.charCode == 13 || e.keyCode == 13) """),format.raw/*36.49*/("""{"""),format.raw/*36.50*/("""
            """),format.raw/*37.13*/("""e.preventDefault()
            sendMessage()
        """),format.raw/*39.9*/("""}"""),format.raw/*39.10*/("""
    """),format.raw/*40.5*/("""}"""),format.raw/*40.6*/("""

    """),format.raw/*42.5*/("""$("#nr").keypress(handleReturnKey)


    chatSocket.onmessage = receiveEvent

"""),format.raw/*47.1*/("""}"""),format.raw/*47.2*/(""")
"""))
      }
    }
  }

  def render(username:String): play.twirl.api.JavaScriptFormat.Appendable = apply(username)

  def f:((String) => play.twirl.api.JavaScriptFormat.Appendable) = (username) => apply(username)

  def ref: this.type = this

}


}

/**/
object chatRoom extends chatRoom_Scope0.chatRoom
              /*
                  -- GENERATED --
                  DATE: Fri Dec 25 16:00:05 CET 2015
                  SOURCE: D:/websocket-logger2/app/views/chatRoom.scala.js
                  HASH: eecc77595f5ca8b217e29cd2401e421dc8f0a370
                  MATRIX: 771->1|890->19|918->21|958->34|986->35|1017->40|1076->73|1090->79|1159->128|1223->165|1251->166|1286->175|1357->219|1385->220|1430->238|1458->239|1493->248|1553->281|1581->282|1614->288|1677->323|1706->324|1742->333|1853->416|1882->417|1923->430|2077->557|2106->558|2135->559|2168->564|2197->565|2238->578|2293->606|2322->607|2367->625|2644->874|2673->875|2747->921|2776->922|2815->934|2843->935|2876->941|2938->975|2967->976|3003->985|3071->1025|3100->1026|3141->1039|3221->1092|3250->1093|3282->1098|3310->1099|3343->1105|3448->1183|3476->1184
                  LINES: 27->1|32->1|34->3|34->3|34->3|35->4|35->4|35->4|35->4|37->6|37->6|38->7|39->8|39->8|39->8|39->8|40->9|43->12|43->12|45->14|45->14|45->14|46->15|49->18|49->18|50->19|54->23|54->23|54->23|54->23|54->23|55->24|56->25|56->25|58->27|63->32|63->32|63->32|63->32|64->33|64->33|66->35|66->35|66->35|67->36|67->36|67->36|68->37|70->39|70->39|71->40|71->40|73->42|78->47|78->47
                  -- GENERATED --
              */
          