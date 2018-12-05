package com.wolffr.PDFBlackener;

import java.io.Serializable;

/**
 * Configuration entity that is mandatory for THSService initialization.<br>
 * Optional parameters: blackeningConfig<br>
 * <u>Implements Builder pattern for object creation(call Builder).</u>
 *
 */
public class PDFBlackenerConfig implements Serializable {

	private static final long serialVersionUID = -9038709942943233993L;

	private BlackeningConfig blackeningConfig;
	
	PDFBlackenerConfig(Builder builder) {
		this.blackeningConfig=builder.blackeningConfig;
	}

	public BlackeningConfig getBlackeningConfig() {
		return blackeningConfig;
	}

	public void setConsentCacheConfig(BlackeningConfig blackeningConfig) {
		this.blackeningConfig = blackeningConfig;
	}
	/**
	 * Builder pattern initializer.<br><br>
	 * Use .build() to finally create the configuration.</u>
	 *
	 */
	public static class Builder {

		BlackeningConfig blackeningConfig;

		/**
		 * Initialize a new configuration.
		 * @return Builder object
		 */
		public static Builder createConfig() {
			Builder b = new Builder();
			return b;
		}
		
		
		/**
		 * Add a new {@link BlackeningConfig} object that will be considered during blackening-process.<br>
		 * @param blackeningConfig BlackeningConfig entity.
		 * @return Builder object
		 */
		public Builder setBlackeningConfig(BlackeningConfig blackeningConfig) {
			this.blackeningConfig=blackeningConfig;
			return this;
		}
		

		/**
		 * Create the configured PDFBlackenerConfig.
		 * @return PDFBlackenerConfig that can be used to initialize {@link PDFBlackener}.
		 */
		public PDFBlackenerConfig build() {
			return new PDFBlackenerConfig(this);
		}

	}
}
