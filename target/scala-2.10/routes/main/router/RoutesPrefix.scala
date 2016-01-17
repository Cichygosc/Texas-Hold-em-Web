
// @GENERATOR:play-routes-compiler
// @SOURCE:D:/websocket-logger2/conf/routes
// @DATE:Thu Dec 24 01:37:20 CET 2015


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
