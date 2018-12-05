package com.wolffr.PDFBlackener.config;

import java.awt.Color;

/**
 * Configuration class that can be used during {@link PDFBlackenerConfig}
 * creation to control the blackening behaviour.
 *
 */
public class BlackeningConfig {

	private float blackenLowerLeftXPercentage;
	private float blackenLowerLeftYPercentage;
	private float blackenWidthPercentage;
	private float blackenHeightPercentage;
	private Color color;

	/**
	 * Constructor to create BlackeningConfig-Entity that will be used during
	 * {@link PDFBlackenerConfig} creation The rect will be drawn from lower
	 * left corner and thus requires the coordinates to start the drawing and
	 * the width/height of itself.
	 * 
	 * @param blackenLowerLeftXPercentage
	 *            Percentage of the page width at which the blackening rect
	 *            should be drawn. Value 0 will start the drawing at the left
	 *            border of the page (default: 0)
	 * @param blackenLowerLeftYPercentage
	 *            Percentage of the page height at which the blackening rect
	 *            should be drawn. Value 0 will start the drawing at the bottom
	 *            border of the page (default: 0)
	 * @param blackenWidthPercentage
	 *            Percentage of the page width. The resulting width is the width
	 *            of the drawn rect. Value 100 will result in a rect with the
	 *            same width as the page (default: 100)
	 * @param blackenHeightPercentage
	 *            Percentage of the page height. The resulting height is the
	 *            height of the drawn rect. Value 100 will result in a rect with
	 *            the same height as the page (default: 100)
	 * @param color Color that should be used to blacken the document -> White would essentially lead to a whitened document 8) (default: black)            
	 */
	public BlackeningConfig(float blackenLowerLeftXPercentage, float blackenLowerLeftYPercentage, float blackenWidthPercentage,
			float blackenHeightPercentage, Color color) {
		super();
		this.blackenLowerLeftXPercentage = blackenLowerLeftXPercentage;
		this.blackenLowerLeftYPercentage = blackenLowerLeftYPercentage;
		this.blackenWidthPercentage = blackenWidthPercentage;
		this.blackenHeightPercentage = blackenHeightPercentage;
		this.color=color;
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

	public Color getColor() {
		return color;
	}

}
