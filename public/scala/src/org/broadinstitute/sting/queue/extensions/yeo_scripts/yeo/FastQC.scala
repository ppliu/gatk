package org.broadinstitute.sting.queue.extensions.yeo

import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction
import org.broadinstitute.sting.commandline.Input


class FastQC(@Input inFastq: File) extends CommandLineFunction {
  def perl = "/oasis/tscc/scratch/yeo-lab/ace_progenity/asd_processing/dependencies/perl-5.20.0/perl"
  def fastqc = "/projects/ps-yeolab/ace_progenity/asd_processing/dependencies/FastQC/fastqc"
  override def shortDescription = "FastQC"
  def commandLine = "%s %s %s -o ./".format(perl, fastqc, inFastq)

}
