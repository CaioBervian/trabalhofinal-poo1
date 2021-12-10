package interfaces;

import java.util.List;

import modelos.Especie;

public interface IRepositorioEspecie {
	
	Especie buscarEspeciePorRegistro(String registroUnico) ;
	List<Especie> buscarTodos() ;
	
	void inserirEspecie(Especie especie);
	void atualizarEspecie(Especie especie);
	void deletarEspecie(Especie especie);

}
