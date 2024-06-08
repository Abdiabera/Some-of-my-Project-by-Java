package PackPro;
/* CIT242 Data Structures
 * Matthew Daly
 * XML Final
 *
 * This class implements a fairly pimped-out GUI for the Product Maintenance
 * application. It can be controlled entirely from the keyboard (see 'Help'
 * for keyboard shortcuts) and allows quick navigation via next/previous
 * buttons.
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// NOTE: Remember to recompile Product.java!
public class ProductMaintAppGUI extends JFrame {
	private ProductDAO productDAO = null;

	private JTextField codeField;
	private JTextField idField;
	private JTextField descriptionField;
	private JTextField amountField;
	private JTextField priceField;
	private JTextField discountField;

	private JList productList;
	private DefaultListModel productListModel;

	private JButton saveButton;

	private ArrayList<JTextField> editableTextFields;
	private ArrayList<JTextField> allTextFields;

	public ProductMaintAppGUI() {
		super("Product Maintenance Application");

		try {
			productDAO = DAOFactory.getProductDAO();
		} catch (Exception e) {
			this.handleFatalException(e);
		}

		editableTextFields = new ArrayList<JTextField>();
		allTextFields = new ArrayList<JTextField>();

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(5, 2));

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(590, 390);

		codeField = addLabelAndTextField("Code", 100, true, centerPanel);
		idField = new JTextField("-1"); // not shown
		descriptionField = addLabelAndTextField("Description", 100, true, centerPanel);
		amountField = addLabelAndTextField("Amount", 10, true, centerPanel);
		priceField = addLabelAndTextField("Price", 10, true, centerPanel);
		discountField = addLabelAndTextField("Discount", 10, true, centerPanel);

		add(centerPanel, BorderLayout.CENTER);

		JPanel eastPanel = new JPanel(new GridLayout(2, 1));

		// NOTE: The list will contain product descriptions only, due to my
		// workaround with the Product class's toString() method.
		productListModel = new DefaultListModel();
		productList = new JList(productListModel);
		productList.setLayoutOrientation(JList.VERTICAL);
		productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		try {
			refreshProductList();
		} catch (Exception e) {
			handleFatalException(e);
		}
		productList.setSelectedIndex(0);

		JScrollPane scrollPane = new JScrollPane(productList);
		eastPanel.add(scrollPane);

		JPanel eastPanelBottom = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridy = 0;
		JPanel navButtonPanel = new JPanel(new GridLayout(1, 2));
		JButton prevButton = new JButton("<<   Prev");
		prevButton.setMnemonic('[');
		prevButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				int index = productList.getSelectedIndex();
				if (index != 0) {
					index--;
				} else {
					index = productList.getModel().getSize() - 1;
				}
				productList.setSelectedIndex(index);
			}
		});
		navButtonPanel.add(prevButton);

		JButton nextButton = new JButton("Next   >>");
		nextButton.setMnemonic(']');
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				int index = productList.getSelectedIndex();
				if (index != productList.getModel().getSize() - 1) {
					index++;
				} else {
					index = 0;
				}
				productList.setSelectedIndex(index);
			}
		});
		navButtonPanel.add(nextButton);
		eastPanelBottom.add(navButtonPanel, constraints);

		constraints.gridy++;
		JButton viewButton = new JButton("View Selected");
		viewButton.setMnemonic('v');
		viewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				viewProduct();
			}
		});
		eastPanelBottom.add(viewButton, constraints);

		constraints.gridy++;
		JButton viewAllButton = new JButton("View All");
		viewAllButton.setMnemonic('a');
		viewAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				viewAllProducts();
			}
		});
		eastPanelBottom.add(viewAllButton, constraints);

		constraints.gridy++;
		JButton editButton = new JButton("Edit");
		editButton.setMnemonic('e');
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				editProduct();
			}
		});
		eastPanelBottom.add(editButton, constraints);

		constraints.gridy++;
		JButton deleteButton = new JButton("Delete");
		deleteButton.setMnemonic('d');
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				deleteProduct();
			}
		});
		eastPanelBottom.add(deleteButton, constraints);

		constraints.gridy++;
		JButton helpButton = new JButton("Help");
		helpButton.setMnemonic('h');
		helpButton.addActionListener(new ActionListener() {
			@Override
			
			
			public void actionPerformed(ActionEvent ae) {
				help();
			}
		});
		eastPanelBottom.add(helpButton, constraints);

		eastPanel.add(eastPanelBottom);
		add(eastPanel, BorderLayout.EAST);

		// south panel
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 2));

		JButton newButton = new JButton("New");
		newButton.setMnemonic('n');
		newButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				clearTextFields();
				setFieldsEditable(true);
				saveButton.setEnabled(true);
				codeField.requestFocus();
			}
		});
		southPanel.add(newButton);

		saveButton = new JButton("Save");
		saveButton.setEnabled(false);
		saveButton.setMnemonic('s');
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					saveProduct();
				} catch (Exception e) {
					handleFatalException(e);
				}
			}
		});
		southPanel.add(saveButton);
		add(southPanel, BorderLayout.SOUTH);

		// center the frame
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - this.getWidth()) / 4;
		int y = (screenSize.height - this.getHeight()) / 4;
		this.setLocation(x, y);
		setVisible(true);
	}

	private void refreshProductList() throws Exception {
		List<Product> products = productDAO.getProducts();

		productListModel.clear();
		for (Product product : products) {
			productListModel.addElement(product);
		}
	}

	private void viewProduct() {
		Product product = getSelectedProduct();
		if (product != null) {
			clearTextFields();
			setFieldsEditable(false);
			populateFieldsFromProduct(product);
			saveButton.setEnabled(false);
		}
	}

	private void viewAllProducts() {
		List<Product> products = productDAO.getProducts();
		StringBuilder productsTextBuilder = new StringBuilder(products.size() * 100);

		for (Product p : products) {
			productsTextBuilder.append(p.toStringAllFields()).append("\n");
		}

		JTextArea textArea = new JTextArea(20, 25);
		String productsText = productsTextBuilder.toString();
		productsText = productsText.substring(0, productsText.length() - 2);
		textArea.setText(productsText);
		textArea.setCaretPosition(0);
		textArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textArea);
		JOptionPane.showMessageDialog(this, scrollPane, "All Products", JOptionPane.INFORMATION_MESSAGE);
	}

	// NOTE: Editing the product's code field is not allowed, since the DAO uses
	// that to look them up in its internal data structure.
	private void editProduct() {
		Product product = getSelectedProduct();
		if (product != null) {
			clearTextFields();
			setFieldsEditable(true);
			codeField.setEditable(false);
			descriptionField.requestFocus();
			populateFieldsFromProduct(product);
			saveButton.setEnabled(true);
		}
	}

	private void deleteProduct() {
		Product product = getSelectedProduct();
		if (product != null) {
			// ENTER should always press the focused button
			UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);

			int result = JOptionPane.showOptionDialog(this,
					"Are you sure you want to delete this product?\n\n" + product + "\n\n", "Confirm Delete",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

			if (result == JOptionPane.YES_OPTION) {
				int index = productList.getSelectedIndex();
				try {
					productDAO.deleteProduct(product);
					refreshProductList();
					index--;
					JOptionPane.showMessageDialog(this, "Product deleted successfully.\n", "Success",
							JOptionPane.WARNING_MESSAGE);
				} catch (Exception e) {
					handleFatalException(e);
				}

				if (idField.getText().equals(product.getId())) {
					clearTextFields();
					setFieldsEditable(false);
					saveButton.setEnabled(false);
				}
				productList.setSelectedIndex(index);
			} else {
				return;
			}
		}
	}

	private void help() {
		String helpMessage = "New                     -  alt+n\n" + "Save                    -  alt+s\n\n"
				+ "Prev                     -  alt+[\n" + "Next                     -  alt+]\n\n"
				+ "View Selected    -  alt+v\n" + "View All               -  alt+a\n"
				+ "Edit                      -  alt+e\n" + "Delete                 -  alt+d\n"
				+ "Help                     -  alt+h\n";

		JOptionPane.showMessageDialog(this, helpMessage, "Help", JOptionPane.INFORMATION_MESSAGE);
	}

	private void saveProduct() throws Exception {
		Product product = populateProductFromFields(); // remember the selection
		int index = productList.getSelectedIndex();
		if (product != null) {
			// if the hidden id is -1, this is a new product; otherwise the id
			// should be the empty string. id string length is checked first
			// since calling parseInt() on a null string throws an exception.
			if (product.getId().length() == 0 || Integer.parseInt(product.getId()) != -1) {
				productDAO.updateProduct(product);
			} else {
				productDAO.addProduct(product);
				index++;
			}

			JOptionPane.showMessageDialog(this, "Product saved successfully:\n\n" + product + "\n\n", "Success",
					JOptionPane.WARNING_MESSAGE);
		}
		clearTextFields();
		setFieldsEditable(false);
		refreshProductList();
		productList.setSelectedIndex(index);
		saveButton.setEnabled(false);
	}

	private Product getSelectedProduct() {
		int selectedIndex = productList.getSelectedIndex();

		if (selectedIndex == -1) {
			JOptionPane.showMessageDialog(this, "Please select a product from the list.\n", "Error",
					JOptionPane.PLAIN_MESSAGE);
			return null;
		}
		return (Product) productListModel.getElementAt(selectedIndex);
	}

	private void setFieldsEditable(boolean b) {
		for (JTextField textField : editableTextFields) {
			textField.setEditable(b);
		}
	}

	protected void clearTextFields() {
		for (JTextField textField : allTextFields) {
			textField.setText("");
		}
		idField.setText("-1");
	}

	private void handleFatalException(Exception e) {
		JOptionPane.showMessageDialog(this, e.getMessage() + "\n", "Fatal Error", JOptionPane.ERROR_MESSAGE);
		e.printStackTrace();
		System.exit(1);
	}

	private Product populateProductFromFields() {
		try {
			Product product = new Product();

			product.setCode(codeField.getText());
			product.setId(idField.getText());
			product.setDescription(descriptionField.getText());
			product.setAmount((int) Integer.parseInt(amountField.getText()));
			product.setPrice(getDoubleValue(priceField.getText(), "Price"));
			product.setDiscount(discountField.getText().charAt(0));

			return product;
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this, nfe.getMessage() + "\n", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	private void populateFieldsFromProduct(Product product) {
		codeField.setText(product.getCode());
		idField.setText(product.getId());
		descriptionField.setText(product.getDescription());
		amountField.setText(String.valueOf(product.getAmount()));
		priceField.setText(String.valueOf(product.getPrice()));
		discountField.setText(String.valueOf(product.getDiscount()));
	}

	private double getDoubleValue(String input, String fieldName) {
		try {
			return Double.parseDouble(input);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(fieldName + " must contain a numeric value!");
		}
	}

	private JTextField addLabelAndTextField(String label, int fieldLength, boolean textFieldIsEditable, JPanel panel) {
		panel.add(new JLabel(label));

		JTextField textField = new JTextField(fieldLength);
		textField.setEditable(false);
		panel.add(textField);

		if (textFieldIsEditable) {
			editableTextFields.add(textField);
		}
		allTextFields.add(textField);

		return textField;
	}

	public static void main(String[] args) {
		new ProductMaintAppGUI();
	}
}
