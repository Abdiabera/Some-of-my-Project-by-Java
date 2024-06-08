package DBS;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewAllUI {

    @FXML
    private Button closebtn;
    @FXML
    private TableView<ProductItem> MainTable;

    @FXML
    private TableColumn<ProductItem, String> codecol;

    @FXML
    private TableColumn<ProductItem, String> descriptioncol;

    @FXML
    private TableColumn<ProductItem, String> amountcol;

    @FXML
    private TableColumn<ProductItem, String> pricecol;

    @FXML
    private TableColumn<ProductItem, String> Discountcol;
    
    
    public static class ProductItem
    {
        private final SimpleStringProperty code;
        private final SimpleStringProperty description;
        private final SimpleStringProperty amount;
        private final SimpleStringProperty price;
        private final SimpleStringProperty Discount;
        
        public ProductItem(String _code, String _desc, String _amount, String _price, String _discount)
        {
            code = new SimpleStringProperty(_code);
            description = new SimpleStringProperty(_desc);
            amount = new SimpleStringProperty(_amount);
            price = new SimpleStringProperty(_price);
            Discount = new SimpleStringProperty(_discount);
        }
        
        public String getCode() { return code.get(); }
        public String getDescription() { return description.get(); }
        public String getAmount() { return amount.get(); }
        public String getPrice() { return price.get(); }
        public String getDiscount() { return Discount.get(); }
    };
    
    @FXML
   void initialize () {
    	
  	
    }
    
    public void setDAO(ProductDAO d) {
    	
    	codecol.setCellValueFactory(new PropertyValueFactory<>("Code"));
    	descriptioncol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        amountcol.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        pricecol.setCellValueFactory(new PropertyValueFactory<>("Price"));
       Discountcol.setCellValueFactory(new PropertyValueFactory<>("Discount"));
        
        amountcol.setStyle( "-fx-alignment: CENTER-RIGHT;");
        pricecol.setStyle( "-fx-alignment: CENTER-RIGHT;");
        Discountcol.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
    	
    	ArrayList <Product> Plist = d.getProducts();
    	ObservableList<ProductItem> data = FXCollections.observableArrayList();
    	for(int i=0; i<Plist.size(); i++) {
    		Product curItem = Plist.get(i);

    		ProductItem p = new ProductItem(
    		    curItem.getCode(),
    		    curItem.getDescription(),
    		    Integer.toString(curItem.getAmount()),
    		    curItem.getFormattedPrice(), 
    		    "" + curItem.getDiscount());
    		
    		
    	data.add(p);
    	
    	}
    	MainTable.setItems(data);
    }
    
    
    
    

    @FXML
    void Onclose(ActionEvent event) {
    	((Stage) closebtn.getScene().getWindow()).close();
    	
    	

    }

}
