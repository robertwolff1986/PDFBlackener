package com.wolffr.PDFBlackener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.ws.Endpoint;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.TextPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wolffr.PDFBlackener.config.DetailBlackeningConfig;
import com.wolffr.PDFBlackener.exception.PDFBlackenerException;

class DetailBlackenPDFUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(DetailBlackenPDFUtil.class);

	protected static byte[] detailBlacken(byte[] pdf, List<DetailBlackeningConfig> detailBlackeningConfigList) throws PDFBlackenerException {
		try (PDDocument loadedPdf = PDDocument.load(pdf)) {
			for (DetailBlackeningConfig detailBlackeningConfig : detailBlackeningConfigList) {
				detailBlacken(loadedPdf, detailBlackeningConfig);
			}
			return getBytes(loadedPdf);
		} catch (InvalidPasswordException e) {
			LOGGER.error(String.format("Password protected pdf could not be opened, not yet implemented"), e.getMessage());
			throw new PDFBlackenerException(String.format("Password protected pdf could not be opened, not yet implemented", e.getMessage()));
		} catch (IOException ioe) {
			LOGGER.error(String.format("Could not read pdf"), ioe.getMessage());
			throw new PDFBlackenerException(String.format("Could not read pdf: %s", ioe.getMessage()));
		}
	}

	private static void detailBlacken(PDDocument loadedPdf, DetailBlackeningConfig detailBlackeningConfig) throws PDFBlackenerException {
		TextPosition startPosition = null;
		TextPosition endPosition = null;
		if (!StringUtils.isEmpty(detailBlackeningConfig.getStartMarkerText())) {
			startPosition = getPosition(loadedPdf, detailBlackeningConfig.getPageNr(), detailBlackeningConfig.getStartMarkerText());
		}
		if (!StringUtils.isEmpty(detailBlackeningConfig.getFinishMarkerText())) {
			endPosition = getPosition(loadedPdf, detailBlackeningConfig.getPageNr(), detailBlackeningConfig.getFinishMarkerText());
		}
		switch (detailBlackeningConfig.getBlackenType()) {
		case EXCLUDE:
			blackenExcluding(loadedPdf,detailBlackeningConfig.getPageNr(),startPosition, endPosition);
			break;
		case INCLUDE:
			blackenIncluding(loadedPdf,detailBlackeningConfig.getPageNr(),startPosition, endPosition);
			break;
		default:
			break;
		}
	}

	private static void blackenExcluding(PDDocument loadedPdf, Integer pageNumber, TextPosition startPosition, TextPosition endPosition)
			throws PDFBlackenerException {
		List<BlackenRect> blackenRects = new ArrayList<>();
		if (startPosition != null && endPosition == null)
			blackenRects.addAll(blackenAfterIncludingStartPosition(loadedPdf, startPosition, pageNumber));
		else if (endPosition != null && startPosition == null)
			blackenRects.addAll(blackenPriorIncludingEndPosition(loadedPdf, endPosition, pageNumber));
		else
			blackenRects.addAll(blackenExcludingStartAndEndPosition(loadedPdf, startPosition, endPosition, pageNumber));
		drawRects(loadedPdf, pageNumber, blackenRects);
	}

	private static void blackenIncluding(PDDocument loadedPdf, Integer pageNumber, TextPosition startPosition, TextPosition endPosition)
			throws PDFBlackenerException {
		List<BlackenRect> blackenRects = new ArrayList<>();
		if (startPosition != null)
			blackenRects.addAll(blackenPriorTextPosition(loadedPdf, startPosition, pageNumber));
		if (endPosition != null)
			blackenRects.addAll(blackenAfterTextPosition(loadedPdf, endPosition, pageNumber));
		drawRects(loadedPdf, pageNumber, blackenRects);
	}

	private static List<BlackenRect> blackenExcludingStartAndEndPosition(PDDocument loadedPdf, TextPosition startPosition, TextPosition endPosition,
			Integer pageNumber) {
			float x=10;
			float y=endPosition.getEndY()-endPosition.getHeight();
			float width=loadedPdf.getPage(pageNumber).getBBox().getWidth() - 2*x;
			float height=startPosition.getEndY()-endPosition.getEndY() + endPosition.getHeight()+startPosition.getHeight();
			return Arrays.asList(new BlackenRect(x, y, width, height));
	}

	private static List<BlackenRect> blackenAfterIncludingStartPosition(PDDocument loadedPdf, TextPosition startPosition, Integer pageNumber) {
			float x=10;
			float y=10;
			float width=loadedPdf.getPage(pageNumber).getBBox().getWidth() - 2*x;
			float height=loadedPdf.getPage(pageNumber).getBBox().getHeight()-startPosition.getY();
			return Arrays.asList(new BlackenRect(x, y, width, height));
	}
	
	private static List<BlackenRect> blackenPriorIncludingEndPosition(PDDocument loadedPdf, TextPosition startPosition, Integer pageNumber) {
			float x=10;
			float y=startPosition.getEndY()-startPosition.getHeight();
			float width=loadedPdf.getPage(pageNumber).getBBox().getWidth() - 2*x;
			float height=startPosition.getY();
			return Arrays.asList(new BlackenRect(x, y, width, height));
	}

	private static List<BlackenRect> blackenPriorTextPosition(PDDocument loadedPdf, TextPosition firstIncludedTextPosition, Integer pageNumber) {
			float x=10;
			float y=loadedPdf.getPage(pageNumber).getBBox().getHeight()	- (firstIncludedTextPosition.getYDirAdj() - (firstIncludedTextPosition.getHeight() * 1.5f));
			float width=loadedPdf.getPage(pageNumber).getBBox().getWidth() - 2*x;
			float height=loadedPdf.getPage(pageNumber).getBBox().getHeight() - 10 - (loadedPdf.getPage(pageNumber).getBBox().getHeight() - (firstIncludedTextPosition.getYDirAdj() - (firstIncludedTextPosition.getHeight() * 1.5f)));
			return Arrays.asList(new BlackenRect(x, y, width, height));
	}
	
	private static List<BlackenRect> blackenAfterTextPosition(PDDocument loadedPdf, TextPosition firstIncludedTextPosition, Integer pageNumber) {
			float x=10;
			float y=x;
			float width=loadedPdf.getPage(pageNumber).getBBox().getWidth() - 20;
			float height=firstIncludedTextPosition.getEndY()-x-3 ;
			return Arrays.asList(new BlackenRect(x, y, width, height));
	}
	
	private static void drawRects(PDDocument loadedPdf, Integer pageNumber, List<BlackenRect> rectList) throws PDFBlackenerException {
		try (PDPageContentStream contentStream = new PDPageContentStream(loadedPdf, loadedPdf.getPage(pageNumber - 1), AppendMode.APPEND, false)) {
			contentStream.setNonStrokingColor(GlobalParameterUtil.getPDFBlackenerConfig().getBlackeningConfig().getColor());
			for (BlackenRect rect : rectList) {
				LOGGER.info(rect.toString());
				contentStream.addRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
			}
			contentStream.fill();
		} catch (IOException ioe) {
			LOGGER.error(String.format("Could not blacken page %s", pageNumber), ioe.getMessage());
			throw new PDFBlackenerException(String.format("Could not blacken page %s : %s", pageNumber, ioe.getMessage()));
		}
	}
	
	private static TextPosition getPosition(PDDocument loadedPdf, Integer pageNr, String textToFind) throws PDFBlackenerException {
		return TextLocator.getTextPositionOnPage(loadedPdf, pageNr, textToFind);
	}

	private static byte[] getBytes(PDDocument loadedPdf) throws PDFBlackenerException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			loadedPdf.save(baos);
			return baos.toByteArray();
		} catch (IOException ioe) {
			LOGGER.error(String.format("Could not retrieve byte[] for blackenedPDF"), ioe.getMessage());
			throw new PDFBlackenerException(String.format("Could not retrieve byte[] for blackenedPDF: %s", ioe.getMessage()));
		}
	}

}