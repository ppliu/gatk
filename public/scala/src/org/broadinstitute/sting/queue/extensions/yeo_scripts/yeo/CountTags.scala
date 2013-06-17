package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class CountTags extends CommandLineFunction {

 @Input(doc="input bam file", shortName = "inBam", fullName = "input_bam_file", required = true) 
 var inBam: File = _

 @Output(doc="output count file", shortName = "outCount", fullName = "out_count_file", required = true) 
 var outCount: File = _
 
 @Argument(doc="species name", shortName = "s", fullName = "species", required = true) 
 var species: String = _
 
 @Output(doc="flip the strands", shortName = "f", fullName = "flip", required = false) 
 var flip: String = _
 
 def commandLine = "python /nas3/ppliu/gscripts/count_tags_c.py " + 
  		    required("--bam_file", inBam) + 
		    required("--species", species) + 
		    optional("--flip", flip) +
		    required("--outCount", outCount)  
		    
 this.isIntermediate = false
}

