package scala.spark.org.utility

import java.io.{BufferedInputStream, FileOutputStream}
import java.net.URL
import scala.io.Source
import scala.spark.org.common.logger.Logging
import scala.util.matching.Regex
import scala.util.{Failure, Success, Try}

object UtilityFunction extends Logging {
  /**
   * Method to scrape files from a URL and download them.
   *
   * @param url         : The URL from which to scrape files.
   * @param downloadDir : The local directory to save downloaded files.
   */
  def scrapeAndDownloadFiles(url: String, downloadDir: String): Unit = {
    logger.info("Started processing scrapes and download file method.")
    Try {
      // Fetch the HTML content from the URL
      val content = Source.fromURL(url)

      // Define a regex pattern to find file links (adjust if necessary)
      val fileLinkPattern: Regex = """href="([^"]*?\.(zip|csv|txt))"""".r

      // Extract file URLs and download each one
      val fileUrls = fileLinkPattern.findAllMatchIn(content.mkString).map { m =>
        val relativeUrl = m.group(1)
        // Create absolute URL for the file
        if (relativeUrl.startsWith("http")) relativeUrl else s"$url$relativeUrl"
      }

      // Download each file found
      fileUrls.foreach { fileUrl =>
        logger.info(s"Attempting to download: $fileUrl")
        downloadFile(fileUrl, downloadDir)
      }
    } match {
      case Success(_) => logger.info(s"Files scraped and downloaded from $url")
      case Failure(ex) => logger.error(s"An error occurred while scraping $url: ${ex.getMessage}")
    }
  }

  /**
   * This method to download a file from a given URL to a specific directory
   *
   * @param fileUrl   : The URL of the file to download.
   * @param directory : The local directory to save the download file.
   */
  def downloadFile(fileUrl: String, directory: String): Unit = {
    logger.info("Started processing download file method to download a file from a given URL")
    Try {
      val url = new URL(fileUrl)
      val fileName = fileUrl.split("/").last
      val connection = url.openConnection() //.asInstanceOf[HttpURLConnection]
      val inputStream = new BufferedInputStream(connection.getInputStream)
      val targetPath = s"$directory/$fileName"

      // Create the output file and write the input stream to it with a 2 GB buffer
      val outputStream = new FileOutputStream(targetPath)
      val bufferSize = 2 * 1024 //* 1024 * 1024 // 2 GB buffer size in bytes
      val buffer = new Array[Byte](bufferSize)

      Iterator.continually(inputStream.read(buffer))
        .takeWhile(_ != -1)
        .foreach(outputStream.write(buffer, 0, _))

      inputStream.close()
      outputStream.close()
      logger.info(s"Downloaded file name is: $fileName")
    } match {
      case Success(_) =>
      case Failure(ex) => logger.error(s"Failed to download $fileUrl: ${ex.getMessage}")
    }
    logger.info("Finished processing downloading file method.")
  }
}
