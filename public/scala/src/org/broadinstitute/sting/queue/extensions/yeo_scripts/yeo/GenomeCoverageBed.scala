package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class GenomeCoverageBed extends CommandLineFunction {

 @Input(doc="input bed file", shortName = "inBed", fullName = "input_bed_files", required = false) 
 var inBed: File = _
 
 @Input(doc="input bam file", shortName = "inBam", fullName = "input_bam_files", required = false) 
 var inBam: File = _
 
 @Argument(doc="file contating chromosome sizes", shortName = "genomeSize", fullName = "chromosome_sizes", required = true) 
 var genomeSize: String = _
 
 @Output(doc="output bed graph file", shortName = "bedGraph", fullName = "out_bed_graph", required = true) 
 var bedGraph: File = _
 
 @Argument(doc="use a single strand when generating the bed graph (+ / -)", shortName = "strand", fullName = "strand", required = false) 
 var strand: String = null
 
 @Argument(doc="split gapped alignments", shortName = "split", fullName = "split", required = false) 
 var split: Boolean = true 

 def commandLine = "genomeCoverageBed " + 
  		    optional("-strand", strand) + 
		    conditional(split, "-split") + 
		    required("-bg") + 
		    optional("-ibam", inBam) +
		    optional("-i", inBed) +  
		    required("-g", genomeSize) + " > " + bedGraph
 this.isIntermediate = true
}