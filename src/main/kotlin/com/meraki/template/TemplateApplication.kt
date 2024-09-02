package com.meraki.template

import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import com.itextpdf.io.source.ByteArrayOutputStream
import com.meraki.template.dto.SampleData
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.server.ServerWebExchange
import org.thymeleaf.spring5.SpringWebFluxTemplateEngine
import org.thymeleaf.spring5.context.webflux.SpringWebFluxContext
import reactor.core.publisher.Mono
import java.io.ByteArrayInputStream

@SpringBootApplication
class SpringPdfApplication

fun main(args: Array<String>) {
    runApplication<SpringPdfApplication>(*args)
}
@Controller
@RequestMapping("/api/v1")
class PdfController(private val templateEngine: SpringWebFluxTemplateEngine) {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val sampleData =SampleData()

    @GetMapping("/view-letter")
    fun viewLetter(): Mono<String> {
        return Mono.just("letter")
    }

    @GetMapping("/pdf")
    fun pdf(exchange: ServerWebExchange): Mono<ResponseEntity<ByteArray>> {
        val context = SpringWebFluxContext(exchange)
        val html = templateEngine.process("letter", context) ?: return Mono.error(
            NullPointerException("Template processing returned null")
        )
        val pdf = ByteArrayOutputStream()

        // Convert HTML to PDF
        val inputStream = ByteArrayInputStream(html.toByteArray())
        HtmlConverter.convertToPdf(inputStream, pdf, ConverterProperties().apply {
            baseUri = "http://localhost:8080"  // Adjust if necessary
        })

        // Create and return ResponseEntity
        return Mono.just(
            ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=letter.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf.toByteArray())
        )
    }
    @GetMapping("/generate-pdf")
    fun generatePdf(exchange: ServerWebExchange): Mono<ResponseEntity<ByteArray>>{
        val context = SpringWebFluxContext(exchange).apply {
            setVariable("customer", sampleData.createSampleCustomer())
            setVariable("quoteItems", sampleData.createSampleQuoteItems())
        }

        val html = templateEngine.process("quotation", context) ?: return Mono.error(
            NullPointerException("Template processing returned null")
        )
        val pdf = ByteArrayOutputStream()

        // Convert HTML to PDF
        val inputStream = ByteArrayInputStream(html.toByteArray())
        HtmlConverter.convertToPdf(inputStream, pdf, ConverterProperties().apply {
            baseUri = "http://localhost:8080"  // Adjust if necessary
        })

        // Create and return ResponseEntity
        return Mono.just(
            ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=quotation.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf.toByteArray())
        )
    }
}




