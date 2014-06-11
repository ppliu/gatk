package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class CuffQuant extends CommandLineFunction {

 
 @Input(doc="input bam file", shortName = "inBam", fullName = "input_bam_file", required = true) 
 var inBam: File = _
 
 @Argument(doc="genome location", shortName = "genome", fullName = "genome", required = true) 
 var genome: String = _

 @Argument(doc="library type", shortName = "ltype", fullName = "librarytype", required = true) 
 var ltype: String = _

 this.nCoresRequest = Option(16) 
 override def shortDescription = "CuffQuant"  
 def commandLine = "cuffquant " +
  		required("--library-type", ltype) +
  		required("--num-threads", "16") +
  		required(genome) +
  		required(inBam) 
		
 //this.isIntermediate = true
}
