package com.wolffr.PDFBlackener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import com.wolffr.PDFBlackener.config.DetailBlackeningConfig;
import com.wolffr.PDFBlackener.exception.PDFBlackenerException;

public class BlackenPDFUtilTest {

	@Test
	@DisplayName("Blacken pdf")
	public  void testBlackenPDF() throws IOException, PDFBlackenerException {
		GlobalParameterUtil.initializeDefaultConfig();
		byte[] pdf = Files.readAllBytes(Paths.get("src/test/resources/gg.pdf"));
		Integer nrPages = TestUtil.getNrPages(pdf);
		byte[] blackenedPDF = BlackenPDFUtil.blackenPDF(pdf, Arrays.asList(1,2));
		assertTrue("resultLength should be > sourceLength as black reckts are added",pdf.length<blackenedPDF.length);
		assertEquals(nrPages,TestUtil.getNrPages(blackenedPDF));
	}
}
