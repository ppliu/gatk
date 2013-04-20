package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class NegBedGraph(@Input inBedGraph: File, @Output outBedGraph: File) extends CommandLineFunction {
  def commandLine = "python /nas3/gpratt/gscripts/negBedGraph.py " + required("--bg", inBedGraph) + " > " + required(outBedGraph)
  this.isIntermediate = true
}