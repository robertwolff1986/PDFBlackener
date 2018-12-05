package com.wolffr.PDFBlackener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import com.wolffr.PDFBlackener.exception.PDFBlackenerException;

public class ImagifyPDFUtilTest {

	@Test
	@DisplayName("Search string in pdf, imagify and search again")
	public void testImagifyPDF() throws IOException, PDFBlackenerException {
		GlobalParameterUtil.initializeDefaultConfig();
		byte[] pdf = Files.readAllBytes(Paths.get("src/test/resources/gg.pdf"));
		Integer nrPages= TestUtil.getNrPages(pdf);
		assertTrue("PDF should contain that text",TestUtil.pdfContainsText(pdf, " "));
		byte[] imagifiedPDF = ImagifyPDFUtil.imagifyPDF(pdf);
		assertFalse("PDF should not contain this text as it is imagified",TestUtil.pdfContainsText(imagifiedPDF, " "));
		assertEquals(nrPages,TestUtil.getNrPages(imagifiedPDF));
	}
}