package com.wolffr.PDFBlackener;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.wolffr.PDFBlackener.exception.PDFBlackenerException;

public class PDFBlackenerImplTest {

	@Test
	public void testBlackenPDF() throws IOException, PDFBlackenerException {
		PDFBlackener blackener = new PDFBlackenerImpl();
		byte[] pdf = Files.readAllBytes(Paths.get("src/test/resources/ab.pdf"));
		byte[] result= blackener.blackenPDF(pdf, Arrays.asList(0), true);
		Files.write(Paths.get("src/test/resources/result.pdf"), result, StandardOpenOption.CREATE);
	}
	
	@Test
	public void testBlackenPDFWithConfig() throws IOException, PDFBlackenerException {
		PDFBlackener blackener = new PDFBlackenerImpl(PDFBlackenerConfig.Builder.createConfig().setBlackeningConfig(new BlackeningConfig(10, 10, 80, 80)).build());
		byte[] pdf = Files.readAllBytes(Paths.get("src/test/resources/gg.pdf"));
		byte[] result= blackener.blackenPDF(pdf, Arrays.asList(0), true);
		Files.write(Paths.get("src/test/resources/result.pdf"), result, StandardOpenOption.CREATE);
	}

	@Test
	void testFindPagesAndBlackenPDF() {
		fail("Not yet implemented");
	}

}
