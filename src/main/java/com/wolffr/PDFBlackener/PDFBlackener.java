package com.wolffr.PDFBlackener;

import java.util.List;

import com.wolffr.PDFBlackener.config.DetailBlackeningConfig;
import com.wolffr.PDFBlackener.exception.PDFBlackenerException;

/**
 * Blacken pdfs based on content or simply via submitting List<Integer> of
 * pageNrs. that should not be blackened
 */
public interface PDFBlackener {

	/**
	 * Inserting a black rectangle on all pages of a given pdf whose pagenumbers are not submitted in pagesToKeep. 
	 * @param pdf: byte-Array containing the pdf that should be blackened
	 * @param pagesToKeep: List of pageNumbers that should not be blackened
	 * @param imagify: if true, the resulting pdf will contain images instead of text etc. Any information leakage will be prevented this way as no more information can be retrieved from the images.
	 * @return the resulting pdf as byte[]
	 * @throws PDFBlackenerException
	 */
	public byte[] blackenPDF(byte[] pdf, List<Integer> pagesToKeep, boolean imagify) throws PDFBlackenerException;

	/**
	 * Inserting a black rectangle on all pages of a given pdf which should not be kepped. Prior to blackening all pages will be scanned for any occurence of a string contained in stringsOnPagesThatShouldBeKepped. If a string from stringsOnPagesThatShouldBeKepped is on a page, that page will be not blackened.
	 * @param pdf: byte-Array containing the pdf that should be blackened
	 * @param stringsOnPagesThatShouldBeKepped: List of strings that should be used to determine pages that should not be blackened
	 * @param imagify: if true, the resulting pdf will contain images instead of text etc. Any information leakage will be prevented this way as no more information can be retrieved from the images
	 * @return the resulting pdf as byte[]
	 * @throws PDFBlackenerException
	 */
	public byte[] findPagesAndBlackenPDF(byte[] pdf, List<String> stringsOnPagesThatShouldBeKepped, boolean imagify) throws PDFBlackenerException;
	
	
	public byte[] detailBlackening(byte[] pdf, List<DetailBlackeningConfig> detailBlackeningConfigList,boolean imagify) throws PDFBlackenerException;
}
