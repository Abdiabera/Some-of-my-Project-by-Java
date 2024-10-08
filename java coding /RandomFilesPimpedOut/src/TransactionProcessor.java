

// This program reads a random-access file sequentially,
// updates records already written to the file, creates new
// records to be placed in the file and deletes data
// already in the file.

// Java core packages
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DecimalFormat;

// Java extension packages
import javax.swing.*;

public class TransactionProcessor extends JFrame {   
   private UpdateDialog updateDialog;
   private NewDialog newDialog;
   private DeleteDialog deleteDialog;
   private JMenuItem newItem, updateItem, deleteItem, 
      openItem, exitItem;
   private JDesktopPane desktop;
   private RandomAccessFile file;  
   private RandomAccessAccountRecord record;  
   
   // set up GUI
   public TransactionProcessor()
   {
      super( "Transaction Processor" );

      // set up desktop, menu bar and File menu
      desktop = new JDesktopPane();
	  
      getContentPane().add( desktop);
 
      JMenuBar menuBar = new JMenuBar();
	 // setJMenuBar(menuBar);
   add( menuBar,BorderLayout.SOUTH );//Put MenuBar on the bottom of the Frame

      JMenu fileMenu = new JMenu( "File" );
	  fileMenu.setMnemonic('F');
      menuBar.add( fileMenu );

      // set up menu item for adding a record
      newItem = new JMenuItem( "New Record" );
      newItem.setEnabled( false );

      // display new record dialog when user selects New Record
      newItem.addActionListener( 

         new ActionListener() {

            public void actionPerformed( ActionEvent event )
            {
               newDialog.setVisible( true );              
            }
         }
      );

      // set up menu item for updating a record
      updateItem = new JMenuItem( "Update Record" );
      updateItem.setEnabled( false );

      // display update dialog when user selects Update Record
      updateItem.addActionListener( 

         new ActionListener() {

            public void actionPerformed( ActionEvent event )
            {
               updateDialog.setVisible( true );              
            }
         }
      );
      
      // set up menu item for deleting a record
      deleteItem = new JMenuItem( "Delete Record" );
      deleteItem.setEnabled( false );

      // display delete dialog when user selects Delete Record
      deleteItem.addActionListener( 

         new ActionListener() {            

            public void actionPerformed( ActionEvent event )
            {
               deleteDialog.setVisible( true );              
            }
         }
      );

      // set up button for opening file
      openItem = new JMenuItem( "New/Open File" );

      // enable user to select file to open, then set up 
      // dialog boxes
      openItem.addActionListener( 

         new ActionListener() {

            public void actionPerformed( ActionEvent event )
            {
               boolean opened = openFile();

               if ( !opened ) return;
                 

               openItem.setEnabled( false );

               // set up internal frames for record processing
               updateDialog = new UpdateDialog( file );   // remember to pass the file reference !!!!!
              desktop.add( updateDialog );
               
               deleteDialog = new DeleteDialog( file );   // remember to pass the file reference !!!!!
               desktop.add ( deleteDialog );
               
               newDialog = new NewDialog( file );   // remember to pass the file reference !!!!!
               desktop.add( newDialog );
            }

         }  // end anonymous inner class

      ); // end call to addActionListener

      // set up menu item for exiting program
      exitItem = new JMenuItem( "Exit" );
	  exitItem.setMnemonic('x');
      exitItem.setEnabled( true );

      // teminate application
      exitItem.addActionListener( 

         new ActionListener() {            

            public void actionPerformed( ActionEvent event )
            {
               closeFile();              
            }
         }
      );

      // attach menu items to File menu
      fileMenu.add( openItem );
      fileMenu.add( newItem );
      fileMenu.add( updateItem );
      fileMenu.add( deleteItem );
      fileMenu.addSeparator();
      fileMenu.add( exitItem );

      // configure window
         
     setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
	 // setDefaultCloseOperation( WindowConstants.DO_NOTHING_ON_CLOSE );

      setSize( 400, 250  );
      setVisible( true );

   }  // end TransactionProcessor constructor

   // enable user to select file to open
   private boolean openFile()
   {
      // display dialog so user can select file
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileSelectionMode(
         JFileChooser.FILES_ONLY );

      int result = fileChooser.showOpenDialog( this );

      // if user clicked Cancel button on dialog, return
      if ( result == JFileChooser.CANCEL_OPTION )
         return false;

      // obtain selected file
      File fileName = fileChooser.getSelectedFile();

      // display error if file name invalid
      if ( fileName == null ||
           fileName.getName().equals( "" ) ) {
         JOptionPane.showMessageDialog( this,
            "Invalid File Name", "Invalid File Name",
            JOptionPane.ERROR_MESSAGE );

         return false;
      }

      else {

         // open file
         try {
            file = new RandomAccessFile( fileName, "rw" );
            openItem.setEnabled( false );
            newItem.setEnabled( true );
            updateItem.setEnabled( true );
            deleteItem.setEnabled( true );
         }
  
         // process problems opening file
         catch ( IOException ioException ) {
            JOptionPane.showMessageDialog( this,
               "File does not exist", "Invalid File Name",
               JOptionPane.ERROR_MESSAGE );

            return false;
         }      
      }

      return true;  // file is  opened
   }

   // close file and terminate application
   private void closeFile() 
   {
      // close file and exit
      try {
         if ( file != null )
            file.close();

         System.exit( 0 );
      }

      // process exceptions closing file
      catch( IOException ioException ) {
         JOptionPane.showMessageDialog( this,
            "Error closing file",
            "Error", JOptionPane.ERROR_MESSAGE );
         System.exit( 1 );
      }
   }
  
   // execute application
   public static void main( String args[] )
   { 
      new TransactionProcessor();
   }

}  // end class TransactionProcessor

// class for udpating records
class UpdateDialog extends JInternalFrame {
   private RandomAccessFile file;  
   private BankUI userInterface;

   // set up GUI
   public UpdateDialog( RandomAccessFile updateFile )
   {
      super( "Update Record" );

      file = updateFile;   // remember to pass the file reference !!!!!

      // set up GUI components
      userInterface = new BankUI( 5 );
      getContentPane().add( userInterface,
         BorderLayout.CENTER );

      // set up Save Changes button and register listener
      JButton saveButton = userInterface.getDoTask1Button();
      saveButton.setText( "Save Changes" );

      saveButton.addActionListener(

         new ActionListener() {

            public void actionPerformed( ActionEvent event )
            {
               addRecord( getRecord() );
               setVisible( false );  
               userInterface.clearFields();            
            }
         }      
      );

      // set up Cancel button and register listener
      JButton cancelButton = userInterface.getDoTask2Button();
      cancelButton.setText( "Cancel" );

      cancelButton.addActionListener(

         new ActionListener() {

            public void actionPerformed( ActionEvent event )
            {
               setVisible( false );
              userInterface.clearFields();     
            }
         }
      );
  
      // set up listener for transaction textfield
      JTextField transactionField = 
         userInterface.getFields()[ BankUI.TRANSACTION ];

      transactionField.addActionListener(

         new ActionListener() {

            public void actionPerformed( ActionEvent event )
            {
               // add transaction amount to balance
               try {
                  RandomAccessAccountRecord record = getRecord();

				      // get textfield values from userInterface
                  String fieldValues[] = 
                     userInterface.getFieldValues();

                  // get transaction amount
                  double change = Double.parseDouble( 
                     fieldValues[ BankUI.TRANSACTION ] );

                  // specify Strings to display in GUI
                  String[] values = {
                     String.valueOf( record.getAccount() ),
                     record.getFirstName(),
                     record.getLastName(),
                     String.valueOf( record.getBalance()
                        + change ), 
                     "Charge(+) or payment (-)" };

                  // display Strings in GUI
                  userInterface.setFieldValues( values );
               }  

               // process invalid number in transaction field
               catch ( NumberFormatException numberFormat ) {
                  JOptionPane.showMessageDialog( null,
                     "Invalid Transaction",
                     "Invalid Number Format",
                     JOptionPane.ERROR_MESSAGE );
               }

            }  // end method actionPerformed

         }  // end anonymous inner class

      ); // end call to addActionListener

      // set up listener for account text field
      JTextField accountField = 
         userInterface.getFields()[ BankUI.ACCOUNT ];

      accountField.addActionListener(

         new ActionListener() {

            // get record and display contents in GUI
            public void actionPerformed( ActionEvent event )
            {
               RandomAccessAccountRecord record = getRecord();

               if ( record.getAccount() != 0 )  {
                  String values[] = {
                     String.valueOf( record.getAccount() ),
                     record.getFirstName(),
                     record.getLastName(),
                     String.valueOf( record.getBalance() ),
                     "Charge(+) or payment (-)" };

                  userInterface.setFieldValues( values ); 
               }       

            }  // end method actionPerformed

         }  // end anonymous inner class
         
      ); // end call to addActionListener

      setSize( 300, 175 );
      setVisible( false );
   }

   // get record from file
   private RandomAccessAccountRecord getRecord() 
   {
      RandomAccessAccountRecord record = 
         new RandomAccessAccountRecord();

      // get record from file
      try {
         JTextField accountField =  
            userInterface.getFields()[ BankUI.ACCOUNT ];

         int accountNumber = 
            Integer.parseInt( accountField.getText() );

         if ( accountNumber < 1 || accountNumber > 100 ) {
            JOptionPane.showMessageDialog( this,
               "Account Does Not Exist",
               "Error", JOptionPane.ERROR_MESSAGE );
            return record;
         }

         // seek to appropriate record location in file
         file.seek( ( accountNumber - 1 ) * 
            RandomAccessAccountRecord.size() );
         record.read( file );
      
         if ( record.getAccount() == 0 )
            JOptionPane.showMessageDialog( this,
               "Account Does Not Exist",
               "Error", JOptionPane.ERROR_MESSAGE ); 
      }

      // process invalid account number format
      catch ( NumberFormatException numberFormat ) {
         JOptionPane.showMessageDialog( this,
            "Invalid Account", "Invalid Number Format",
            JOptionPane.ERROR_MESSAGE );
      }
 
      // process file processing problems
      catch ( IOException ioException ) {
         JOptionPane.showMessageDialog( this,
            "Error Reading File",
            "Error", JOptionPane.ERROR_MESSAGE );
      }

      return record;

   }  // end method getRecord

   // add record to file
   public void addRecord( RandomAccessAccountRecord record )
   {
      // update record in file
      try {
         int accountNumber = record.getAccount();

         file.seek( ( accountNumber - 1 ) * 
            RandomAccessAccountRecord.size() ); 

         String[] values = userInterface.getFieldValues();   

         // set firstName, lastName and balance in record
         record.setFirstName( values[ BankUI.FIRSTNAME ] );
         record.setLastName( values[ BankUI.LASTNAME ] );
         record.setBalance( 
            Double.parseDouble( values[ BankUI.BALANCE ]  ) );
         
         // rewrite record to file
         record.write( file );
      }

      // process file processing problems
      catch ( IOException ioException ) {
         JOptionPane.showMessageDialog( this,
            "Error Writing To File",
            "Error", JOptionPane.ERROR_MESSAGE );
      }

      // process invalid balance value
      catch ( NumberFormatException numberFormat ) {
         JOptionPane.showMessageDialog( this,
         "Bad Balance", "Invalid Number Format",
         JOptionPane.ERROR_MESSAGE );
      }

   }  // end method addRecord

}  // end class UpdateDialog

// class for creating new records
class NewDialog extends JInternalFrame {
   private RandomAccessFile file;  
   private BankUI userInterface;

   // set up GUI
   public NewDialog( RandomAccessFile newFile )
   {
      super( "New Record" );

      file = newFile;

      // attach user interface to dialog
      userInterface = new BankUI( 4 );
      getContentPane().add( userInterface,
         BorderLayout.CENTER );

      // set up Save Changes button and register listener
      JButton saveButton = userInterface.getDoTask1Button();
      saveButton.setText( "Save Changes" );

      saveButton.addActionListener(

         new ActionListener() {
         
            // add new record to file
            public void actionPerformed( ActionEvent event ) 
            {
               addRecord( getRecord() );
              setVisible( false ); 
               userInterface.clearFields(); 
            }  

         }  // end anonymous inner class

      ); // end call to addActionListener

      JButton cancelButton = userInterface.getDoTask2Button();
      cancelButton.setText( "Cancel" );

      cancelButton.addActionListener(

         new ActionListener() {

            // dismiss dialog without storing new record
            public void actionPerformed( ActionEvent event ) 
            {
              setVisible( false ); 
              userInterface.clearFields();
            }  

         }  // end anonymous inner class

      ); // end call to addActionListener

      setSize( 300, 150 );
      setVisible( false );

   }  // end constructor

   // get record from file
   private RandomAccessAccountRecord getRecord() 
   {
      RandomAccessAccountRecord record = 
         new RandomAccessAccountRecord();

      // get record from file
      try {
         JTextField accountField = 
            userInterface.getFields()[ BankUI.ACCOUNT ];

         int accountNumber = 
            Integer.parseInt( accountField.getText() );

			
			
			
			
         if ( accountNumber < 1 || accountNumber > 100 ) {
            JOptionPane.showMessageDialog( this,
               "Account Does Not Exist",
               "Error", JOptionPane.ERROR_MESSAGE );
            return record;
         }

         // seek to record location
         file.seek((long) ( accountNumber - 1 ) * 
            RandomAccessAccountRecord.size() );

         // read record from file
         record.read( file );
		 
		 

		 
		 
      }

      // process invalid account number format
      catch ( NumberFormatException numberFormat ) {
         JOptionPane.showMessageDialog( this,
            "Account Does Not Exist", "Invalid Number Format",
            JOptionPane.ERROR_MESSAGE );
      }

      // process file processing problems
      catch ( IOException ioException ) {
         JOptionPane.showMessageDialog( this,
            "Error Reading File",
            "Error", JOptionPane.ERROR_MESSAGE );
      }

      return record;

   }  // end method getRecord

   // add record to file
   public void addRecord( RandomAccessAccountRecord record )
   {
      String[] fields = userInterface.getFieldValues();
      
      if ( record.getAccount() != 0 ) {
         JOptionPane.showMessageDialog( this,
            "Record Already Exists",
            "Error", JOptionPane.ERROR_MESSAGE );
         return;
      }

      // output the values to the file
      try {

         // set account, first name, last name and balance
         // for record
         record.setAccount( Integer.parseInt( 
            fields[ BankUI.ACCOUNT ] ) );
         record.setFirstName( fields[ BankUI.FIRSTNAME ] );
         record.setLastName( fields[ BankUI.LASTNAME ] );
         record.setBalance( Double.parseDouble(
            fields[ BankUI.BALANCE ] ) );

         // seek to record location
         file.seek( ( record.getAccount() - 1 ) * 
            RandomAccessAccountRecord.size() );

         // write record
         record.write( file );
      } 
 
      // process invalid account or balance format
      catch ( NumberFormatException numberFormat ) {
         JOptionPane.showMessageDialog( this,
            "Invalid Balance", "Invalid Number Format", 
            JOptionPane.ERROR_MESSAGE );
      }

      // process file processing problems
      catch ( IOException ioException ) {
         JOptionPane.showMessageDialog( this,
            "Error Writing To File",
            "Error", JOptionPane.ERROR_MESSAGE );
      }

   }  // end method addRecord

}  // end class NewDialog

// class for deleting records
class DeleteDialog extends JInternalFrame {
   private RandomAccessFile file;  // file for output
   private BankUI userInterface;

   // set up GUI
   public DeleteDialog( RandomAccessFile deleteFile )
   {
      super( "Delete Record" );

      file = deleteFile;

      // create BankUI with only account field 
      userInterface = new BankUI( 1 );

      getContentPane().add( userInterface,
         BorderLayout.CENTER );

      // set up Delete Record button and register listener
      JButton deleteButton = userInterface.getDoTask1Button();
      deleteButton.setText( "Delete Record" );

      deleteButton.addActionListener(

         new ActionListener() {

            // overwrite existing record
            public void actionPerformed( ActionEvent event )
            {
              addRecord( getRecord() );      
              setVisible( false );  
              userInterface.clearFields();  
            }  

         }  // end anonymous inner class 
         
      ); // end call to addActionListener

      // set up Cancel button and register listener
      JButton cancelButton = userInterface.getDoTask2Button();
      cancelButton.setText( "Cancel" );

      cancelButton.addActionListener(

         new ActionListener() {

            // cancel delete operation by hiding dialog
            public void actionPerformed( ActionEvent event ) 
            {
               setVisible( false );   
            }   

         }  // end anonymous inner class

      ); // end call to addActionListener
  
      // set up listener for account text field
      JTextField accountField = 
         userInterface.getFields()[ BankUI.ACCOUNT ];

      accountField.addActionListener( 

         new ActionListener() {

            public void actionPerformed( ActionEvent event )  
            {
               RandomAccessAccountRecord record = getRecord(); 
            }   

         }  // end anonymous inner class

      ); // end call to addActionListener

      setSize( 300, 100 );
      setVisible( false );

   }  // end constructor

   // get record from file
   private RandomAccessAccountRecord getRecord() 
   {
      RandomAccessAccountRecord record = 
         new RandomAccessAccountRecord();

      // get record from file
      try {
         JTextField accountField = 
            userInterface.getFields()[ BankUI.ACCOUNT ];

         int accountNumber = 
            Integer.parseInt( accountField.getText() );

         if ( accountNumber < 1 || accountNumber > 100 ) {
            JOptionPane.showMessageDialog( this,
               "Account Does Not Exist",
               "Error", JOptionPane.ERROR_MESSAGE );
            return( record );
         }

         // seek to record location and read record
         file.seek( ( accountNumber - 1 ) * 
            RandomAccessAccountRecord.size() );
         record.read( file );
      
         if ( record.getAccount() == 0 )
            JOptionPane.showMessageDialog( this,
               "Account Does Not Exist",
               "Error", JOptionPane.ERROR_MESSAGE );
      }

      // process invalid account number format
      catch ( NumberFormatException numberFormat ) {
         JOptionPane.showMessageDialog( this,
            "Account Does Not Exist",
            "Invalid Number Format",
            JOptionPane.ERROR_MESSAGE );
      }

      // process file processing problems
      catch ( IOException ioException ) {
        JOptionPane.showMessageDialog( this,
           "Error Reading File",
           "Error", JOptionPane.ERROR_MESSAGE );
      }

      return record;

   }  // end method getRecord

   // add record to file
   public void addRecord( RandomAccessAccountRecord record )
   {
      if ( record.getAccount() == 0 )
         return;

      // delete record by setting account number to 0
      try {
         int accountNumber = record.getAccount();

         // seek to record position
         file.seek( ( accountNumber - 1 ) * 
            RandomAccessAccountRecord.size() );

         // set account to 0 and overwrite record
         record.setAccount( 0 ); //THIS IS TO DELETE THE RECORD BY SETTING THE ACCOUNT FIELD TO ZERO !!!!
         record.write( file );
      }

      // process file processing problems
      catch ( IOException ioException ) {
         JOptionPane.showMessageDialog( this,
            "Error Writing To File",
            "Error", JOptionPane.ERROR_MESSAGE );
      }

   }  // end method addRecord

}  // end class DeleteDialog
