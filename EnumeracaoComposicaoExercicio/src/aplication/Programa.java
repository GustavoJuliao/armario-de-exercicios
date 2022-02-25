package aplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import entidades.Client;
import entidades.Order;
import entidades.OrderItem;
import entidades.Product;
import entidades.enums.OrderStatus;


public class Programa {

	public static void main(String[] args) throws ParseException {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		System.out.print("Enter client data: \n");
		System.out.print("Name: \n");
		String name = sc.nextLine();
		System.out.print("Email: \n");
		String email = sc.nextLine();
		System.out.print("Birth Date (DD/MM/YYYY): \n");
		Date birthDate = sdf.parse(sc.next());
		
		
		Client client = new Client(name, email, birthDate);

		
		System.out.print("Enter order data: \n");
		System.out.print("Status: \n");
		OrderStatus status = OrderStatus.valueOf(sc.next());
		
		Order order = new Order(new Date(), status, client);
				
		System.out.print("How many items to this order? \n");
		int n = sc.nextInt();		
		for (int i=1; i<=n; i++) {
			System.out.println("Enter #" + i + " item data:");
			System.out.print("Product Name: ");
			sc.nextLine(); // esse nextLine existe somente para skipar a linha 44 e assim a linha 45 pegar o valor pro productPrice.
			String productName = sc.nextLine();
			System.out.print("Product Price: ");
			double productPrice = sc.nextDouble();
			
			Product product = new Product(productName, productPrice);
			
			System.out.print("Quantity: ");
			int quantity = sc.nextInt();
			OrderItem orderItem  = new OrderItem(quantity, productPrice, product);
			order.addItem(orderItem);
		}
		
		
		System.out.println();
		System.out.println("ORDER SUMMARY:");
		System.out.println(order);
		
		sc.close();
	}

}
