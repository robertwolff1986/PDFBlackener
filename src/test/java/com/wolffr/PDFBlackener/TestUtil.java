package com.wolffr.PDFBlackener;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

public class TestUtil {

	protected static Integer getNrPages(byte[] pdf) throws InvalidPasswordException, IOException {
		try (PDDocument loadedPdf = PDDocument.load(pdf)) {
			return loadedPdf.getNumberOfPages();
		}
	}
	
	protected static boolean pdfContainsText(byte[] pdf, String text) throws InvalidPasswordException, IOException {
		try (PDDocument loadedPdf = PDDocument.load(pdf)) {
			for (int pageNumber = 0; pageNumber < loadedPdf.getNumberOfPages(); pageNumber++) {
				PDFTextStripper s = new PDFTextStripper();
				s.setStartPage(pageNumber);
				s.setEndPage(pageNumber);
				String pageText = s.getText(loadedPdf);
				if (pageText.contains(text)) {
					return true;
				}
			}
			return false;
		}
	}

}
