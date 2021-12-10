package modelos;

public class Especie {
	
	private String nome;
	private String registroUnico;
	
	
	public Especie(String nome, String registroUnico) {
		this.nome = nome;
		this.registroUnico = registroUnico;
	}
	
	public Especie() {}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRegistroUnico() {
		return registroUnico;
	}
	public void setRegistroUnico(String registroUnico) {
		this.registroUnico = registroUnico;
	}
	
	

}
