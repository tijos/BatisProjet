package main.java.fr.batis.components.custom;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CustomColor {
	private Color color;
	private Font font;

	public CustomColor() {
		super();
		this.color = Color.rgb(168, 211, 255);

		font = new javafx.scene.text.Font("Arial Black", 12);
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * @param font the font to set
	 */
	public void setFont(Font font) {
		this.font = font;
	}

}
