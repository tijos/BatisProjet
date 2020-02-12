package main.java.fr.batis.components.tables;

import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.entites.Materiel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

class EditingColorCell<T> extends TableCell<T, String> {
	 
    private TextField textField;

    private Materiel materiel;
    public EditingColorCell() {
    //	this.materiel = mat;
    }

    
    /**
	 * @return the materiel
	 */
	public Materiel getMateriel() {
		return materiel;
	}


	/**
	 * @param materiel the materiel to set
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}


	@Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText((String) getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        String montant;
     
        if (!empty && item != null) {
        	 String mont = (String) getItem();
             montant = BatisUtils.getNumberFormatter(Double.valueOf(mont));
        	  if(mont.startsWith("-")) {
              	setTextFill(Color.web("#FF0000"));
              	
              }else {
              	setTextFill(Color.web("#008000"));
              	 setTextFill(Color.GREEN);
              }
        	  setText(montant);
        	 
          } else {
            setText(null);
          }

    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
        textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, 
                Boolean arg1, Boolean arg2) {
                    if (!arg2) {
                        commitEdit(textField.getText());
                    }
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}