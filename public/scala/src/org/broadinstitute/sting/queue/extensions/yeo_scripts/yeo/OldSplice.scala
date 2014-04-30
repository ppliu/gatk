package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class OldSplice extends CommandLineFunction {

 @Input(doc="input bam file", shortName = "inBam", fullName = "input_bam_file", required = true) 
 var inBam: File = _

 @Output(doc="output file", shortName = "outFile", fullName = "out_file", required = true) 
 var out_file: File = _
 
 @Argument(doc="species name", shortName = "s", fullName = "species", required = true) 
 var in_species: String = _
 
 @Argument(doc="Splice Type", shortName = "s", fullName = "splice_type", required = true) 
 var splice_type: List[String] = Nil
 
 @Argument(doc="flip the strands", shortName = "f", fullName = "flip", required = false) 
 var flip: Boolean = _
 
 def commandLine = "oldsplice.py " + 
         	   required("--bam", inBam) + 
		   required("--species", in_species) + 
		   conditional(flip, "--flip") + 
		   required("--outfile", out_file) +
		   repeat("--splice_type", splice_type) +
		   required("--processors", 16)
																	 
 this.isIntermediate = false
}