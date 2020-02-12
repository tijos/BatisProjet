package main.java.fr.batis.components.custom;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CustomLabel extends Label {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6170933457121982460L;
	CustomColor base= new CustomColor();
	private Color color = base.getColor();
	
	public CustomLabel() {
		//setColor(color);
		//setTextFill(Color.web(color.toString()));
		setFont(Font.font("Arial",FontWeight.BOLD,12));
		//setTextFill(Color.web("#808080"));
	}
	
	
	public CustomLabel(String text) {
		super(text);
		//setColor(color);
		//setTextFill(Color.web(color.toString()));
		setFont(Font.font("Arial",FontWeight.BOLD,14));
		//setTextFill(Color.web("#808080"));
	}

	
	 public CustomLabel(String text, Node graphic) {
	        super(text, graphic);
	        
    }
	 
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
