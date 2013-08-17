package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class ClipAnalysis(@Input inBam: File, @Input inBed: File, @Argument species: String, @Output metrics: File,
      		   @Argument regions_location: String, @Argument AS_Structure: String, @Argument genome_location: String,
		   @Argument phastcons_location: String, @Argument gff_db: String, @Input bw_pos: File, @Input bw_neg: File ) extends CommandLineFunction {

  override def shortDescription = "CLIP_Analysis"
  def commandLine = "clip_analysis " +
    required("--clusters", inBed) +
    required("-s", species) +
    required("--bam", inBam) +
    required("--regions_location", regions_location) +
    required("--AS_Structure", AS_Structure) +
    required("--genome_location", genome_location) +
    required("--phastcons_location", phastcons_location) +
    //required("--motifs", "AAAAA") +
    required("--nrand", 3) +
    required("--runPhast") +
    required("--runMotif") +
    required("--runHomer") +
    required("--metrics", metrics) +
    required("--gff_db", gff_db) +
    required("--bw_pos", bw_pos) +
    required("--bw_neg", bw_neg) 

}
