package com.wolffr.PDFBlackener;

import java.util.List;

import com.wolffr.PDFBlackener.exception.PDFBlackenerException;

public class PDFBlackenerImpl implements PDFBlackener {

	private static GlobalParameterUtil config;

	public PDFBlackenerImpl() {
	}

	public PDFBlackenerImpl(PDFBlackenerConfig config) {
		GlobalParameterUtil.setPDFBlackenerConfig(config);
	}

	@Override
	public byte[] blackenPDF(byte[] pdf, List<Integer> pagesToKeep, boolean imagify) throws PDFBlackenerException {
		byte[] blackenedPDF = BlackenPDFUtil.blackenPDF(pdf, pagesToKeep);

		if (imagify) {
			return ImagifyPDFUtil.imagifyPDF(blackenedPDF);
		}
		return blackenedPDF;
	}

	@Override
	public byte[] findPagesAndBlackenPDF(byte[] pdf, List<String> stringsOnPagesThatShouldBeKepped, boolean imagify) throws PDFBlackenerException {
		List<Integer> pagesToKeep = PageSelector.findPagesThatContains(pdf, stringsOnPagesThatShouldBeKepped);
		return blackenPDF(pdf, pagesToKeep, imagify);
	}
}
