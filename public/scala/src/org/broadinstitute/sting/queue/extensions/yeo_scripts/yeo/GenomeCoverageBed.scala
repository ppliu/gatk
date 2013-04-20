package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class GenomeCoverageBed(@Input inBam: File, @Argument genomeSize: String, @Output bedGraph: File, @Argument strand: String = null) extends CommandLineFunction {
  //When it comes time to refactor use the @Input(doc, requiered=False) pattern...
  def commandLine = "genomeCoverageBed " + optional("-strand", strand) + required("-split") + required("-bg") + required("-ibam", inBam) + required("-g", genomeSize) + " > " + bedGraph
  this.isIntermediate = true
}