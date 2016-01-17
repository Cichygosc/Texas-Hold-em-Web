
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object main_Scope0 {
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

class main extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template2[String,Html,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(connected: String)(content: Html):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.36*/("""

"""),format.raw/*3.1*/("""<!DOCTYPE html>

<html>
    <head>
        <title>Websocket Chat-Room</title>
		<link rel="stylesheet" media="screen" href=""""),_display_(/*8.48*/routes/*8.54*/.Assets.at("stylesheets/bootstrap.css")),format.raw/*8.93*/("""">
        <link rel="stylesheet" media="screen" href=""""),_display_(/*9.54*/routes/*9.60*/.Assets.at("stylesheets/main.css")),format.raw/*9.94*/("""">
        <link rel="shortcut icon" type="image/png" href=""""),_display_(/*10.59*/routes/*10.65*/.Assets.at("images/favicon.png")),format.raw/*10.97*/("""">
        <script src=""""),_display_(/*11.23*/routes/*11.29*/.Assets.at("javascripts/jquery-1.7.1.min.js")),format.raw/*11.74*/("""" type="text/javascript"></script>
    </head>
    <body>
        
        <div class="topbar">
            <div class="fill">
                <div class="container">
                                  
                    """),_display_(/*19.22*/if(connected != null)/*19.43*/ {_display_(Seq[Any](format.raw/*19.45*/("""
                        """),format.raw/*20.25*/("""<p class="pull-right">
                            Logged in as """),_display_(/*21.43*/connected),format.raw/*21.52*/(""" """),format.raw/*21.53*/("""—
                            <a href=""""),_display_(/*22.39*/routes/*22.45*/.Application.index()),format.raw/*22.65*/("""">Disconnect</a>
                        </p>
                    """)))}/*24.23*/else/*24.28*/{_display_(Seq[Any](format.raw/*24.29*/("""
                        """),format.raw/*25.25*/("""<form action=""""),_display_(/*25.40*/routes/*25.46*/.Application.chatRoom()),format.raw/*25.69*/("""" class="pull-right">
                            <input id="username" name="username" class="input-small" type="text" placeholder="Username">
                            <button class="btn" type="submit">Sign in</button>
                        </form>
                    """)))}),format.raw/*29.22*/("""
                    
                """),format.raw/*31.17*/("""</div>
            </div>
        </div>

        <div class="container">

            <div class="content">
                """),_display_(/*38.18*/content),format.raw/*38.25*/("""
            """),format.raw/*39.13*/("""</div>

            <footer>
                <p>
                    <a href="http://www.playframework.com">www.playframework.com</a>
                </p>
            </footer>

        </div>
        
    </body>
</html>
"""))
      }
    }
  }

  def render(connected:String,content:Html): play.twirl.api.HtmlFormat.Appendable = apply(connected)(content)

  def f:((String) => (Html) => play.twirl.api.HtmlFormat.Appendable) = (connected) => (content) => apply(connected)(content)

  def ref: this.type = this

}


}

/**/
object main extends main_Scope0.main
              /*
                  -- GENERATED --
                  DATE: Fri Jan 15 14:27:35 CET 2016
                  SOURCE: /home/jacob/Dokumenty/Eclipse/websocket-logger2/app/views/main.scala.html
                  HASH: b3e022a5491e17fc7fdf221d5799a13ed149b556
                  MATRIX: 748->1|877->35|905->37|1056->162|1070->168|1129->207|1211->263|1225->269|1279->303|1367->364|1382->370|1435->402|1487->427|1502->433|1568->478|1818->701|1848->722|1888->724|1941->749|2033->814|2063->823|2092->824|2159->864|2174->870|2215->890|2301->958|2314->963|2353->964|2406->989|2448->1004|2463->1010|2507->1033|2813->1308|2879->1346|3032->1472|3060->1479|3101->1492
                  LINES: 27->1|32->1|34->3|39->8|39->8|39->8|40->9|40->9|40->9|41->10|41->10|41->10|42->11|42->11|42->11|50->19|50->19|50->19|51->20|52->21|52->21|52->21|53->22|53->22|53->22|55->24|55->24|55->24|56->25|56->25|56->25|56->25|60->29|62->31|69->38|69->38|70->39
                  -- GENERATED --
              */
          