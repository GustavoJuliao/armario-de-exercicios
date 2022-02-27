package application;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

import entities.Conta;
import exceptions.BusinessException;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		System.out.print("Informe os dados da conta \n");
		System.out.println("Numero: ");
		int num = sc.nextInt();
		sc.nextLine();
		System.out.print("Titular: ");
		String proprietario = sc.next();
		System.out.print("Saldo Inicial: ");
		double saldo = sc.nextDouble();
		System.out.print("Limite de saque: ");
		double limiteSaque = sc.nextDouble();
		
		Conta cc = new Conta(num, proprietario, saldo, limiteSaque);
		
		System.out.print("\nInforme uma quantia para sacar: ");
		double saque = sc.nextDouble();
		
		try {
			cc.saque(saque);
			System.out.printf("Novo saldo: %.2f%n", cc.getSaldo());
		}catch(BusinessException e) {
			System.out.println(e.getMessage());
		}
		

 		sc.close();
	}

}
