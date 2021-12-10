package modelos;


import com.google.gson.annotations.SerializedName;

import enums.Sexo;

public class Proprietario {
	
	private String nome;
	private String cpf;
	private String dataNascimento;
	private Endereco endereco;
	@SerializedName("sexo")
	private Sexo sexo;

	
	public Proprietario(String nome, String cpf, String dataNascimento, Endereco endereco, Sexo sexo) {
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.endereco = endereco;
		this.sexo = sexo;
	}
	
	public Proprietario() {	}
	
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public String mostrar() {
		StringBuilder s = new StringBuilder();
		return s.append("Animal:").append("\n")
		.append("nome: ").append(nome).append("\n")
		.append("identificacao: ").append(cpf).append("\n")
		.append("data nascimento: ").append(dataNascimento).append("\n")
		.append("sexo: ").append(sexo.name()).append("\n")
		.append("endereco: ").append("\n")
			.append("\trua: ").append(endereco.getRua()).append(", ")
			.append("\tnumero: ").append(endereco.getNumero()).append(", ")
			.append("\tbairro: ").append(endereco.getBairro()).append(", ")
			.append("\tcomplemento: ").append(endereco.getComplemento()).append(", ")
			.append("\tcidade: ").append(endereco.getCidade()).append(", ")
			.append("\testado: ").append(endereco.getEstado())
		.append("\n").toString();
	}
	
	
	

}
