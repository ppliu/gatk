package org.broadinstitute.sting.queue.qscripts

import org.broadinstitute.sting.queue.QScript
import org.broadinstitute.sting.queue.util.QScriptUtils
import net.sf.samtools.SAMFileHeader.SortOrder
import org.broadinstitute.sting.utils.exceptions.UserException
import org.broadinstitute.sting.commandline.Hidden
import org.broadinstitute.sting.queue.extensions.picard.{ ReorderSam, SortSam, AddOrReplaceReadGroups, MarkDuplicates }
import org.broadinstitute.sting.queue.extensions.samtools._
import org.broadinstitute.sting.queue.extensions.gatk._
import org.broadinstitute.sting.queue.util.TsccUtils._
import org.broadinstitute.sting.queue.extensions.yeo._

class AsdRNASeq extends QScript {
  // Script argunment
  @Input(doc = "input file or txt file of input files")
  var input: File = _

  @Input(doc = "sailfish index")
  var sailindex: File = _

  @Input(doc = "cufflinks reference genome")
  var cuffref: File = _

  @Argument(doc = "adapter to trim", required=false)
  var adapter: List[String] = Nil

  @Argument(doc = "flipped", required = false)
  var flipped: String = "none"

  @Argument(doc = "not stranded", required = false)
  var not_stranded: Boolean = false

  @Argument(doc = "strict triming run")
  var strict: Boolean = false

  @Argument(doc = "start processing run from bam file")
  var fromBam: Boolean = false
    
  case class sortSam(inSam: File, outBam: File, sortOrderP: SortOrder) extends SortSam {
    override def shortDescription = "sortSam"
    this.input :+= inSam
    this.output = outBam
    this.sortOrder = sortOrderP
    this.createIndex = true
  }

  case class filterRepetitiveRegions(noAdapterFastq: File, filteredResults: File, filteredFastq: File) extends FilterRepetitiveRegions {
       override def shortDescription = "FilterRepetitiveRegions"

       this.inFastq = noAdapterFastq
       this.outCounts = filteredResults
       this.outNoRep = filteredFastq
       this.isIntermediate = true
  }

  case class singleRPKM(input: File, output: File, s: String) extends SingleRPKM {
	this.inCount = input
	this.outRPKM = output
  }

  case class countTags(input: File, index: File, output: File, species: String) extends CountTags {
	this.inBam = input
	this.outCount = output
	this.tags_annotation = exonLocation(species)
	this.flip = flipped
  }

  case class star(input: File, output: File, stranded : Boolean, paired : File = null, species: String) extends STAR {
       this.inFastq = input

       if(paired != null) {
        this.inFastqPair = paired
       }

       this.outSam = output
       //intron motif should be used if the data is not stranded
       this.intronMotif = stranded
       this.genome = starGenomeLocation(species) 
  }

 
  case class samtoolsIndexFunction(input: File, output: File) extends SamtoolsIndexFunction {
       override def shortDescription = "indexBam"
       this.bamFile = input
       this.bamFileIndex = output
  }

  case class cutadapt(fastqFile: File, noAdapterFastq: File, adapterReport: File, adapter: List[String]) extends Cutadapt{
       override def shortDescription = "cutadapt"

       this.inFastq = fastqFile
       this.outFastq = noAdapterFastq
       this.report = adapterReport
       this.anywhere = adapter ++ List("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                                     "TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT")
       this.overlap = 5
       this.length = 18
       this.quality_cutoff = 6
       this.isIntermediate = true
   }

  case class sailfishquant(input: File, paired: File = null, output: File, indexFile: File, libraryType: String) extends SailfishQuant{

    this.inFastq = input
    this.inFastqPair = paired
    this.outbase = output
    this.index = indexFile
    this.ltype = libraryType

  }

  case class cuffquant(input: File, genomeFile: File, libraryType: String) extends CuffQuant{

    this.inBam = input
    this.genome = genomeFile
    this.ltype = libraryType

  }

 @Argument(doc="reads are single ended", shortName = "single_end", fullName = "single_end", required = false)
 var singleEnd: Boolean = true

def stringentJobs(fastqFile: File) : File = {

        // run if stringent
      val noPolyAFastq = swapExt(fastqFile, ".fastq", ".polyATrim.fastq")
      val noPolyAReport = swapExt(noPolyAFastq, ".fastq", ".metrics")
      val noAdapterFastq = swapExt(noPolyAFastq, ".fastq", ".adapterTrim.fastq")
      val adapterReport = swapExt(noAdapterFastq, ".fastq", ".metrics")
      val filteredFastq = swapExt(noAdapterFastq, ".fastq", ".rmRep.fastq")
      val filtered_results = swapExt(filteredFastq, ".fastq", ".metrics")
            //filters out adapter reads
      add(cutadapt(fastqFile = fastqFile, noAdapterFastq = noAdapterFastq, 
          adapterReport = adapterReport, 
          adapter = adapter))
          
          
      add(filterRepetitiveRegions(noAdapterFastq, filtered_results, filteredFastq))
      add(new FastQC(filteredFastq))

      return filteredFastq
}


def script() {

    val fileList = QScriptUtils.createArgsFromFile(input)
    var trackHubFiles: List[File] = List()
    var splicesFiles: List[File] = List()
    var bamFiles: List[File] = List()
    
    for (item : Tuple3[File, String, String] <- fileList) {
      var fastqFiles = item._1.toString().split(""";""")
      var species = item._2
      var fastqFile: File = new File(fastqFiles(0))
      var fastqPair: File = null
      var singleEnd = true
      var samFile: File = null
      if(!fromBam) {
      	if (fastqFiles.length == 2){
           singleEnd = false
           fastqPair = new File(fastqFiles(1))
	   add(new FastQC(inFastq = fastqPair))
      	}
            
     	add(new FastQC(inFastq = fastqFile))

      	var filteredFastq: File = null
      	if(strict && fastqPair == null) {
       		  filteredFastq = stringentJobs(fastqFile)
      	} else {
          filteredFastq = fastqFile
      	}
	samFile = swapExt(filteredFastq, ".fastq", ".sam")
      	if(fastqPair != null) { //if paired	
           add(new star(filteredFastq, samFile, not_stranded, fastqPair, species = species))
      	} else { //unpaired
           add(new star(filteredFastq, samFile, not_stranded, species = species))
      	}

	// run regardless of stringency
      	
      } else {
      	samFile = new File(fastqFiles(0))
      }

      val sortedBamFile = swapExt(samFile, ".sam", ".sorted.bam")
      val indexedBamFile = swapExt(sortedBamFile, "", ".bai")
      
      val countFile = swapExt(sortedBamFile, "bam", "count")
      val RPKMFile = swapExt(countFile, "count", "rpkm")
      val sailFishFile = swapExt(samFile, ".fastq.gz.sam", ".sail")     

      bamFiles = bamFiles ++ List(sortedBamFile)
      

      add(new sortSam(samFile, sortedBamFile, SortOrder.coordinate))
      add(new samtoolsIndexFunction(sortedBamFile, indexedBamFile))
      add(new countTags(input = sortedBamFile, index = indexedBamFile, output = countFile, species = species))	
      add(new singleRPKM(input = countFile, output = RPKMFile, s = species))

      add(new sailfishquant(input=fastqFile, paired=fastqPair, output=sailFishFile, indexFile=sailindex, libraryType="T=PE"))
      add(new cuffquant(input=sortedBamFile, genomeFile=cuffref, libraryType="fr-firststrand"))
    }

  } 
 
}






