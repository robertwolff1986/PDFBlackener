package com.wolffr.PDFBlackener;

class GlobalParameterUtil {

	private static PDFBlackenerConfig pdfBlackenerConfig;

	protected static void setPDFBlackenerConfig(PDFBlackenerConfig pdfBlackenerConfig) {
		GlobalParameterUtil.pdfBlackenerConfig = pdfBlackenerConfig;
	}

	protected static void initializeDefaultConfig() {
		pdfBlackenerConfig = PDFBlackenerConfig.Builder.createConfig().setBlackeningConfig(new BlackeningConfig(0, 0, 100, 100)).build();
	}
	
	protected static PDFBlackenerConfig getPDFBlackenerConfig() {
		return pdfBlackenerConfig;
	}
	
}
