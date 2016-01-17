
// @GENERATOR:play-routes-compiler
// @SOURCE:D:/websocket-logger2/conf/routes
// @DATE:Thu Dec 24 01:37:20 CET 2015

import play.api.routing.JavaScriptReverseRoute
import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers.javascript {
  import ReverseRouteContext.empty

  // @LINE:12
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:12
    def at: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.at",
      """
        function(file) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
        }
      """
    )
  
  }

  // @LINE:6
  class ReverseApplication(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:7
    def chatRoom: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.chatRoom",
      """
        function(username) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "room" + _qS([(username == null ? null : (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("username", username))])})
        }
      """
    )
  
    // @LINE:9
    def chatRoomJs: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.chatRoomJs",
      """
        function(username) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/javascripts/chatroom.js" + _qS([(""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("username", username)])})
        }
      """
    )
  
    // @LINE:6
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + """"})
        }
      """
    )
  
    // @LINE:8
    def chat: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.chat",
      """
        function(username) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "room/chat" + _qS([(""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("username", username)])})
        }
      """
    )
  
  }


}