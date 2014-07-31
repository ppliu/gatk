package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class CountTags extends CommandLineFunction {
 
 def counttags_loc = "/oasis/tscc/scratch/yeo-lab/ace_progenity/asd_processing/code_repository/ASDAnalysis/python_src/count_tags.py "

 @Input(doc="input bam file", shortName = "inBam", fullName = "input_bam_file", required = true) 
 var inBam: File = _

 @Output(doc="output count file", shortName = "outCount", fullName = "out_count_file", required = true) 
 var outCount: File = _
 
 @Argument(doc="annotation file to get counts from", shortName = "a", fullName = "tags_annotation", required = true) 
 var tags_annotation: String = _
 
 @Argument(doc="flip the strands", shortName = "f", fullName = "flip", required = false) 
 var flip: String = _

 this.nCoresRequest = Option(16)
 override def shortDescription = "count_tags"  
 def commandLine = counttags_loc + 
  		    required("--bam_file", inBam) + 
		    required("--annotation_file", tags_annotation) +
		    optional("--flip", flip) +
		    required("--out_file", outCount)  
		    
 this.isIntermediate = false
}

