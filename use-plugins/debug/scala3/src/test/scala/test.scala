package debug

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

abstract class Okable { // Record
  def _doNothing: Unit = println("Nothing")
} 

abstract class SomeClass extends Okable // Bundle

class SomeChildClass extends SomeClass // BadSeqBundle

class WrapperSpec extends AnyFlatSpec with Matchers {
  // Removing Matchers mixin & changing this to "Wrapping types" { works fine
  "Wrapping types" should "work" in {
    val sc = new SomeClass {
      val scc = new SomeChildClass
    }
  }
}

@main def run() = {
  val z = new WrapperSpec
}
