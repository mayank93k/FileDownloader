package scala.spark.org.utility

import java.io.{BufferedInputStream, FileOutputStream}
import java.net.URL
import scala.spark.org.common.logger.Logging
import scala.util.{Failure, Success, Try}

object UtilityFunction extends Logging {
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
