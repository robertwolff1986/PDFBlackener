package com.wolffr.PDFBlackener.config;
/**
 * Configuration class that can be used during {@link PDFBlackenerConfig} creation to control the imagifieing behaviour.
 *
 */
public class ImagifyConfig {

	private float targetDPI;

	/**
	 * Constructor to create ImagifyConfig-Entity that will be used during {@link PDFBlackenerConfig} creation
	 * @param dpi dpi that should be used to imagify pdfs. Higher dpi result in a higher quality pdf but it will be larger
	 */
	public ImagifyConfig(float dpi) {
		super();
		this.targetDPI = dpi;
	}

	public float getTargetDPI() {
		return targetDPI;
	}

}
