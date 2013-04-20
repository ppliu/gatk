package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class ClipAnalysis(@Input inBam: File, @Input inBed: File, @Argument species: String, @Output metrics: File) extends CommandLineFunction {

  def commandLine = "clip_analysis " +
    required("--clusters", inBed) +
    required("-s", species) +
    required("--bam", inBam) +
    required("--regions_location", "/nas3/lovci/projects/ucscBED/%s".format(species)) +
    required("--AS_Structure", "/nas3/yeolab/Genome/ensembl/AS_STRUCTURE/%sdata4".format(species)) +
    required("--genome_location", "/nas3/yeolab/Genome/ucsc/%s/chromosomes/all.fa".format(species)) +
    required("--phastcons_location", "/nas3/yeolab/Conservation/phastCons/hg19_46way/placentalMammals/reformat/hg19_phastcons.bw") +
    required("--motifs", "AAAAA") +
    required("--nrand", 3) +
    required("--runPhast") +
    required("--runMotif") +
    required("--runHomer") +
    required("--metrics", metrics)

}
