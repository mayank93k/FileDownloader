package scala.spark.org.common.constant

object ApplicationConstant {
  val BaseUrl = "http://200.152.38.155/CNPJ/dados_abertos_cnpj/"
  val DownloadDirectory = "downloads"
  val FileLinkPattern = """href="([^"]*?\.(zip|csv|txt))""""
  val DatePattern = """href="(\d{4}-\d{2}/)""""
}
