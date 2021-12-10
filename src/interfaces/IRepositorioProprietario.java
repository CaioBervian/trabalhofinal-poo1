package interfaces;

import java.util.List;

import modelos.Proprietario;

public interface IRepositorioProprietario {
	
	Proprietario buscarProprietarioPorCpf(String cpf) ;
	List<Proprietario> buscarTodos() ;
	
	void inserirProprietario(Proprietario proprietario);
	void atualizarProprietario(Proprietario proprietario);
	void deletarProprietario(Proprietario proprietario);

}
