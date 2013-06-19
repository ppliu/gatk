package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class SingleRPKM extends CommandLineFunction {

 @Input(doc="input count file", shortName = "inCount", fullName = "input_count_file", required = true) 
 var inCount: File = _

 @Output(doc="output RPKM file", shortName = "outRPKM", fullName = "out_rpkm_file", required = true) 
 var outRPKM: File = _
 
 @Argument(doc="species name", shortName = "s", fullName = "species", required = true) 
 var species: String = _
 
 def commandLine = "perl /nas3/ppliu/gscripts/pipeflower/single_RPKM.pl " + 
		   required("-input_1", inCount) + 
		   required("-output", outRPKM) + 
		   required("--species", species) 
		   
		    
 this.isIntermediate = false
}

