package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class Cutadapt(@Input inFastq: File, @Output outFastq: File, @Output report: File, @Argument anywhere: List[String] = Nil, front: List[String] = Nil, @Argument overlap: Option[Int] = None, error_rate: Option[Double] = None, length: Option[Int] = None, quality_cutoff: Option[Int] = None) extends CommandLineFunction {
  //see argunments on cutadapt command line for more documentation

  def commandLine = "cutadapt -f fastq --match-read-wildcards --times 2" + optional("-e", error_rate) + optional("-O", overlap) + optional("--quality-cutoff", quality_cutoff) + optional("-m", length) + repeat("-b", anywhere) + repeat("-f", front) + required("-o", outFastq) + required(inFastq) + " > " + report
  this.isIntermediate = true
}