package com.wolffr.PDFBlackener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

import com.wolffr.PDFBlackener.exception.PDFBlackenerException;

public class ImagifyPDFUtilTest {

	@Test
	public void testImagifyPDF() throws IOException, PDFBlackenerException {
		byte[] pdf = Files.readAllBytes(Paths.get("src/test/resources/gg.pdf"));
		ImagifyPDFUtil imagifyPDFUtil = new ImagifyPDFUtil();
		byte[] imagifiedPDF = imagifyPDFUtil.imagifyPDF(pdf);
		Files.write(Paths.get("src/test/resources/gg_imagified.pdf"), imagifiedPDF, StandardOpenOption.CREATE);
	}

}
