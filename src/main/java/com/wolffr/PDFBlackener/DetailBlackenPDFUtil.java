package com.wolffr.PDFBlackener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
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

			for (int pageNumber = 1; pageNumber < loadedPdf.getNumberOfPages(); pageNumber++) {
				final int currentPage = pageNumber;
				List<DetailBlackeningConfig> configs = detailBlackeningConfigList.stream().filter(config -> config.getPageNr() == currentPage)
						.collect(Collectors.toList());
				detailBlacken(loadedPdf, configs);
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

	private static void detailBlacken(PDDocument loadedPdf, List<DetailBlackeningConfig> detailBlackeningConfigList) throws PDFBlackenerException {
		List<Rect> keepRects = new ArrayList<>();
		for (DetailBlackeningConfig detailBlackeningConfig : detailBlackeningConfigList) {
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
				keepRects.addAll(keepIncluding(loadedPdf, detailBlackeningConfig.getPageNr(), startPosition, endPosition));
				break;
			case INCLUDE:
				keepRects.addAll(blackenIncluding(loadedPdf, detailBlackeningConfig.getPageNr(), startPosition, endPosition));
				break;
			default:
				break;
			}
		}
		if (!keepRects.isEmpty()) {
			List<Rect> blackenRects = calcBlackenRects(keepRects,loadedPdf.getPage(0).getBBox().getHeight(),loadedPdf.getPage(0).getBBox().getWidth());
			drawRects(loadedPdf, detailBlackeningConfigList.get(0).getPageNr(), blackenRects);
		}
	}

	private static List<Rect> calcBlackenRects(List<Rect> keepRects, float height,float width) {
		List<Rect> blackenRectList = new ArrayList<>();
			for (float y = 10; y < height - 10; y++) {
				boolean draw=true;
				for (Rect rect : keepRects) {
					if (rect.getY()< y &&(rect.getY()+rect.getHeight())>y) {
						draw=false;
					}
				}
				if(draw)
					blackenRectList.add(new Rect(10, y - 1, width - 20, 1));
			}
			return blackenRectList;
	}

	private static Collection<? extends Rect> keepIncluding(PDDocument loadedPdf, Integer pageNumber, TextPosition startPosition,
			TextPosition endPosition) {
		List<Rect> blackenRects = new ArrayList<>();
		if (startPosition != null && endPosition == null)
			blackenRects.addAll(keepIncludingStartPosition(loadedPdf, startPosition, pageNumber));
		else if (endPosition != null && startPosition == null)
			blackenRects.addAll(keepPriorIncludingEndPosition(loadedPdf, endPosition, pageNumber));
		else
			blackenRects.addAll(keepIncludingStartAndEndPosition(loadedPdf, startPosition, endPosition, pageNumber));
		return blackenRects;
	}

	private static List<Rect> keepIncludingStartAndEndPosition(PDDocument loadedPdf, TextPosition startPosition, TextPosition endPosition,
			Integer pageNumber) {
		float x = 10;
		float y = endPosition.getEndY() - endPosition.getHeight();
		float width = loadedPdf.getPage(pageNumber).getBBox().getWidth() - 2 * x;
		float height = startPosition.getEndY() - endPosition.getEndY() + endPosition.getHeight() + startPosition.getHeight();
		return Arrays.asList(new Rect(x, y, width, height));
	}

	private static List<Rect> keepIncludingStartPosition(PDDocument loadedPdf, TextPosition startPosition, Integer pageNumber) {
		float x = 10;
		float y = 10;
		float width = loadedPdf.getPage(pageNumber).getBBox().getWidth() - 2 * x;
		float height = startPosition.getEndY()-y;
		return Arrays.asList(new Rect(x, y, width, height));
	}

	private static List<Rect> keepPriorIncludingEndPosition(PDDocument loadedPdf, TextPosition startPosition, Integer pageNumber) {
		float x = 10;
		float y = startPosition.getEndY() - startPosition.getHeight();
		float width = loadedPdf.getPage(pageNumber).getBBox().getWidth() - 2 * x;
		float height = startPosition.getY();
		return Arrays.asList(new Rect(x, y, width, height));
	}
	
	
	private static Collection<? extends Rect> blackenIncluding(PDDocument loadedPdf, Integer pageNumber, TextPosition startPosition,
			TextPosition endPosition) {
		List<Rect> rectsToKeep = new ArrayList<>();
		if (startPosition != null && endPosition==null)
			rectsToKeep.addAll(keepPriorTextPosition(loadedPdf, startPosition, pageNumber));
		if (endPosition != null)
			rectsToKeep.addAll(keepAfterTextPosition(loadedPdf, endPosition, pageNumber));
		return rectsToKeep;
	}

	private static List<Rect> keepPriorTextPosition(PDDocument loadedPdf, TextPosition firstIncludedTextPosition, Integer pageNumber) {
		float x = 10;
		float y = 10;
		float width = loadedPdf.getPage(pageNumber).getBBox().getWidth() - 2 * x;
		float height = firstIncludedTextPosition.getEndY();
		return Arrays.asList(new Rect(x, y, width, height));
	}

	private static List<Rect> keepAfterTextPosition(PDDocument loadedPdf, TextPosition firstIncludedTextPosition, Integer pageNumber) {
		float x = 10;
		float y = x;
		float width = loadedPdf.getPage(pageNumber).getBBox().getWidth() - 20;
		float height = firstIncludedTextPosition.getEndY() - x - 3;
		return Arrays.asList(new Rect(x, y, width, height));
	}

	private static void drawRects(PDDocument loadedPdf, Integer pageNumber, List<Rect> rectList) throws PDFBlackenerException {
		try (PDPageContentStream contentStream = new PDPageContentStream(loadedPdf, loadedPdf.getPage(pageNumber - 1), AppendMode.APPEND, false)) {
			contentStream.setNonStrokingColor(GlobalParameterUtil.getPDFBlackenerConfig().getBlackeningConfig().getColor());
			for (Rect rect : rectList) {
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