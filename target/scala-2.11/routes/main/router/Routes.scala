
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/jacob/Dokumenty/Eclipse/websocket-logger2/conf/routes
// @DATE:Fri Jan 15 14:27:34 CET 2016

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

object Routes extends Routes

class Routes extends GeneratedRouter {

  import ReverseRouteContext.empty

  override val errorHandler: play.api.http.HttpErrorHandler = play.api.http.LazyHttpErrorHandler

  private var _prefix = "/"

  def withPrefix(prefix: String): Routes = {
    _prefix = prefix
    router.RoutesPrefix.setPrefix(prefix)
    
    this
  }

  def prefix: String = _prefix

  lazy val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation: Seq[(String, String, String)] = List(
    ("""GET""", prefix, """controllers.Application.index()"""),
    ("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """room""", """controllers.Application.chatRoom(username:String ?= null)"""),
    ("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """room/chat""", """controllers.Application.chat(username:String)"""),
    ("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/javascripts/chatroom.js""", """controllers.Application.chatRoomJs(username:String)"""),
    ("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""", """controllers.Assets.at(path:String = "/public", file:String)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val controllers_Application_index0_route: Route.ParamsExtractor = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_Application_index0_invoker = createInvoker(
    controllers.Application.index(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "index",
      Nil,
      "GET",
      """ Home page""",
      this.prefix + """"""
    )
  )

  // @LINE:7
  private[this] lazy val controllers_Application_chatRoom1_route: Route.ParamsExtractor = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("room")))
  )
  private[this] lazy val controllers_Application_chatRoom1_invoker = createInvoker(
    controllers.Application.chatRoom(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "chatRoom",
      Seq(classOf[String]),
      "GET",
      """""",
      this.prefix + """room"""
    )
  )

  // @LINE:8
  private[this] lazy val controllers_Application_chat2_route: Route.ParamsExtractor = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("room/chat")))
  )
  private[this] lazy val controllers_Application_chat2_invoker = createInvoker(
    controllers.Application.chat(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "chat",
      Seq(classOf[String]),
      "GET",
      """""",
      this.prefix + """room/chat"""
    )
  )

  // @LINE:9
  private[this] lazy val controllers_Application_chatRoomJs3_route: Route.ParamsExtractor = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/javascripts/chatroom.js")))
  )
  private[this] lazy val controllers_Application_chatRoomJs3_invoker = createInvoker(
    controllers.Application.chatRoomJs(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "chatRoomJs",
      Seq(classOf[String]),
      "GET",
      """""",
      this.prefix + """assets/javascripts/chatroom.js"""
    )
  )

  // @LINE:12
  private[this] lazy val controllers_Assets_at4_route: Route.ParamsExtractor = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_at4_invoker = createInvoker(
    controllers.Assets.at(fakeValue[String], fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "at",
      Seq(classOf[String], classOf[String]),
      "GET",
      """ Map static resources from the /public folder to the /assets URL path""",
      this.prefix + """assets/$file<.+>"""
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case controllers_Application_index0_route(params) =>
      call { 
        controllers_Application_index0_invoker.call(controllers.Application.index())
      }
  
    // @LINE:7
    case controllers_Application_chatRoom1_route(params) =>
      call(params.fromQuery[String]("username", Some(null))) { (username) =>
        controllers_Application_chatRoom1_invoker.call(controllers.Application.chatRoom(username))
      }
  
    // @LINE:8
    case controllers_Application_chat2_route(params) =>
      call(params.fromQuery[String]("username", None)) { (username) =>
        controllers_Application_chat2_invoker.call(controllers.Application.chat(username))
      }
  
    // @LINE:9
    case controllers_Application_chatRoomJs3_route(params) =>
      call(params.fromQuery[String]("username", None)) { (username) =>
        controllers_Application_chatRoomJs3_invoker.call(controllers.Application.chatRoomJs(username))
      }
  
    // @LINE:12
    case controllers_Assets_at4_route(params) =>
      call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        controllers_Assets_at4_invoker.call(controllers.Assets.at(path, file))
      }
  }
}