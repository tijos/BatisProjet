package main.java.fr.batis.components.custom;

import org.apache.commons.lang3.StringUtils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

import javafx.scene.input.KeyEvent;


public class DoubleTextField extends TextField {
//   private static final Logger logger = LoggerFactory.getLogger(DoubleTextField.class);

   public DoubleTextField() {
      super();
 
      addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
         @Override
         public void handle(KeyEvent event) {
            if (!isValid(getText())) {
               event.consume();
            }
         }
      });

      textProperty().addListener(new ChangeListener<String>() {
         @Override
         public void changed(ObservableValue<? extends String> observableValue,
                             String oldValue, String newValue) {
            if(!isValid(newValue)) {
               setText(oldValue);
            
            }
         }
      });
      
      
   }

   public DoubleTextField(String text) {
       super(text);
     //  getStyleClass().add("text-field");
     //  setAccessibleRole(AccessibleRole.TEXT_FIELD);
      // setText(text);
   }
   
   private boolean isValid(final String value) {
      if (value.length() == 0 || value.equals("-")) {
         return true;
      }

      if (StringUtils.countMatches(value, ".") > 1) {
         return false;
      } 
      if (value.endsWith(".")) {
         return true;
      }

      try {
         Double.parseDouble(value);
         return true;
      } catch (NumberFormatException ex) {
         return false;
      }
   }

   public double getDouble() {
      try {
         return Double.parseDouble(getText());
      }
      catch (NumberFormatException e) {
       //  logger.error("Error parsing double (" + getText() +") from field.", e);
         return 0;
      }
   }
}
