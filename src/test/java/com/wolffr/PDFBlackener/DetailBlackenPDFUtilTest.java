package com.wolffr.PDFBlackener;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.wolffr.PDFBlackener.config.DetailBlackeningConfig;
import com.wolffr.PDFBlackener.exception.PDFBlackenerException;

class DetailBlackenPDFUtilTest {

	
	@Test
	@DisplayName("Detaillacken pdf")
	public  void testDetailBlackenPDF() throws IOException, PDFBlackenerException {
		GlobalParameterUtil.initializeDefaultConfig();
		DetailBlackeningConfig detailBlackeningConfig = new DetailBlackeningConfig(DetailBlackenMode.INCLUDE, "Eingangsformel", "Bundesgesetzblatt veröffentlicht:", 10, 10, 10, 1);
		byte[] pdf = Files.readAllBytes(Paths.get("src/test/resources/gg.pdf"));
		Integer nrPages = TestUtil.getNrPages(pdf);
		byte[] detailBlackanedPDF = DetailBlackenPDFUtil.detailBlacken(pdf, Arrays.asList(detailBlackeningConfig));
		Files.write(Paths.get("src/test/resources/gg_detail.pdf"), detailBlackanedPDF, StandardOpenOption.CREATE);
	}
	@Test
	@DisplayName("Detaillacken pdf")
	public  void testDetailBlackenPDF2() throws IOException, PDFBlackenerException {
		GlobalParameterUtil.initializeDefaultConfig();
		DetailBlackeningConfig detailBlackeningConfig = new DetailBlackeningConfig(DetailBlackenMode.EXCLUDE, "Eingangsformel", "Bundesgesetzblatt veröffentlicht:", 10, 10, 10, 1);
		byte[] pdf = Files.readAllBytes(Paths.get("src/test/resources/gg.pdf"));
		Integer nrPages = TestUtil.getNrPages(pdf);
		byte[] detailBlackanedPDF = DetailBlackenPDFUtil.detailBlacken(pdf, Arrays.asList(detailBlackeningConfig));
		Files.write(Paths.get("src/test/resources/gg_detail.pdf"), detailBlackanedPDF, StandardOpenOption.CREATE);
	}
}
