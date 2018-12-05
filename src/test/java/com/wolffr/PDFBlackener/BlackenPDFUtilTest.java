package com.wolffr.PDFBlackener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import org.junit.Test;

import com.wolffr.PDFBlackener.exception.PDFBlackenerException;

public class BlackenPDFUtilTest {

	@Test
	public  void testBlackenPDF() throws IOException, PDFBlackenerException {
		PDFBlackener blackener = new PDFBlackenerImpl();
		byte[] pdf = Files.readAllBytes(Paths.get("src/test/resources/gg.pdf"));
		byte[] blackenedPDF = BlackenPDFUtil.blackenPDF(pdf, Arrays.asList(1,2));
		Files.write(Paths.get("src/test/resources/gg_blackened.pdf"), blackenedPDF, StandardOpenOption.CREATE);
	}

}
