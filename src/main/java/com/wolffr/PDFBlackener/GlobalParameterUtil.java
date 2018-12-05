package com.wolffr.PDFBlackener;

import java.awt.Color;

import com.wolffr.PDFBlackener.config.BlackeningConfig;
import com.wolffr.PDFBlackener.config.PDFBlackenerConfig;

class GlobalParameterUtil {

	private static PDFBlackenerConfig pdfBlackenerConfig;

	protected static void setPDFBlackenerConfig(PDFBlackenerConfig pdfBlackenerConfig) {
		GlobalParameterUtil.pdfBlackenerConfig = pdfBlackenerConfig;
	}

	protected static void initializeDefaultConfig() {
		pdfBlackenerConfig = PDFBlackenerConfig.Builder.createConfig().setBlackeningConfig(new BlackeningConfig(0, 0, 100, 100,Color.black)).build();
	}
	
	protected static PDFBlackenerConfig getPDFBlackenerConfig() {
		return pdfBlackenerConfig;
	}
}
