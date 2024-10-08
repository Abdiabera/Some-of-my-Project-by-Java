
// RandomAccessAccountRecord.java
// Subclass of AccountRecord for random access file programs.


// Java core packages
import java.io.*;

public class RandomAccessAccountRecord extends   AccountRecord {
  
   // no-argument constructor calls other constructor
   // with default values
   public RandomAccessAccountRecord()
   {
      this( 0, "", "", 0.0 );
   }

   // initialize a RandomAccessAccountRecord
   public RandomAccessAccountRecord( int account, 
      String firstName, String lastName, double balance )
   {
      super( account, firstName, lastName, balance );
   }

   // read a record from specified RandomAccessFile
   public void read( RandomAccessFile file ) throws IOException
   {
      setAccount( file.readInt() );
      setFirstName( padName( file,15 ));
      setLastName( padName( file,28 ));
      setBalance( file.readDouble() );
	 
   }

   // ensure that name is proper length
   private String padName( RandomAccessFile file, int ln )
      throws IOException
   {
      char name[] = new char[ ln ], temp;

      for ( int count = 0; count < name.length; count++ ) {
         temp = file.readChar();
         name[ count ] = temp;
      }     
      
      return new String( name ).replace( '\0', ' ' );
   }

   // write a record to specified RandomAccessFile
   public void write( RandomAccessFile file ) throws IOException
   {
      file.writeInt( getAccount() );
      writeName( file, getFirstName(),15 );
      writeName( file, getLastName(),28 );
      file.writeDouble( getBalance() );
	 
   }

   // write a name to file; maximum of 15 characters
   private void writeName( RandomAccessFile file, String name,int ln)
      throws IOException
   {
      StringBuffer buffer = null;

      if ( name != null ) 
         buffer = new StringBuffer( name );
      else 
         buffer = new StringBuffer( ln);

      buffer.setLength( ln );
      file.writeChars( buffer.toString() );
   }

   // NOTE: This method contains a hard coded value for the
   // size of a record of information.
   public static int size() 
   { 
     return (30+4+56+8); 
   }

}  // end class RandomAccessAccountRecord






