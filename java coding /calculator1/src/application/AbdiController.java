package application;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.event.ActionEvent;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author abditamirat2018gmail.com
 
 */
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AbdiController {

    @FXML
    private Button clear;

    @FXML
    private Button plus;

    @FXML
    private Button three;

    @FXML
    private Button two;

    @FXML
    private Button one;

    @FXML
    private Button minus;

    @FXML
    private Button six;

    @FXML
    private Button five;

    @FXML
    private Button four;

    @FXML
    private Button multi;

    @FXML
    private Button seven;

    @FXML
    private Button eight;

    @FXML
    private Button nine;

    @FXML
    private Button divide;

    @FXML
    private Button percent;

    @FXML
    private Button plusminus;

    @FXML
    private Button point;

    @FXML
    private Button zero;

    @FXML
    private Button equal;

    @FXML
    private TextField Text;

    @FXML
    void Operation(ActionEvent event) {
    	
    	
    	String btnTxt = ((Button)event.getSource()).getText();

    	switch (btnTxt) {
    	
    	case "+/-":
    		
    		
    	{
            String text = Text.getText();
            String opStr = "+-x/";
            int opidx = -1;
            for (int i = 1; i < text.length(); i++)
                if (opStr.contains("" + text.charAt(i)))
                {
                    opidx = i;
                    break;
                }
            
            
            if (opidx == -1)
            {
                if (text.length() == 0 || text.charAt(0) != '-')
                    Text.setText('-' + text);
                else
                    Text.setText(text.substring(1));
            }
            
            else
            {
                String a = text.substring(0, opidx + 1);
                String b = text.substring(opidx + 1, text.length());
                
                if (b.charAt(0) == '-')
                    Text.setText(a + b.substring(1));
                else
                    Text.setText(a + '-' + b);
            }
        }
    		
    		
    		break;
    	
    	case "+":
    		
    		
    	case "-":
    	case "/":
    	case "x":
    	 
    operation = btnTxt;
    		
    		
    	   String value = Text.getText();
    	   
    	   Double valuenumber = Double.parseDouble(value);
    	   
    	   this.Fnumber = valuenumber;
    	   
    	   // Text.setText("");
    	   
    	   Text.setText(value + btnTxt);
    	        
    	    break;
    		
    	case   "=":
    		
    		
    	equal_click();
    	
  
    	
    	
    	break;
    	
    	case "C":
    		
    		Text.setText("");
    		
    		 this.Fnumber = 0.0;
    		    this.Snumber =0.0;
    		
    		break;	
    		
    	}
    	
    	}
    

    @FXML
    void numberButton(ActionEvent event) {
    	
     Text.setText(Text.getText() + ((Button)event.getSource()).getText());		
    }

    @FXML
    public TextField input;
    
    private Double Snumber;
    
    private Double  Fnumber;
    @FXML
    private String operation;

    @FXML

     public void equal_click(){
    	
    	 String text = Text.getText();
    	 
    	 if (text.endsWith("%"))
    	 {
    		 
    	     text = text.substring(0, text.length() - 1);
    	     Text.setText(text);
    	     Snumber = GetSNumber();
    	     Snumber = Fnumber * (Snumber / 100.0);
    	 }
    	 else
    	 {
    	     Snumber = GetSNumber();
    	 }
    	 
	   switch(operation) {
	   
	   
           case "+":
              
              
               Text.setText(Double.toString(Fnumber +Snumber));
               
         
               break;
                case "-":
                	  Text.setText(Double.toString(Fnumber - Snumber));
               break;
                case "x":
                	
                	  Text.setText(Double.toString(Fnumber * Snumber));
              
               break;
                case "/":
                	  Text.setText(Double.toString(Fnumber / Snumber));
               break;   
               
       }
       
   }
    private double GetSNumber()
    {
        String text = Text.getText();
        String opStr = "+-x/";
        for (int i = text.length() - 1; i >= 0; i--)
        {
            if (opStr.contains("" + text.charAt(i)))
            {
            	return Double.parseDouble(text.substring(i + 1));
           
            }
            if (i == 0)
            {
                Text.setText("Error");
               
            }
        }
        
        return 0;
    }
}
  
