
import dotty.tools.dotc.report
import dotty.tools.dotc.plugins.{PluginPhase, StandardPlugin}
import dotty.tools.dotc.core.Contexts.Context
import dotty.tools.dotc.ast.tpd
import dotty.tools.dotc.ast.tpd.Tree
import dotty.tools.dotc.ast.tpd.TreeOps
import dotty.tools.dotc.CompilationUnit
import dotty.tools.dotc.core.Phases.Phase
import dotty.tools.dotc.typer.TyperPhase
import dotty.tools.dotc.parsing._

import dotty.tools.dotc.core.Names
import dotty.tools.dotc.core.Flags
import dotty.tools.dotc.core.Constants.*
import dotty.tools.dotc.core.Contexts.*
import dotty.tools.dotc.core.Symbols.*
import dotty.tools.dotc.core.Types.*

class PluginDebugPhase extends PluginPhase {
  val phaseName: String = "pluginDebugPhase"
  override val runsAfter = Set(TyperPhase.name)

  def isOkable(t: Type)(using Context): Boolean = {
    val okableTpe = requiredClass("debug.Okable")
    t.baseClasses.contains(okableTpe)
  }

  def genPrintsOk(outer: tpd.TypeDef)(using ctx: Context): tpd.DefDef = {
    val debugOne = requiredClassRef("debug.One")
    val newExpr = tpd.New(outer.symbol.typeRef, List.empty)
    val doNothingSym = newSymbol(
      outer.symbol,
      Names.termName("_doNothing"),
      Flags.Method | Flags.Override | Flags.Protected,
      MethodType(Nil, Nil, defn.UnitType)
    )
    tpd.DefDef(doNothingSym.asTerm, _ => newExpr)
  }

  override def transformTypeDef(okable: tpd.TypeDef)(using Context): tpd.Tree = {
    if (isOkable(okable.tpe) && okable.isClassDef
      && !okable.symbol.flags.is(Flags.Abstract)) {

      val thiz:     tpd.This = tpd.This(okable.symbol.asClass)
      val printsOkDef = genPrintsOk(okable)

      okable match {
        case td @ tpd.TypeDef(name, tmpl: tpd.Template) => {
          val newDefs = printsOkDef.toList
          val newTemplate =
            if (tmpl.body.size >= 1)
              cpy.Template(tmpl)(body = newDefs ++: tmpl.body)
            else
              cpy.Template(tmpl)(body = newDefs)
          tpd.cpy.TypeDef(td)(name, newTemplate)
        }
        case _ => super.transformTypeDef(okable)
      }
    } else {
      super.transformTypeDef(okable)
    }
  }
}

class PluginDebug extends StandardPlugin {
  val name: String = "debug"
  override val description: String = "debug plugins"

  override def init(options: List[String]): List[PluginPhase] = {
    (new PluginDebugPhase) :: Nil
  }
}

