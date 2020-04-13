package br.sc.senac.sistemabancario;

import java.util.ArrayList;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/conta")

public class ContaService {

	// private Map<String, Conta> contas = new HashMap<>();
	private List<ContaDTO> contas = new ArrayList<>();

	@PostMapping("/add-default")
	public void addDefault() {

		ContaDTO conta = new ContaDTO("Gabriel", 500.00, "1234-1");
		contas.add(conta);

		conta = new ContaDTO("Macalister", 500.00, "1234-2");
		contas.add(conta);

		conta = new ContaDTO("Marcelo", 500.00, "1234-3");
		contas.add(conta);
	}

	@GetMapping("/list")
	public List<ContaDTO> list() {
		return this.contas;
	}

	@GetMapping("/{id}/details")

	public ResponseEntity<ContaDTO> getConta(@PathVariable Long id) {
		if (id >= contas.size() || id < 0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		int index = id.intValue();
		ContaDTO conta = contas.get(index);
		return new ResponseEntity<>(conta, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")

	public ResponseEntity<ContaDTO> removeConta(@PathVariable Long id) {
		if (id >= contas.size() || id < 0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		int index = id.intValue();
		ContaDTO conta = contas.remove(index);
		return new ResponseEntity<>(conta, HttpStatus.OK);
	}

	@PostMapping("/add")
	public Long addConta(@RequestBody ContaDTO conta) {
		contas.add(conta);
		Long id = Long.valueOf(contas.size() - 1);
		return id;
	}

	@PutMapping("/{id}")
	public ResponseEntity<ContaDTO> uptadeConta(@PathVariable Long id, @RequestBody ContaDTO updatedConta) {
		if (id >= contas.size() || id < 0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		int index = id.intValue();
		ContaDTO oldConta = contas.remove(index);
		contas.add(index, updatedConta);
		return new ResponseEntity<>(oldConta, HttpStatus.OK);
	}

	@PatchMapping("/depositar/{id}/{valorDeposito}")
	public double efetuarDeposito(@PathVariable Long id, @PathVariable double valorDeposito) {
		int index = id.intValue();
		ContaDTO conta = contas.get(index);

		conta.depositar(valorDeposito);

		return valorDeposito;
	}

	@PatchMapping("/sacar/{id}/{valorSaque}")
	public double efetuarSaque(@PathVariable Long id, @PathVariable double valorSaque) {
		int index = id.intValue();
		ContaDTO conta = contas.get(index);

		if (!conta.sacar(valorSaque)) {
			return 0;
		}
		return valorSaque;
	}

	@PatchMapping("/transferir/{numeroContaOrigem}/{numeroContaDestino}/{valor}")
	public double efetuarTransferencia(@PathVariable int numeroContaOrigem, @PathVariable int numeroContaDestino,
			@PathVariable double valor) {

		ContaDTO ContaDestino = contas.get(numeroContaDestino);
		contas.get(numeroContaOrigem).transferirPara(ContaDestino, valor);
		return valor;
	}
}
