Scalatest should matchers are causing compiler crashes during erasure phase when a Scala 3 compiler plugin modifies the template of a class 

To run, publish plugin first:

`sbt publishLocal`

then run the failing test

`sbt useDebug/compile`

