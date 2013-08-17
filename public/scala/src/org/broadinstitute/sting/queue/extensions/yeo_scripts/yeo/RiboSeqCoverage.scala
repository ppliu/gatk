package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class RiboSeqCoverage(@Input inBam: File, @Output outBed: File) extends CommandLineFunction {
  override def shortDescription = "RiboSeqCoverage"
  def commandLine = "riboseq_coverage.py " +
  required("--bam", inBam) +  
  required("--out", outBed) 
}