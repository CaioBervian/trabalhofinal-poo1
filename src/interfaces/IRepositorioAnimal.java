package interfaces;

import java.util.List;

import enums.Sexo;
import modelos.Animal;

public interface IRepositorioAnimal {
	
	Animal buscarAnimalChip(String chip) ;
	List<Animal> buscarTodos() ;
	List<Animal> buscarAnimalCpfProprietario(String cpfPropietario) ;
	List<Animal> buscarAnimalSexo(Sexo sexo) ;
	List<Animal> buscarAnimalRaca(String codigoRaca) ;
	List<Animal> buscarAnimalEspecie(String codigoEspecie) ;
	
	void inserirAnimal(Animal animal);
	void atualizarAnimal(Animal animal);
	void deletarAnimal(Animal animal);

}
