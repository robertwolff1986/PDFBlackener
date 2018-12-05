package com.wolffr.PDFBlackener;

public class BlackeningConfig {

	private float blackenLowerLeftXPercentage;
	private float blackenLowerLeftYPercentage;
	private float blackenWidthPercentage;
	private float blackenHeightPercentage;

	public BlackeningConfig(float blackenLowerLeftXPercentage, float blackenLowerLeftYPercentage, float blackenWidthPercentage,
			float blackenHeightPercentage) {
		super();
		this.blackenLowerLeftXPercentage = blackenLowerLeftXPercentage;
		this.blackenLowerLeftYPercentage = blackenLowerLeftYPercentage;
		this.blackenWidthPercentage = blackenWidthPercentage;
		this.blackenHeightPercentage = blackenHeightPercentage;
	}

	public float getBlackenLowerLeftXPercentage() {
		return blackenLowerLeftXPercentage;
	}

	public float getBlackenLowerLeftYPercentage() {
		return blackenLowerLeftYPercentage;
	}

	public float getBlackenWidthPercentage() {
		return blackenWidthPercentage;
	}

	public float getBlackenHeightPercentage() {
		return blackenHeightPercentage;
	}

}
