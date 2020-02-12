/**
 * 
 */
package main.java.fr.batis.components.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

/**
 * @author admin
 *
 */
public class DateConverterHelper {

	public DateConverterHelper() {
		super();
	}
	
	 public static DatePicker getConvetedDate() {
		 
		 DatePicker datePicker = new DatePicker();
		 String pattern = "dd/MM/yyyy";

		 datePicker.setPromptText(pattern.toLowerCase());

		 datePicker.setConverter(new StringConverter<LocalDate>() {
		      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

		      @Override 
		      public String toString(LocalDate date) {
		          if (date != null) {
		              return dateFormatter.format(date);
		          } else {
		              return "";
		          }
		      }

		      @Override 
		      public LocalDate fromString(String string) {
		          if (string != null && !string.isEmpty()) {
		              return LocalDate.parse(string, dateFormatter);
		          } else {
		              return null;
		          }
		      }
		  });
		 
		 return datePicker;
	 }   
}
