package application;

// Java core packages
import java.io.Serializable;

public class AccountRecord implements Serializable {
   private int account;
   private String firstName;
   private String lastName;
   private double balance;
  private int socialSecurity;
  private  String email;
   
   // no-argument constructor calls other constructor with
   // default values
   public AccountRecord() 
   {
      this( 0, "", "", 0.0,0,"" );
   }
  
   // initialize a record
   public AccountRecord( int account, String firstName,
      String lastName, double balance ,int socialSecurity, String email)
   {
     
	  setAccount( account ); //    this.account = account;
      setFirstName( firstName );
      setLastName( lastName );
      setBalance( balance );
      setemail(email);
      setsocialSecurity(socialSecurity);
	 
   }

   // set account number   
   public void setAccount( int account )
   {
      this.account = account;
   }

   // get account number   
   public int getAccount() 
   { 
      return account; 
   }
   
   // set first name   
   public void setFirstName( String firstName )
   {
      this.firstName = firstName;
   }
   public void setemail( String email )
   {
      this.email = email;
   }
      public String getemail() 
      {
         return email; 
      
   }
   public void setsocialSecurity( int fields )
   {
      this.socialSecurity = fields;
   // get first name  
   }
   public int getsocialSecurity() 
   {
      return socialSecurity; 
   }
   
   public String getFirstName() 
   { 
      return firstName; 
   }
   
   // set last name   
   public void setLastName( String lastName )
   {
      this.lastName = lastName;
   }

   // get last name   
   public String getLastName() 
   {
      return lastName; 
   }
   
   // set balance  
   public void setBalance( double balance )
   {
      this.balance = balance;
   }

   // get balance   
   public double getBalance() 
   { 
      return balance; 
   }
   
  
  }  // end class AccountRecord

