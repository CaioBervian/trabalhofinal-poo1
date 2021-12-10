package repositorios;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import enums.Sexo;
import interfaces.IRepositorioAnimal;
import modelos.Animal;

public  class RepositorioAnimal implements IRepositorioAnimal {
	private final String animaisJSONPath = "jsons/animais.json"; 
	private final Type ANIMAL_LIST_TYPE = new TypeToken<List<Animal>>() {}.getType();
	private final Gson gson = new Gson();

	public Animal buscarAnimalChip(String chip) {
		try {
			return buscarTodos().stream().filter(animal -> animal.getChip().equals(chip)).findFirst().get();
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Animal> buscarAnimalCpfProprietario(String cpfPropietario) {
		return buscarTodos().stream().filter(animal -> animal.getCpfProprietario().equals(cpfPropietario)).collect(Collectors.toList());
	}
	
	public List<Animal> buscarAnimalSexo(Sexo sexo) {
		return buscarTodos().stream().filter(animal -> animal.getSexo().equals(sexo)).collect(Collectors.toList());
	}
	
	public List<Animal> buscarAnimalRaca(String codigoRaca) {
		return buscarTodos().stream().filter(animal -> animal.getCodigoRaca().equals(codigoRaca)).collect(Collectors.toList());
	}
	
	public List<Animal> buscarAnimalEspecie(String codigoEspecie) {
		return buscarTodos().stream().filter(animal -> animal.getCodigoEspecie().equals(codigoEspecie)).collect(Collectors.toList());
	}
	
	public void inserirAnimal(Animal animal) {
		List<Animal> animais = buscarTodos();
		animais.add(animal);
		gravarAnimais(animais);
	}
	
	public void atualizarAnimal(Animal animal) {
		List<Animal> animais = buscarTodos();
		animais = animais.stream().filter(an -> !an.getChip().equals(animal.getChip())).collect(Collectors.toList());
		animais.add(animal);
		gravarAnimais(animais);
	}
	
	public void deletarAnimal(Animal animal) {
		List<Animal> animais = buscarTodos();
		animais = animais.stream().filter(an -> !an.getChip().equals(animal.getChip())).collect(Collectors.toList());
		gravarAnimais(animais);
	}
	
	private void gravarAnimais(List<Animal> animais) {
		try {
			FileWriter f = new FileWriter(animaisJSONPath);
			gson.toJson(animais, f);
			f.close();
		} catch (JsonIOException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Animal> buscarTodos() {
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(animaisJSONPath));
			List<Animal> animais = gson.fromJson(reader, ANIMAL_LIST_TYPE);
			return animais != null ? animais: new ArrayList<>();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}
