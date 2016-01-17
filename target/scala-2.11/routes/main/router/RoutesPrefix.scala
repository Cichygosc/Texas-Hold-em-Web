
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/jacob/Dokumenty/Eclipse/websocket-logger2/conf/routes
// @DATE:Fri Jan 15 14:27:34 CET 2016


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
