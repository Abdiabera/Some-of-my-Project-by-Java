package DBS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductSQL implements ProductDAO {

	@Override
	public Product getProduct(String code) {
		
	
		try {
			
			Connection con = DatabaseUtils.connectDatabase();
			
			Statement stat = con.createStatement();
			ResultSet r= stat.executeQuery("SELECT * FROM product WHERE code=\"" + code + "\"");
		
			while(r.next()) {
				
				Product n = new Product();
				
				n.setCode(r.getString("code"));
				n.setDescription(r.getString("description"));
				n.setAmount(r.getInt("amount"));
				n.setPrice(r.getDouble("price"));
				
				n.setDiscount(r.getString("discount").charAt(0));
				return n;
				
			}
			
			con.close();
	
			
			
			}catch(Exception e) {
				
			}
			return null;
	}

	@Override
	public ArrayList<Product> getProducts() {
		
		try {
			
		Connection con = DatabaseUtils.connectDatabase();
		
		Statement stat = con.createStatement();
		ResultSet r= stat.executeQuery("SELECT * FROM product");
		
		ArrayList<Product> p = new ArrayList<Product>();
		
		while(r.next()) {
			
			Product n = new Product();
			
			n.setCode(r.getString("code"));
			n.setDescription(r.getString("description"));
			n.setAmount(r.getInt("amount"));
			n.setPrice(r.getDouble("price"));
			
			n.setDiscount(r.getString("discount").charAt(0));
			p.add(n);
			
		}
		
		con.close();
	return p;	
		
		
		}catch(Exception e) {
			
		}
		return null;
	}

	@Override
	public boolean addProduct(Product p) {
	
		
		try {
			 Connection con = DatabaseUtils.connectDatabase();
			 Statement stat = con.createStatement();
			 String query = "INSERT INTO product (code, description, amount, price, discount) VALUES (" 
						+ "\"" + p.getCode() + "\", "
						+ "\"" + p.getDescription() + "\", "
						+ "\"" + Integer.toString(p.getAmount()) + "\", "
						+ "\"" + p.getFormattedPrice().replace("$", "") + "\", "
						+ "\"" + p.getDiscount() + "\""
						+ ");"; 

			 int count = stat.executeUpdate(query);
			 
			 con.close();
			 return true;
			 
		}catch(Exception e) {
			
		}
	
		
		return false;
	}

	@Override
	public boolean updateProduct(Product p) {
		
		
		try {
			 Connection con = DatabaseUtils.connectDatabase();
			 Statement stat = con.createStatement();
			 String query = "UPDATE product SET\n"
						+ "description=\"" + p.getDescription() + "\",\n"
						+ "amount=\"" + Integer.toString(p.getAmount()) + "\",\n"
						+ "price=\"" + p.getFormattedPrice().replace("$", "") + "\",\n"
						+ "discount=\"" + ("" + p.getDiscount()) + "\"" 
						+ "\nWHERE code=\"" + p.getCode() + "\""; 

			 stat.executeUpdate(query);
			 
			 con.close();
			 return true;
			 
		}catch(Exception e) {
			
		}
		return false;
	}

	@Override
	public boolean deleteProduct(Product p) {
		try {
		Connection con = DatabaseUtils.connectDatabase();
		 Statement stat = con.createStatement();
		 String query = "DELETE FROM product WHERE code=\"" + p.getCode() + "\"";
		 int count = stat.executeUpdate(query);
		 
		 con.close();
		 return true;
		 
	}catch(Exception e) {
		
	}

		return false;
}
}