package org.example

import org.example.utils.flattenPdfHelper
import org.example.utils.flattenPdfJpegHelper
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val inputPath = "src/main/resources/data/Input.pdf"
    val outputPath = "src/main/resources/output/Output_Flattened.pdf"
    val outputPathJpeg = "src/main/resources/output/Output_Flattened_Jpeg.pdf"

    try {
        // The Actual size of inout file is 147 KB
        Files.deleteIfExists(Path.of(outputPath))
        Files.deleteIfExists(Path.of(outputPathJpeg))

        // lossless flattened
        // file size 1.3 MB
        flattenPdfHelper(inputPath, outputPath)
        println("PDF processing completed successfully!")

        // Jpeg flattened
        // file size 787 KB
        flattenPdfJpegHelper(inputPath, outputPathJpeg)
        println("PDF processing completed successfully with jpeg conversion")
    } catch (e: Exception) {
        println("Error processing PDF: ${e.message}")
        e.printStackTrace()
    }
}