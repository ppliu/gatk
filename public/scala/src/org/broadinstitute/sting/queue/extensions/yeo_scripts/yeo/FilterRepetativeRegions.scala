package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline.Output
import java.io.File
import org.broadinstitute.sting.commandline._
import org.broadinstitute.sting.queue.function.CommandLineFunction

class FilterRepetitiveRegions extends CommandLineFunction {
  override def shortDescription = "FilterRepetitiveRegions"

  @Input(doc="input fastq file", shortName = "inFastq", fullName = "input_fastq_file", required = true) 
  var inFastq: File = _
  
  @Argument(doc="metrics file for what reads got removed", shortName = "outCounts", fullName = "out_counts", required = true) 
  var outCounts: File = _
 
  @Output(doc="fastq file with repetive elements remove", shortName = "outNoRep", fullName = "outNoRep", required = true) 
  var outNoRep: File = _

  this.nCoresRequest = Option(16) 
  def commandLine = "bowtie -S -q -p 16 -e 100 -l 20 --un %s all_ref %s | count_aligned_from_sam.py > %s".format(outNoRep, inFastq, outCounts)

}