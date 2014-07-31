package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class SingleRPKM extends CommandLineFunction {
 
 def rpkm_loc = "/oasis/tscc/scratch/yeo-lab/ace_progenity/asd_processing/code_repository/ASDAnalysis/python_src/rpkm_from_counts.py "

 @Input(doc="input count file", shortName = "inCount", fullName = "input_count_file", required = true) 
 var inCount: File = _

 @Output(doc="output RPKM file", shortName = "outRPKM", fullName = "out_rpkm_file", required = true) 
 var outRPKM: File = _
 
 override def shortDescription = "SingleRPKM" 
 def commandLine = rpkm_loc + 
		   required("--input", inCount) + 
		   required("--output", outRPKM)  
		   
		   
		    
 this.isIntermediate = false
}

