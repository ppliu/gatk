package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline.Output
import java.io.File
import org.broadinstitute.sting.commandline._
import org.broadinstitute.sting.queue.function.CommandLineFunction

class FilterRepetativeRegions(@Input inFastq: File, @Output outCounts: File, @Output outNoRep: File) extends CommandLineFunction {
  override def shortDescription = "FilterRepetativeRegions"
  this.nCoresRequest = Option(16) 
  def commandLine = "bowtie -S -q -p 16 -e 100 -l 20 --un %s all_ref %s | count_aligned_from_sam.py > %s".format(outNoRep, inFastq, outCounts)

}