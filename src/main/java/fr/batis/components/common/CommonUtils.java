package main.java.fr.batis.components.common;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.controlsfx.tools.Borders;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class CommonUtils {

	
	public CommonUtils() {
		super();
	}

	//Header
	public Node getHeader(String title) {
		Image logoImg = new Image(getClass().getResourceAsStream("/images/logo_min.png"));
		Label logo = new Label();
		logo.setGraphic(new ImageView(logoImg));
	    Label titlLabel = new Label(title);
	  //  logo.setPrefHeight(30);
	   // logo.setPadding(new Insets(1));
	  //  logo.setPrefWidth(110);
	    titlLabel.setFont(new Font("Arial", 24));
	    BorderPane  headerPane = new BorderPane();
	   // headerPane.setPrefHeight(50);
	   headerPane.setLeft(titlLabel);
	   headerPane.setRight(logo);
	   Node wraperHead = Borders.wrap(headerPane)
				
		    		.lineBorder()
	        		.color(Color.rgb(168,211,255))
	        		.thickness(1, 0, 1, 0)
	        		//.outerPadding(0,0,10,0)
	        	//	.innerPadding(10)
	        		. buildAll();
		
		return wraperHead;
	} 
	
	
	/**
	 * 
	 * @return
	 */
	public Callback<DatePicker, DateCell> getDayCellFactory() {
		 
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
 
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
 
                       // item.getDayOfWeek() == DayOfWeek.SATURDAY //
                                
                        // Disable Monday, Tueday, Wednesday.
                       // DatePicker checkInDatePicker = new DatePicker();
                       // || item.isBefore(
                           //     checkInDatePicker.getValue().plusDays(1))
                      //  checkInDatePicker.setValue(LocalDate.now());
                        if (item.getDayOfWeek() == DayOfWeek.SUNDAY ) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }

                  
                    }
                };
            }
        };
        return dayCellFactory;
    }
	
	/**
	 * 
	 * @return
	 */
	public String getBatisStyle() {
		return "-fx-font-size: 13px;";
	}
	
	/**
	 * 
	 * @return
	 */
	public GridPane getGridPanel() {
		GridPane gridPrincipale = new GridPane();	       
		gridPrincipale.setPadding(new Insets(10));
		gridPrincipale.setAlignment(Pos.TOP_LEFT);        
		gridPrincipale.setVgap(10);
		gridPrincipale.setHgap(10);
		return gridPrincipale;
	}
	
}
