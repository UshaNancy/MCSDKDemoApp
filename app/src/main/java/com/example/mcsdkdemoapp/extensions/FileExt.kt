package com.kobil.mcwmpgettingstarted.extensions

import android.content.Context
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


fun File?.zipFolder(destinationFile: File): File? {

    if (this != null && exists()) {
        if (destinationFile.exists()) {

            if (destinationFile.delete()) {
            } else {
            }
        }
        try {
            val logFiles =
                listFiles()?.filter { it.name.startsWith("ks_") && it.name.endsWith(".log") }
            val fos = FileOutputStream(destinationFile)
            val zos = ZipOutputStream(fos)

            logFiles?.forEach { file ->
                val buffer = ByteArray(1024)

                FileInputStream(file).use { fi ->
                    BufferedInputStream(fi).use { origin ->
                        val entry = ZipEntry(file.name).apply {
                            time = file.lastModified()
                            size = file.length()
                        }

                        zos.putNextEntry(entry)
                        while (true) {
                            val readBytes = origin.read(buffer)
                            if (readBytes == -1) {
                                break
                            }
                            zos.write(buffer, 0, readBytes)
                        }
                    }
                }
            }
            zos.closeEntry()
            zos.close()
            return destinationFile
        } catch (ioe: Exception) {
        }
    } else {
    }
    return null
}

fun readConfigFile(context: Context, configName: String, fileName: String): String? {
    val finalFileName = if (configName.isNotNullOrEmpty()) {
        "$configName${File.separator}$fileName"
    } else
        fileName
    if (checkAssetsFileExists(context = context, configName = configName, fileName = fileName)) {
        context.assets.open(finalFileName).bufferedReader().use {
            return it.readText()
        }
    } else {
        throw FileNotFoundException("$finalFileName file is missing!")
    }
}

/**
 * This method checks for the file exists within a subfolder of assets
 * @param configName: This is the name of the subfolder (within assets), if config name is passed as blank
 * then it would search for the file name directly in assets folder
 * @param fileName: this is the name of the file we have to check for existence
 */
fun checkAssetsFileExists(context: Context, configName: String, fileName: String): Boolean {
    val list: Array<String>?
    try {
        if (configName.isNotNullOrEmpty()) {
            list = context.assets.list(configName)
            if (!list.isNullOrEmpty()) {
                for (file in list) {
                    if (file == fileName) {
                        return true
                    }
                }
                return false
            } else return false
        } else {
            return context.assets.list("")!!.contains(fileName)
        }
    } catch (e: IOException) {
        return false
    }
}
