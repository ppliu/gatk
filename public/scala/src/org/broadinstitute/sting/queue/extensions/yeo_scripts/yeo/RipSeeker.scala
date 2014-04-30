package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class RipSeeker extends CommandLineFunction {

 @Input(doc="input bam file", shortName = "inBam", fullName = "input_bam_file", required = true)
 var inBam: File = _

 @Output(doc="bed file of peaks to output", shortName = "outBed", fullName = "outBed", required = true)
 var outBed: File = _
 
  override def shortDescription = "RipSeeker"
  
  def commandLine = "run_ripseeker.py " +
    required("--bam", inBam) +
    required("--out", outBed)
}
