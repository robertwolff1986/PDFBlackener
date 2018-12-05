package com.wolffr.PDFBlackener;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.fontbox.util.BoundingBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wolffr.PDFBlackener.exception.PDFBlackenerException;

class ImagifyPDFUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(ImagifyPDFUtil.class);

	protected static byte[] imagifyPDF(byte[] pdf) throws PDFBlackenerException {

		try (PDDocument loadedPdf = PDDocument.load(pdf); PDDocument newDocument = new PDDocument();) {
			PDFRenderer renderer = new PDFRenderer(loadedPdf);
			for (int pageNumber = 0; pageNumber < loadedPdf.getNumberOfPages(); pageNumber++) {
				byte[] pageImg = getPageAsImg(renderer, pageNumber);
				PDPage page = createPageFromImage(loadedPdf, newDocument, pageImg);
				newDocument.addPage(page);
			}
			return getBytes(newDocument);
		} catch (Exception e) {
			LOGGER.error(String.format("Could not imagify pdf"), e.getMessage());
			throw new PDFBlackenerException(String.format("Could not imagify pdf: %s", e.getMessage()));
		} 
	}
	
	private static byte[] getBytes(PDDocument newDocument) throws PDFBlackenerException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			newDocument.save(baos);
			return baos.toByteArray();
		} catch (IOException ioe) {
			LOGGER.error(String.format("Could not retrieve byte[] for blackenedPDF"), ioe.getMessage());
			throw new PDFBlackenerException(String.format("Could not retrieve byte[] for blackenedPDF: %s", ioe.getMessage()));
		}
	}

	private static PDPage createPageFromImage(PDDocument loadedPdf, PDDocument newDocument, byte[] pageImg) throws PDFBlackenerException {
		try {
		PDImageXObject pdImage = PDImageXObject.createFromByteArray(newDocument, pageImg, "");
		PDPage page = new PDPage(new PDRectangle(new BoundingBox(0, 0, pdImage.getWidth(), pdImage.getHeight())));
		try (PDPageContentStream contentStream = new PDPageContentStream(loadedPdf, page, AppendMode.APPEND, true, true)) {
			contentStream.drawImage(pdImage, 0, 0, pdImage.getWidth(), pdImage.getHeight());
		}
		return page;
		}catch(IOException ioe) {
			LOGGER.error(String.format("Could not create page from image"), ioe.getMessage());
			throw new PDFBlackenerException(String.format("Could not create page from image: %s", ioe.getMessage()));
		}
	}

	private static byte[] getPageAsImg(PDFRenderer renderer, int pageNumber) throws PDFBlackenerException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			BufferedImage currentPageImage = renderer.renderImageWithDPI(pageNumber, 150f);
			ImageIO.write(currentPageImage, "jpg", baos);
			return baos.toByteArray();
		} catch (IOException ioe) {
			LOGGER.error(String.format("Could not convert page %s to image", pageNumber), ioe.getMessage());
			throw new PDFBlackenerException(String.format("Could not convert page %s to image: %s", pageNumber, ioe.getMessage()));
		}

	}

}
