
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object chatRoom_Scope0 {
import models._
import controllers._
import play.api.i18n._
import views.html._
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

class chatRoom extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(username: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.20*/("""

"""),_display_(/*3.2*/main(username)/*3.16*/ {_display_(Seq[Any](format.raw/*3.18*/("""
    
    """),format.raw/*5.5*/("""<div class="page-header">
        <h1>WebSocket Logger</h1>
    </div>
    
    <div id="onError" class="alert-message error">
        <p>
            <strong>Error!</strong> <span></span>
        </p>
    </div>
    
    <div id="onChat" class="row">
    	<input id="nr" placeholder="Card Number"></input>    	
		
		
    	<div class="span10" id="main">
		
     			<div id="messages"></div>          
   			
   		</div>
    </div>
    
    <script type="text/javascript" charset="utf-8" src=""""),_display_(/*26.58*/routes/*26.64*/.Application.chatRoomJs(username)),format.raw/*26.97*/(""""></script>
    
""")))}))
      }
    }
  }

  def render(username:String): play.twirl.api.HtmlFormat.Appendable = apply(username)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (username) => apply(username)

  def ref: this.type = this

}


}

/**/
object chatRoom extends chatRoom_Scope0.chatRoom
              /*
                  -- GENERATED --
                  DATE: Fri Jan 15 14:27:35 CET 2016
                  SOURCE: /home/jacob/Dokumenty/Eclipse/websocket-logger2/app/views/chatRoom.scala.html
                  HASH: 88843b47c99c575eebb9c77f5fdaf256d0318850
                  MATRIX: 751->1|864->19|892->22|914->36|953->38|989->48|1509->541|1524->547|1578->580
                  LINES: 27->1|32->1|34->3|34->3|34->3|36->5|57->26|57->26|57->26
                  -- GENERATED --
              */
          