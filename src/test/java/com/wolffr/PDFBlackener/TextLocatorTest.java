package com.wolffr.PDFBlackener;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.TextPosition;
import org.junit.jupiter.api.Test;

import com.wolffr.PDFBlackener.exception.PDFBlackenerException;

class TextLocatorTest {

	@Test
	void testGetTextPositionOnPage() throws PDFBlackenerException, IOException {
		TextPosition position= TextLocator.getTextPositionOnPage(PDDocument.load(Files.readAllBytes(Paths.get("src/test/resources/gg.pdf"))), 1, "Die Würde des Menschen ist unantastbar.");
		System.out.println(position.getXDirAdj() + "/" + position.getYDirAdj() + " -> " + position.getHeight());
	}

}
