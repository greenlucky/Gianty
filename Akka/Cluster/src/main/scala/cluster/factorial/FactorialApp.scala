package cluster.factorial

/**
  * Created by lamdevops on 7/9/17.
  */
object FactorialApp extends App{
  FactorialBackend.main(Seq("2551").toArray)
  FactorialBackend.main(Seq("2552").toArray)
  FactorialBackend.main(Array.empty)
  FactorialFrontend.main(Array.empty)
}
