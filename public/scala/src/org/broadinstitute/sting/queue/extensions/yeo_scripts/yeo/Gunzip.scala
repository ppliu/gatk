package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._

import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class Gunzip(@Input inFastq: File, @Output outFastq: File) extends CommandLineFunction {
  override def shortDescription = "Gunzip"
  def commandLine = "gunzip -c " + required(inFastq) + " > " + required(outFastq)
  this.isIntermediate = false
}
