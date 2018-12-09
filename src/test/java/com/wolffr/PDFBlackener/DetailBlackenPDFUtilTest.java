package com.wolffr.PDFBlackener;

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
		DetailBlackeningConfig detailBlackeningConfig = new DetailBlackeningConfig(DetailBlackenMode.EXCLUDE, "Eingangsformel", null, 10, 10, 10, 1);
		DetailBlackeningConfig detailBlackeningConfig2= new DetailBlackeningConfig(DetailBlackenMode.EXCLUDE, null, "Auf Grund dieser Feststellung hat", 10, 10, 10, 1);
		byte[] pdf = Files.readAllBytes(Paths.get("src/test/resources/gg.pdf"));
		Integer nrPages = TestUtil.getNrPages(pdf);
		byte[] detailBlackanedPDF = DetailBlackenPDFUtil.detailBlacken(pdf, Arrays.asList(detailBlackeningConfig,detailBlackeningConfig2));
		Files.write(Paths.get("src/test/resources/gg_detail.pdf"), detailBlackanedPDF, StandardOpenOption.CREATE);
	}
	
	@Test
	@DisplayName("Detaillacken pdf")
	public  void testDetailBlackenPDF3() throws IOException, PDFBlackenerException {
		GlobalParameterUtil.initializeDefaultConfig();
		DetailBlackeningConfig detailBlackeningConfig = new DetailBlackeningConfig(DetailBlackenMode.EXCLUDE, "Art 3", "Behinderung benachteiligt werden.", 10, 10, 10, 2);
		DetailBlackeningConfig detailBlackeningConfig2 = new DetailBlackeningConfig(DetailBlackenMode.EXCLUDE, "Art 5", "zur Verfassung.", 10, 10, 10, 2);
		DetailBlackeningConfig detailBlackeningConfig3 = new DetailBlackeningConfig(DetailBlackenMode.EXCLUDE, "Art 1", "unmittelbar geltendes Recht.", 10, 10, 10, 1);
		byte[] pdf = Files.readAllBytes(Paths.get("src/test/resources/gg.pdf"));
		Integer nrPages = TestUtil.getNrPages(pdf);
		byte[] detailBlackanedPDF = DetailBlackenPDFUtil.detailBlacken(pdf, Arrays.asList(detailBlackeningConfig,detailBlackeningConfig2,detailBlackeningConfig3));
		Files.write(Paths.get("src/test/resources/gg_detail.pdf"), detailBlackanedPDF, StandardOpenOption.CREATE);
	}
	
	@Test
	@DisplayName("Detaillacken pdf")
	public  void testDetailBlackenPDFMultiple() throws IOException, PDFBlackenerException {
		GlobalParameterUtil.initializeDefaultConfig();
		DetailBlackeningConfig detailBlackeningConfig = new DetailBlackeningConfig(DetailBlackenMode.INCLUDE, "Art 3", "Behinderung benachteiligt werden.", 10, 10, 10, 2);
		DetailBlackeningConfig secondDetailBlackeningConfig = new DetailBlackeningConfig(DetailBlackenMode.INCLUDE, "Art 5", "zur Verfassung.", 10, 10, 10, 2);
		byte[] pdf = Files.readAllBytes(Paths.get("src/test/resources/gg.pdf"));
		Integer nrPages = TestUtil.getNrPages(pdf);
		byte[] detailBlackanedPDF = DetailBlackenPDFUtil.detailBlacken(pdf, Arrays.asList(detailBlackeningConfig,secondDetailBlackeningConfig));
		Files.write(Paths.get("src/test/resources/gg_detail.pdf"), detailBlackanedPDF, StandardOpenOption.CREATE);
	}
}
