package DBS;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;


import javafx.event.ActionEvent;

public class AppUIController  {
	
private ProductDAO productDAO;
	//private List productList;
	//private Button saveButton;
	//private Button SelectedIndex;

	//private ArrayList<JTextField> editableTextFields;
	//private ArrayList<JTextField> allTextFields;

    @FXML
    private TextField codetxt;

    @FXML
    private Label code;

    @FXML
    private Label price;
    

    @FXML
    private Label amount;

    @FXML
    private Label description;

    @FXML
    private Label Discount;

    @FXML
    private TextField Discounttxt;

    @FXML
    private TextField pricetxt;

    @FXML
    private TextField amounttxt;

    @FXML
    private TextField descriptiontxt;

    @FXML
    private ListView<Product> ListView;

    @FXML
    private Button Next;

    @FXML
    private Button ViewAll;

    @FXML
    private Button Edit;

    @FXML
    private Button Delete;

    @FXML
    private Button Help;

    @FXML
    private Button ViewSelected;

    @FXML
    private Button Prev;

    @FXML
    private Button New;

    @FXML
    private Button Save;

    @FXML
   void initialize () {
    	productDAO = new ProductSQL();
    	refreshProductList ();
    	setFieldsEnalble(false);
    	
    	Save.setDisable(true);
    	
	   
   }
    
    @FXML
    void DiscountOperation(ActionEvent event) {
    	
    	

    }

    @FXML
    void ViewAllAction(ActionEvent event) {
    	
    	try {
    	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewAll.fxml"));
    	    BorderPane root1 = (BorderPane) fxmlLoader.load();
    	    ((ViewAllUI)fxmlLoader.getController()).setDAO(productDAO);
    	    Stage stage = new Stage();
    	    stage.initModality(Modality.APPLICATION_MODAL);
    	    stage.initStyle(StageStyle.DECORATED);
    	    stage.setTitle("View All");
    	    stage.setScene(new Scene(root1));
    	    
    	    stage.setMinWidth(root1.getWidth());
    	    stage.setMinHeight(root1.getHeight());
    	    root1.setPrefHeight(root1.getHeight() + 350);
    	    stage.showAndWait();
    	}
    	catch (Exception e)
    	{ 
    	    System.out.println(e.getMessage());
    	}

    		
    }

    @FXML
    void ViewSelectedAction(ActionEvent event) {
    	
    	Product p = ListView.getSelectionModel().getSelectedItem();
    	
    	
    	populateFieldsFromProduct(p);
    	
   
    	

    }


    @FXML
    void btnnew(ActionEvent event) {
    	
    	this.clearTextFields();
    	setFieldsEditable(true);
    	setFieldsEnalble(true);
    	codetxt.requestFocus();
    	Save.setDisable(false);
    	
    	
    }	      

    @FXML
    void btnsave(ActionEvent event)  throws Exception {
    	
    	Product product = getProductFromFields(); // remember the selection
    	
    	
    		int index = getSelectedIndex();
    		if (product != null) {
    			ArrayList<Product> Plist = productDAO.getProducts();
    			
    			boolean exist = false;
    			
    			for (int i=0; i<Plist.size(); i++) {
					 if (Plist.get(i).getCode().equalsIgnoreCase(product.getCode())) {
										 exist = true;
										   break;
						
					 }
    		
    			}
    			
    			if(exist) {
    				
    				
    				productDAO.updateProduct(product);
    			} else {
    				productDAO.addProduct(product);
    				index++;
    			}

    			showAlert (AlertType.INFORMATION, "Success", "Product saved successfully.\\n");
    		}
    		clearTextFields();
    		setFieldsEditable(false);
    		refreshProductList();
    		ListView.getSelectionModel().select(index);
    		Save.setDisable(true);
    		setFieldsEditable(false);
    		setFieldsEnalble(false);
    	
    }

    @FXML
    void deleteAction(ActionEvent event) {
    	
    		Product product = getSelectedProduct();
    		if (product != null) {
    			// ENTER should always press the focused button
    		//	UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);

    			//int result = JOptionPane.showOptionDialog(this,
    				//	"Are you sure you want to delete this product?\n\n" + product + "\n\n", "Confirm Delete",
    					//JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

    			Boolean result;
    		
    			
    		result =AskAlert(AlertType.CONFIRMATION, "delete items", "would you like to delete the item?");
    			
    			if (result) {
    				int index = getSelectedIndex();
    				try {
    					productDAO.deleteProduct(product);
    					refreshProductList();
    					index--;
    					//JOptionPane.showMessageDialog(this, "Product deleted successfully.\n", "Success",
    						//	JOptionPane.WARNING_MESSAGE);
    					showAlert (AlertType.INFORMATION, "Success", "Product deleted successfully.\\n");
    				} catch (Exception e) {
    				//	handleFatalException(e);
    				}

    				if (codetxt.getText().equals(product.getCode())) {
    					clearTextFields();
    					setFieldsEditable(false);
    					Save.setDisable(false);
    				}
    				
    			} else {
    				return;
    			}
    		}
    		
    	}

    

    @FXML
    void editAction(ActionEvent event) {
    	
    	
    	Product product = getSelectedProduct();
		if (product != null) {
			clearTextFields();
			setFieldsEditable(true);
			setFieldsEnalble(true);
			codetxt.setEditable(false);
			codetxt.setDisable(true);
			descriptiontxt.requestFocus();
			populateFieldsFromProduct(product);
			
			Save.setDisable(false);
			
			

    }
		}

    @FXML
    void helpAction(ActionEvent event) {
    	String helpMessage = "New                     -  alt+n\n" + "Save                    -  alt+s\n\n"
				+ "Prev                     -  alt+[\n" + "Next                     -  alt+]\n\n"
				+ "View Selected    -  alt+v\n" + "View All               -  alt+a\n"
				+ "Edit                      -  alt+e\n" + "Delete                 -  alt+d\n"
				+ "Help                     -  alt+h\n";

		//JOptionPane.showMessageDialog(this, helpMessage, "Help", JOptionPane.INFORMATION_MESSAGE);
    	
     showAlert(AlertType.INFORMATION, "Help", helpMessage);
     

      }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setHeaderText(title);
		
		alert.getDialogPane().setContent(new TextArea(message));
		alert.showAndWait();
		
	}

  
    private Boolean AskAlert(Alert.AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setHeaderText(title);
		
		alert.getDialogPane().setContent(new TextArea(message));
		ButtonType  B =  alert.showAndWait().get();
		
		return (B.getText().equalsIgnoreCase("ok"));
		
	}

  
    
    @FXML
    void nextAction(ActionEvent event) {
    	int index = getSelectedIndex();
    	int numItems = ListView.getItems().size();
		if (numItems > 0)
	    {
			index = (index + 1) % numItems;
			ListView.getSelectionModel().select(index);
	    }

    	codetxt.setDisable(true);
    	descriptiontxt.setDisable(true);
    	amounttxt.setDisable(true);
    	pricetxt.setDisable(true);
    	Discounttxt.setDisable(true);
    
    	

    }

    @FXML
    void prevAction(ActionEvent event) {
    	int index = getSelectedIndex();
    	int numItems = ListView.getItems().size();
		if (numItems > 0)
	    {
			index = (index + numItems - 1) % numItems;
			ListView.getSelectionModel().select(index);
	    }

    	codetxt.setDisable(true);
    	descriptiontxt.setDisable(true);
    	amounttxt.setDisable(true);
    	pricetxt.setDisable(true);
    	Discounttxt.setDisable(true);
    

    }

    void refreshProductList() {
    	
    	
    	ListView.getItems().clear();
    	
    ArrayList<Product> p =	productDAO.getProducts();
    	
    	for (int i=0; i< p.size(); i++) {
    		ListView.getItems().add(p.get(i));
    	}
    }
    
  void  clearTextFields() {
	  
	  codetxt.setText("");
	  
	  descriptiontxt.setText("");
	  amounttxt.setText("");
	  Discounttxt.setText("");
	  pricetxt.setText("");
	  
	  		
	  
  }
  
  void  setFieldsEditable(Boolean b) {
	  codetxt.setEditable(b);
	  descriptiontxt.setEditable(b);
	  amounttxt.setEditable(b);
	  Discounttxt.setEditable(b);
	  pricetxt.setEditable(b);
	  
  }
  
     
    Product getSelectedProduct() {
  
    	return ListView.getSelectionModel().getSelectedItem();
    
    }
    int getSelectedIndex() {
    	return ListView.getSelectionModel().getSelectedIndex();
    }
    
    void  populateFieldsFromProduct( Product p) {
    	
    codetxt.setText(p.getCode());
    descriptiontxt.setText(p.getDescription());
    amounttxt.setText(p.getAmountString());
    pricetxt.setText(p.getFormattedPrice());
    Discounttxt.setText("" + p.getDiscount());
    
    
     
    	
    }
    void setFieldsEnalble(boolean b) {
    	
    	codetxt.setDisable(!b);
    	descriptiontxt.setDisable(!b);
    	amounttxt.setDisable(!b);
    	pricetxt.setDisable(!b);
    	Discounttxt.setDisable(!b);
    }
    
    
   Product getProductFromFields() {
	   Product p = new Product();
	   
	   p.setCode(codetxt.getText());
	   p.setDescription(descriptiontxt.getText());
	   p.setAmount(Integer.parseInt(amounttxt.getText()));
	   p.setPrice(Double.parseDouble(pricetxt.getText().replace("$", "")));
	   p.setDiscount(Discounttxt.getText().charAt(0));
	   return p;
	   
		   
		   
	   }
	   
   }
    
    
   


