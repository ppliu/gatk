package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class CalculateNRF(@Input inBam: File, @Output outNRF: File, @Argument genomeSize: String) extends CommandLineFunction {

  def commandLine = "python /nas3/gpratt/gscripts/general/calculate_NRF.py " + required("--bam", inBam) + required("--genome", genomeSize) + " > " + outNRF

}