package com.wolffr.PDFBlackener.config;

import com.wolffr.PDFBlackener.DetailBlackenMode;

public class DetailBlackeningConfig {

	private DetailBlackenMode blackenType;
	private String startMarkerText;
	private String finishMarkerText;
	private float blackenTopMargin;
	private float blackenBottomMargin;
	private float blackenWidth;
	private Integer pageNr;

	public DetailBlackeningConfig(DetailBlackenMode blackenType, String startMarkerText, String finishMarkerText, float blackenTopMargin,
			float blackenBottomMargin, float blackenWidth, Integer pageNr) {
		super();
		this.blackenType = blackenType;
		this.startMarkerText = startMarkerText;
		this.finishMarkerText = finishMarkerText;
		this.blackenTopMargin = blackenTopMargin;
		this.blackenBottomMargin = blackenBottomMargin;
		this.blackenWidth = blackenWidth;
		this.pageNr = pageNr;
	}

	public DetailBlackenMode getBlackenType() {
		return blackenType;
	}

	public String getStartMarkerText() {
		return startMarkerText;
	}

	public String getFinishMarkerText() {
		return finishMarkerText;
	}

	public float getBlackenTopMargin() {
		return blackenTopMargin;
	}

	public float getBlackenBottomMargin() {
		return blackenBottomMargin;
	}

	public float getBlackenWidth() {
		return blackenWidth;
	}

	public Integer getPageNr() {
		return pageNr;
	}
}