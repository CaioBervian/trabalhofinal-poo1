package interfaces;

import java.util.List;

import modelos.Raca;

public interface IRepositorioRaca {
	
	Raca buscarRacaPorRegistro(String registroUnico) ;
	List<Raca> buscarTodos() ;
	
	void inserirRaca(Raca raca);
	void atualizarRaca(Raca raca);
	void deletarRaca(Raca raca);

}
