package sourcecode

object Implicits {
  def implicitRun() = {
    val name = implicitly[sourcecode.Name]
    assert(name.value == "name")

    val fullName = implicitly[sourcecode.FullName]
    assert(fullName.value == "sourcecode.Implicits.fullName")

    val enclosing = implicitly[sourcecode.Enclosing]
    assert(enclosing.value == "sourcecode.Implicits.implicitRun enclosing")

    val pkg = implicitly[sourcecode.Pkg]
    assert(pkg.value == "sourcecode")

    val file = implicitly[sourcecode.File]
    assert(file.value.endsWith("/sourcecode/shared/src/test/scala/sourcecode/Implicits.scala"))

    val line = implicitly[sourcecode.Line]
    assert(line.value == 20)

    lazy val myLazy = {
      trait Bar{
        val name = implicitly[sourcecode.Name]
        assert(name.value == "name")

        val fullName = implicitly[sourcecode.FullName]
        assert(
          fullName.value == "sourcecode.Implicits.Bar.fullName" ||
            fullName.value == "sourcecode.Implicits._$Bar.fullName" // dotty
        )

        val file = implicitly[sourcecode.File]
        assert(file.value.endsWith("/sourcecode/shared/src/test/scala/sourcecode/Implicits.scala"))

        val line = implicitly[sourcecode.Line]
        assert(line.value == 37)

        val enclosing = implicitly[sourcecode.Enclosing]
        assert(
          (enclosing.value == "sourcecode.Implicits.implicitRun myLazy$lzy Bar#enclosing") ||
          (enclosing.value == "sourcecode.Implicits.implicitRun myLazy Bar#enclosing") || // encoding changed in Scala 2.12
          (enclosing.value == "sourcecode.Implicits.implicitRun myLazy Bar.enclosing") // dotty
        )
      }
      val b = new Bar{}
    }
    myLazy // FIXME seems like this is not run on dotty
  }
}
