package modelos;

public class Raca {
	
	private String nome;
	private String registroUnico;
	
	
	public Raca(String nome, String registroUnico) {
		this.nome = nome;
		this.registroUnico = registroUnico;
	}
	
	public Raca() {}
	
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
