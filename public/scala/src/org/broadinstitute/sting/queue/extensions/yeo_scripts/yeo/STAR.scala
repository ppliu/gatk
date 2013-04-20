package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class STAR(@Input inFastq: File, @Output samFile: File, @Argument genome: String) extends CommandLineFunction {

  def commandLine = "/nas3/yeolab/Software/STAR/STAR_2.3.0e/STAR --runMode alignReads --runThreadN 4 --genomeDir /nas3/yeolab/Software/STAR/genomes/2.2.0/%s --genomeLoad LoadAndRemove --readFilesIn %s --outSAMunmapped Within --outFilterMultimapNmax 1 --outFileNamePrefix %s --outStd SAM > %s".format(genome, inFastq, samFile, samFile)
  this.isIntermediate = true
}