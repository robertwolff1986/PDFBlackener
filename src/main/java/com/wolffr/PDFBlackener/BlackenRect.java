package com.wolffr.PDFBlackener;

public class BlackenRect {

	private float x;
	private float y;
	private float width;
	private float height;

	public BlackenRect(float x, float y, float width, float height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return "BlackenRect [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
	}
	
	

}
