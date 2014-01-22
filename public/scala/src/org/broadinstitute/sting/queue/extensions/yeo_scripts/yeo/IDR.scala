package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class IDR(@Input inBam: File, @Output outResult: File, @Argument premRNA: Boolean, @Argument species: String, @Argument genome: String) extends CommandLineFunction {
  override def shortDescription = "IDR"
  def commandLine = "python perform_idr.py " +
    required("--bam", inBam) +
    required("--out", outResult) +
    conditional(premRNA, "--premRNA") +
    required("--species", species) +
    required("--genome", genome)
}