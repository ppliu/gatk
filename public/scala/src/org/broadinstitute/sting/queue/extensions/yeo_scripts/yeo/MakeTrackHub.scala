package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class MakeTrackHub(@Input bwFiles: List[File], @Argument location: String, @Argument genome: String) extends CommandLineFunction {
  
  override def shortDescription = "MakeTrackHub"
  def commandLine = "make_trackhubs.py" + 
      		     repeat(bwFiles) + 
		     required("--hub", location) +
		     required("--genome", genome)

}