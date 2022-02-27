package entities;

import exceptions.BusinessException;

public class Conta {

	private Integer numero;
	private String proprietario;
	private Double saldo, limiteSaque;
	
	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getProprietario() {
		return proprietario;
	}

	public void setProprietario(String proprietario) {
		this.proprietario = proprietario;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Double getLimiteSaque() {
		return limiteSaque;
	}

	public void setLimiteSaque(Double limiteSaque) {
		this.limiteSaque = limiteSaque;
	}

	
	
	public Conta(Integer numero, String proprietario, Double saldo, Double limiteSaque) {
		this.numero = numero;
		this.proprietario = proprietario;
		this.saldo = saldo;
		this.limiteSaque = limiteSaque;
	}

	public void deposito(double saque) {
		saldo += saque;
	}
	
	public void saque(double saque) {
		validarSaque(saque);
		saldo -= saque;
	}
	
	private void validarSaque(double saque) {
		if(saque > getLimiteSaque()) { 
			throw new BusinessException("Erro de saque: Quantia excede o limite de saque."); 
		}
		else if(saque > getSaldo()){ 
			throw new BusinessException("Erro de saque: Saldo insuficiente."); 
		}
	}

}
