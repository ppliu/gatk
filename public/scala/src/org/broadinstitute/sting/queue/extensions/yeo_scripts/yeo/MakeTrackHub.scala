package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class MakeTrackHub(@Input bwFiles: List[File], @Argument location: String) extends CommandLineFunction {
  //@Input(doc="Bam file to sort") 
  //var bwFiles: List[File] = Nil 

  //@Argument(doc="Location") 
  //var location: String = _

  def commandLine = "python /nas3/gpratt/gscripts/make_trackhubs.py" + repeat(bwFiles) + required("--location", location)

}