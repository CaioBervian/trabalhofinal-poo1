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

import interfaces.IRepositorioEspecie;
import modelos.Especie;

public  class RepositorioEspecie implements IRepositorioEspecie {
	private final String especiesJSONPath = "jsons/especies.json"; 
	private final Type ESPECIE_LIST_TYPE = new TypeToken<List<Especie>>() {}.getType();
	private final Gson gson = new Gson();

	public Especie buscarEspeciePorRegistro(String registroUnico) {
		try {
			return buscarTodos().stream().filter(especie -> especie.getRegistroUnico().equals(registroUnico)).findFirst().get();
		} catch (Exception e) {
			return null;
		}
	}
	
	public void inserirEspecie(Especie especie) {
		List<Especie> especies = buscarTodos();
		especies.add(especie);
		gravarEspecies(especies);
	}
	
	public void atualizarEspecie(Especie especie) {
		List<Especie> especies = buscarTodos();
		especies = especies.stream().filter(an -> !an.getRegistroUnico().equals(especie.getRegistroUnico())).collect(Collectors.toList());
		especies.add(especie);
		gravarEspecies(especies);
	}
	
	public void deletarEspecie(Especie especie) {
		List<Especie> especies = buscarTodos();
		especies = especies.stream().filter(an -> !an.getRegistroUnico().equals(especie.getRegistroUnico())).collect(Collectors.toList());
		gravarEspecies(especies);
	}
	
	private void gravarEspecies(List<Especie> especies) {
		try {
			FileWriter f = new FileWriter(especiesJSONPath);
			gson.toJson(especies, f);
			f.close();
		} catch (JsonIOException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Especie> buscarTodos() {
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(especiesJSONPath));
			List<Especie> especies = gson.fromJson(reader, ESPECIE_LIST_TYPE);
			return especies != null ? especies: new ArrayList<>();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}
