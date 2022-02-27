package aplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.PF;
import entities.PJ;
import entities.TaxPayer;

public class Programa {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		List<TaxPayer> list = new ArrayList<>();
		
		System.out.print("Digite a quantidade de contribuintes:");
		int n = sc.nextInt();
		
		for (int i=1; i<=n; i++) {
			System.out.println("Contribuinte #" + i + " data:");
			System.out.print("É pessoa Fisica ou Juridica? (f/j) ");
			char type = sc.next().charAt(0);
			System.out.print("Nome: ");
			sc.nextLine();
			String nome = sc.nextLine();
			System.out.print("Renda anual: ");
			double renda = sc.nextDouble();
			if (type == 'f') {
				System.out.print("Gastos com saúde: ");
				double gastosSaude = sc.nextDouble();
				list.add(new PF(nome, renda, gastosSaude));
			}
			else {
				System.out.print("Número de funcionários: ");
				int numFunc = sc.nextInt();
				list.add(new PJ(nome, renda, numFunc));
			}
		}
		
		double soma = 0;
		System.out.println();
		System.out.println("TAXAS PAGAS:");
		for (TaxPayer tp : list) {
			double tax = tp.tax();
			System.out.println(tp.getNome() + ": $ " + String.format("%.2f", tax));
			soma += tax;
		}
		
		System.out.println();
		System.out.println("TOTAL DE TAXAS: $ " + String.format("%.2f", soma));
		
		sc.close();
	}

}
