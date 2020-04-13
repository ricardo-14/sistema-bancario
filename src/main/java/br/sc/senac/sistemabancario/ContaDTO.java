package br.sc.senac.sistemabancario;

public class ContaDTO {

	public static final ContaDTO NULL_VALUE = new ContaDTO("", Double.valueOf(0.0), "");

	private final String titular;
	private Double saldo;
	private final String numeroConta;

	public ContaDTO(final String titular, final Double saldo, final String numeroConta) {
		this.titular = titular;
		this.saldo = saldo;
		this.numeroConta = numeroConta;

	}

	public String getTitular() {
		return this.titular;
	}

	public Double getSaldo() {
		return this.saldo;
	}

	public String getNumeroConta() {
		return this.numeroConta;
	}

	public void depositar(double valorDeposito) {
		this.saldo += valorDeposito;
	}

	public boolean sacar(double valorSaque) {
		if (valorSaque > this.saldo) {
			return false;
		}
		this.saldo -= valorSaque;
		return true;
	}

	public boolean transferirPara(ContaDTO contaDestino, double valor) {
		boolean isPodeTransferir = this.sacar(valor);
		if (isPodeTransferir) {
			contaDestino.depositar(valor);
		}
		return isPodeTransferir;
	}

}
