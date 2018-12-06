package com.wolffr.PDFBlackener;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.TextPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wolffr.PDFBlackener.exception.PDFBlackenerException;

class TextLocator {
	private static final Logger LOGGER = LoggerFactory.getLogger(TextLocator.class);
	
	protected static  TextPosition getTextPositionOnPage(PDDocument pdf, Integer pageNr, String text) throws PDFBlackenerException {
		try {
			TextLocationStripper textLocationStripper = new TextLocationStripper();
			List<Map<String, TextPosition>> textPositionList = textLocationStripper.getTextPosititions(pdf,pageNr);
			return getTextFromTextPositionList(textPositionList,text);
		} catch (IOException ioe) {
			LOGGER.error(String.format("Could not retrieve textPosition '%s' from page %s",text,pageNr), ioe.getMessage());
			throw new PDFBlackenerException(String.format("Could not retrieve textPosition '%s' from page %s: %s",text,pageNr, ioe.getMessage()));
		}
	}

	private static TextPosition getTextFromTextPositionList(List<Map<String, TextPosition>> textPositionList, String text) {
		String textPositionListText = textPositionList.stream().map(map->map.keySet().iterator().next().toString()).collect(Collectors.joining(""));
		return textPositionList.get(textPositionListText.indexOf(text)).entrySet().iterator().next().getValue();
	}
}
