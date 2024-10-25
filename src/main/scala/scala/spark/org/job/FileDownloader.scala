package scala.spark.org.job

import java.io.File
import scala.spark.org.common.constant.ApplicationConstant._
import scala.spark.org.utility.UtilityFunction._

object FileDownloader {

  // Ensure the download directory exists
  new File(DownloadDirectory).mkdirs()

  def main(args: Array[String]): Unit = {
    // Find the most recent date directory
    val mostRecentDate = findMostRecentDate(BaseUrl)

    mostRecentDate.foreach { date =>
      val mostRecentUrl = s"$BaseUrl$date/"
      val specificDownloadDirectory = s"$DownloadDirectory/$date"

      // Create the specific download directory for the recent date
      new File(specificDownloadDirectory).mkdirs()

      logger.info(s"Scraping and downloading files from: $mostRecentUrl")
      scrapeAndDownloadFiles(mostRecentUrl, specificDownloadDirectory)
    }
  }
}
