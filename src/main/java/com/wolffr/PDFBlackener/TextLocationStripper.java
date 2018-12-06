package com.wolffr.PDFBlackener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

class TextLocationStripper extends PDFTextStripper {

	private List<Map<String, TextPosition>> textList;

	public TextLocationStripper() throws IOException {
		super();
	}

	protected List<Map<String, TextPosition>> getTextPosititions(PDDocument document, Integer pageNr) throws IOException {
			setSortByPosition(true);
			setStartPage(pageNr);
			setEndPage(pageNr);
			Writer osw = new OutputStreamWriter(new ByteArrayOutputStream());
			textList = new ArrayList<>();
			writeText(document, osw);
			return textList;
	}

	@Override
	protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
		for (TextPosition text : textPositions) {
			textList.add(Map.of(text.getUnicode(), text));
		}
	}
}