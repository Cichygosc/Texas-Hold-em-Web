
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object index_Scope0 {
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

class index extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](_display_(/*2.2*/main(null)/*2.12*/ {_display_(Seq[Any](format.raw/*2.14*/("""
    
    """),_display_(/*4.6*/if(flash.containsKey("error"))/*4.36*/ {_display_(Seq[Any](format.raw/*4.38*/("""
        
        """),format.raw/*6.9*/("""<div class="alert-message error">
            <p>
                <strong>Oops!</strong> """),_display_(/*8.41*/flash/*8.46*/.get("error")),format.raw/*8.59*/("""
            """),format.raw/*9.13*/("""</p>
        </div>
        
    """)))}),format.raw/*12.6*/("""
        
    """),format.raw/*14.5*/("""<div class="alert-message block-message info">
        <p>
            <strong>Websocket Play!</strong> 
            To start, choose a username and sign in using the top right form.
        </p>
    </div>
    
""")))}))
      }
    }
  }

  def render(): play.twirl.api.HtmlFormat.Appendable = apply()

  def f:(() => play.twirl.api.HtmlFormat.Appendable) = () => apply()

  def ref: this.type = this

}


}

/**/
object index extends index_Scope0.index
              /*
                  -- GENERATED --
                  DATE: Thu Dec 24 01:37:20 CET 2015
                  SOURCE: D:/websocket-logger2/app/views/index.scala.html
                  HASH: 019156dbfcfc74d2946aa18a5bc8a20f2cf26aef
                  MATRIX: 827->2|845->12|884->14|920->25|958->55|997->57|1041->75|1157->165|1170->170|1203->183|1243->196|1307->230|1348->244
                  LINES: 32->2|32->2|32->2|34->4|34->4|34->4|36->6|38->8|38->8|38->8|39->9|42->12|44->14
                  -- GENERATED --
              */
          