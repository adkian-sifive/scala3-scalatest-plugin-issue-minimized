package debug

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class One {
  val one = 1
}

abstract class Okable // Record

abstract class SomeClass extends Okable // Bundle

class SomeChildClass extends SomeClass // BadSeqBundle

class Container // Module

object ChildContainer { // IO
  def apply[T](gen: => T): T = gen
}

def wrapperFn(some: => Any): Unit = { // thrownBy
  println("wrapping thing")
}

def emitThing(gen: => Container): Unit = { // emitChirrtl
  val fn = () => gen
  println("emitting thing")
}

class WrapperSpec extends AnyFlatSpec with Matchers {
  "Wrapping types" should "work" in {
    wrapperFn {
      emitThing {
        new Container {
          val sc = ChildContainer(new SomeClass {
            val scc = new SomeChildClass
          })
        }
      }
    }
  }
}

@main def run() = {
  println("running")
  val z = new WrapperSpec
}
