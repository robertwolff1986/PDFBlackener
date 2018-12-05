package com.wolffr.PDFBlackener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wolffr.PDFBlackener.exception.PDFBlackenerException;

class BlackenPDFUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(BlackenPDFUtil.class);

	protected static byte[] blackenPDF(byte[] pdf, List<Integer> pagesToKeep) throws PDFBlackenerException {
		try (PDDocument loadedPdf = PDDocument.load(pdf)) {
			for (int pageNumber = 0; pageNumber < loadedPdf.getNumberOfPages(); pageNumber++) {
				if (!pagesToKeep.contains(pageNumber))
					blackenPDF(loadedPdf, pageNumber);
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

	private static byte[] getBytes(PDDocument loadedPdf) throws PDFBlackenerException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			loadedPdf.save(baos);
			return baos.toByteArray();
		} catch (IOException ioe) {
			LOGGER.error(String.format("Could not retrieve byte[] for blackenedPDF"), ioe.getMessage());
			throw new PDFBlackenerException(String.format("Could not retrieve byte[] for blackenedPDF: %s", ioe.getMessage()));
		}
	}

	private static void blackenPDF(PDDocument loadedPdf, int pageNumber) throws PDFBlackenerException {
		try (PDPageContentStream contentStream = new PDPageContentStream(loadedPdf, loadedPdf.getPage(pageNumber), AppendMode.APPEND, false)) {
			contentStream.setNonStrokingColor(GlobalParameterUtil.getPDFBlackenerConfig().getBlackeningConfig().getColor());
			contentStream.addRect(getBottomLeftX(loadedPdf,pageNumber), getBottomLeftY(loadedPdf,pageNumber), getWidth(loadedPdf,pageNumber), getHeight(loadedPdf,pageNumber));
			contentStream.fill();
		} catch (IOException ioe) {
			LOGGER.error(String.format("Could not blacken page %s", pageNumber), ioe.getMessage());
			throw new PDFBlackenerException(String.format("Could not blacken page %s : %s", pageNumber, ioe.getMessage()));
		}
	}

	private static float getBottomLeftX(PDDocument loadedPdf, int pageNumber) {
		return (loadedPdf.getPage(pageNumber).getBBox().getWidth() / 100f)
				* GlobalParameterUtil.getPDFBlackenerConfig().getBlackeningConfig().getBlackenLowerLeftXPercentage();
	}

	private static float getBottomLeftY(PDDocument loadedPdf, int pageNumber) {
		return (loadedPdf.getPage(pageNumber).getBBox().getHeight() / 100f)
				* GlobalParameterUtil.getPDFBlackenerConfig().getBlackeningConfig().getBlackenLowerLeftYPercentage();
	}

	private static float getWidth(PDDocument loadedPdf, int pageNumber) {
		return (loadedPdf.getPage(pageNumber).getBBox().getWidth() / 100f)
				* GlobalParameterUtil.getPDFBlackenerConfig().getBlackeningConfig().getBlackenWidthPercentage();
	}

	private static float getHeight(PDDocument loadedPdf, int pageNumber) {
		return (loadedPdf.getPage(pageNumber).getBBox().getHeight() / 100f)
				* GlobalParameterUtil.getPDFBlackenerConfig().getBlackeningConfig().getBlackenHeightPercentage();
	}
}