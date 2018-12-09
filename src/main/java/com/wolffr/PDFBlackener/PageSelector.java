package com.wolffr.PDFBlackener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wolffr.PDFBlackener.exception.PDFBlackenerException;

class PageSelector {
	private static final Logger LOGGER = LoggerFactory.getLogger(PageSelector.class);

	protected static List<Integer> findPagesThatContains(byte[] pdf, List<String> stringsOnPagesThatShouldBeKepped) throws PDFBlackenerException {
		List<Integer> pages = new ArrayList<>();
		try (PDDocument loadedPdf = PDDocument.load(pdf)) {
			for (int pageNumber = 1; pageNumber < loadedPdf.getNumberOfPages(); pageNumber++) {
				PDFTextStripper s = new PDFTextStripper();
				s.setStartPage(pageNumber);
				s.setEndPage(pageNumber);
				String pageText = s.getText(loadedPdf);
				if (stringsOnPagesThatShouldBeKepped.stream().anyMatch(searchString -> pageText.contains(searchString)))
					pages.add(pageNumber);
			}
		} catch (InvalidPasswordException e) {
			LOGGER.error(String.format("Password protected pdf could not be opened, not yet implemented"), e.getMessage());
			throw new PDFBlackenerException(String.format("Password protected pdf could not be opened, not yet implements", e.getMessage()));
		} catch (IOException ioe) {
			LOGGER.error(String.format("Could not read pdf"), ioe.getMessage());
			throw new PDFBlackenerException(String.format("Could not read pdf: %s", ioe.getMessage()));
		}
		return pages;
	}

}
