package org.example.utils

import org.apache.pdfbox.Loader
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory
import org.apache.pdfbox.rendering.PDFRenderer
import org.apache.pdfbox.rendering.ImageType
import java.io.File

fun flattenPdfHelper(inputPath: String, outputPath: String, dpi: Float = 300f) {
    Loader.loadPDF(File(inputPath)).use { srcDoc ->
        val renderer = PDFRenderer(srcDoc)

        PDDocument().use { destDoc ->
            for (i in 0 until srcDoc.numberOfPages) {
                val pageImage = renderer.renderImageWithDPI(i, dpi, ImageType.RGB)

                val page = PDPage(PDRectangle(pageImage.width.toFloat(), pageImage.height.toFloat()))
                destDoc.addPage(page)

                val pdImage = LosslessFactory.createFromImage(destDoc, pageImage)

                PDPageContentStream(destDoc, page).use { content ->
                    content.drawImage(
                        pdImage,
                        0f,
                        0f,
                        page.mediaBox.width,
                        page.mediaBox.height
                    )
                }
            }
            destDoc.save(outputPath)
        }
    }
    println("PDF flattened successfully: $outputPath")
}



fun flattenPdfJpegHelper(
    inputPath: String,
    outputPath: String,
    dpi: Float = 200f,
    jpegQuality: Float = 0.8f
) {
    Loader.loadPDF(File(inputPath)).use { srcDoc ->
        val renderer = PDFRenderer(srcDoc)

        PDDocument().use { destDoc ->
            for (i in 0 until srcDoc.numberOfPages) {
                val image = renderer.renderImageWithDPI(
                    i,
                    dpi,
                    ImageType.GRAY
                )

                val page = PDPage(
                    PDRectangle(image.width.toFloat(), image.height.toFloat())
                )
                destDoc.addPage(page)

                val pdImage = JPEGFactory.createFromImage(
                    destDoc,
                    image,
                    jpegQuality
                )

                PDPageContentStream(destDoc, page).use { content ->
                    content.drawImage(
                        pdImage,
                        0f,
                        0f,
                        page.mediaBox.width,
                        page.mediaBox.height
                    )
                }
            }
            destDoc.save(outputPath)
        }
    }
}

