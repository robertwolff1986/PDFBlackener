package com.wolffr.PDFBlackener;

import java.util.List;

import com.wolffr.PDFBlackener.config.PDFBlackenerConfig;
import com.wolffr.PDFBlackener.exception.PDFBlackenerException;

public class PDFBlackenerImpl implements PDFBlackener {

	public PDFBlackenerImpl() {
		GlobalParameterUtil.initializeDefaultConfig();
	}

	public PDFBlackenerImpl(PDFBlackenerConfig config) {
		GlobalParameterUtil.setPDFBlackenerConfig(config);
	}

	@Override
	public byte[] blackenPDF(byte[] pdf, List<Integer> pagesToKeep, boolean imagify) throws PDFBlackenerException {
		byte[] blackenedPDF = BlackenPDFUtil.blackenPDF(pdf, pagesToKeep);
		return imagify ? ImagifyPDFUtil.imagifyPDF(blackenedPDF) : blackenedPDF;
	}

	@Override
	public byte[] findPagesAndBlackenPDF(byte[] pdf, List<String> stringsOnPagesThatShouldBeKepped, boolean imagify) throws PDFBlackenerException {
		List<Integer> pagesToKeep = PageSelector.findPagesThatContains(pdf, stringsOnPagesThatShouldBeKepped);
		return blackenPDF(pdf, pagesToKeep, imagify);
	}
}
