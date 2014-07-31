package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class SailfishQuant extends CommandLineFunction {

 def sailfish_loc = "/oasis/tscc/scratch/yeo-lab/ace_progenity/asd_processing/dependencies/Sailfish-0.6.3-Linux_x86-64/bin/sailfish "
 
 @Input(doc="input fastq file", shortName = "inFastq", fullName = "input_fastq_file", required = true) 
 var inFastq: File = _
 
 @Input(doc="input fastq pair file", shortName = "inFastqPair", fullName = "input_fastq_pair_file", required = false) 
 var inFastqPair: File = _ 

 @Output(doc="output file basename", shortName = "outbase", fullName = "out_basename", required = true) 
 var outbase: File = _
 
 @Argument(doc="index location", shortName = "index", fullName = "index", required = true) 
 var index: String = _
 
 @Argument(doc="library type", shortName = "ltype", fullName = "library_type", required = true) 
 var ltype: String = _

 this.nCoresRequest = Option(16) 
 override def shortDescription = "SailfishQuant"  
 def commandLine = sailfish_loc +
  		required("--index", index) +
  		required("--threads", "16") +
  		required("--libtype", ltype) +
  		required("--mates1", inFastq) +
        optional("--mates2", inFastqPair) + 
  		required("--out", outbase)

 this.wallTime = Option((336 * 60 * 60).toLong)
 //this.isIntermediate = true
}
