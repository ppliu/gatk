package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class STAR extends CommandLineFunction {

  
 @Input(doc="input fastq file", shortName = "inFastq", fullName = "input_fastq_file", required = true) 
 var inFastq: File = _
 
 @Output(doc="output sam file", shortName = "outSam", fullName = "out_sam_file", required = true) 
 var outSam: File = _
 
 @Argument(doc="genome location", shortName = "genome", fullName = "genome", required = true) 
 var genome: String = _
 
 @Argument(doc="maximum number of reads to multimap", 
     shortName = "multimapNMax", 
     fullName = "multimapNMax", required = true)     
 var multimapNMax: Int = 10

  @Argument(doc="the score range below the maximum score for multimapping alignments", 
      shortName = "outFilterMultimapScoreRange", 
      fullName = "outFilterMultimapScoreRange", required = true) 
  var outFilterMultimapScoreRange: Int = 1
 
 this.nCoresRequest = Option(16) 
 override def shortDescription = "STAR"  
 def commandLine = "STAR " +
  		required("--runMode", "alignReads") +
  		required("--runThreadN", "16") +
  		required("--genomeDir", genome) +
  		required("--genomeLoad", "LoadAndRemove") +
  		required("--readFilesIn", inFastq) +
  		required("--outSAMunmapped", "Within") +
  		required("--outFilterMultimapNmax", multimapNMax) +
  		required("--outFilterMultimapScoreRange", outFilterMultimapScoreRange ) +
  		required("--outFileNamePrefix", outSam) +
  		required("--outStd", "SAM") + "> " + outSam
 //this.isIntermediate = true
}