package modelos;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.annotations.SerializedName;

import enums.Porte;
import enums.Sexo;
import repositorios.RepositorioAnimal;
import repositorios.RepositorioEspecie;
import repositorios.RepositorioProprietario;
import repositorios.RepositorioRaca;

public class Animal {
	
	private double peso;
	private String nome;
	private List<String> fotos;
	private String dataNascimento;
	private String chip; //registro unico
	@SerializedName("sexo")
	private Sexo sexo; //Feminino, Masculino
	private String cpfProprietario;
	private List<String> antecessores; //codigos dos antecessores
	private String codigoRaca; //registroUnico raca
	private String codigoEspecie; //registroUnico especie
	@SerializedName("porte")
	private Porte porte;
	
	public Animal() {
	}
	
	public Animal(double peso, String nome, List<String> fotos, String dataNascimento, String chip, Sexo sexo,
			String cpfProprietario, List<String> antecessores, String codigoRaca, String codigoEspecie, Porte porte) {
		this.peso = peso;
		this.nome = nome;
		this.fotos = fotos;
		this.dataNascimento = dataNascimento;
		this.chip = chip;
		this.sexo = sexo;
		this.cpfProprietario = cpfProprietario;
		this.antecessores = antecessores;
		this.codigoRaca = codigoRaca;
		this.codigoEspecie = codigoEspecie;
		this.porte = porte;
	}
	

	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<String> getFotos() {
		return fotos;
	}
	public void setFotos(List<String> fotos) {
		this.fotos = fotos;
	}
	public String getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getChip() {
		return chip;
	}
	public void setChip(String chip) {
		this.chip = chip;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	public String getCpfProprietario() {
		return cpfProprietario;
	}
	public void setCpfProprietario(String cpfProprietario) {
		this.cpfProprietario = cpfProprietario;
	}
	public List<String> getAntecessores() {
		return antecessores;
	}
	public void setAntecessores(List<String> antecessores) {
		this.antecessores = antecessores;
	}
	public String getCodigoRaca() {
		return codigoRaca;
	}
	public void setCodigoRaca(String codigoRaca) {
		this.codigoRaca = codigoRaca;
	}
	public String getCodigoEspecie() {
		return codigoEspecie;
	}
	public void setCodigoEspecie(String codigoEspecie) {
		this.codigoEspecie = codigoEspecie;
	}
	public Porte getPorte() {
		return porte;
	}
	public void setPorte(Porte porte) {
		this.porte = porte;
	}
	
	public String mostrar() {
		StringBuilder s = new StringBuilder();
		Raca r = new RepositorioRaca().buscarRacaPorRegistro(codigoRaca);
		Especie e = new RepositorioEspecie().buscarEspeciePorRegistro(codigoEspecie);
		Proprietario p = new RepositorioProprietario().buscarProprietarioPorCpf(cpfProprietario);
		return s.append("Animal:").append("\n")
		.append("nome: ").append(nome).append("\n")
		.append("identificacao: ").append(chip).append("\n")
		.append("data nascimento: ").append(dataNascimento).append("\n")
		.append("peso: ").append(peso).append("\n")
		.append("raca: ").append(r != null? r.getNome() : "").append("\n")
		.append("sexo: ").append(sexo.name()).append("\n")
		.append("especie: ").append(e != null ? e.getNome() : "").append("\n")
		.append("porte: ").append(porte.name()).append("\n")
		.append("proprietario: ").append(p != null ? p.getNome() : "").append("\n")
		.append("fotos: ").append(String.join(", ", fotos)).append("\n")
		.append("antepassados: ").append(String.join(", ", antecessores.stream().map((a) -> {
			Animal an = new RepositorioAnimal().buscarAnimalChip(a);
			return an.getNome() + "(" + an.getChip() + ")";
		}).collect(Collectors.toList()))).append("\n").toString();
	}
	
	
	
}
