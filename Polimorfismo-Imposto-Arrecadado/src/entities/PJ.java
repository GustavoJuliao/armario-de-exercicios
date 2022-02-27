package entities;

public class PJ extends TaxPayer {
	
	private int numFuncionario;
	
	public PJ(String nome, Double rendaAnual, int numFuncionario) {
		super(nome, rendaAnual);
		this.numFuncionario = numFuncionario;
	}

	public int getNumFuncionario() {
		return numFuncionario;
	}

	public void setNumFuncionario(int numFuncionario) {
		this.numFuncionario = numFuncionario;
	}

	@Override
	public Double tax() {
		if (numFuncionario > 10) {
			return getRendaAnual() * 0.14;
		}
		else {
			return getRendaAnual() * 0.16;
		}
	}


}
