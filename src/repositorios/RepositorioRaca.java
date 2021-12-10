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

import interfaces.IRepositorioRaca;
import modelos.Raca;

public  class RepositorioRaca implements IRepositorioRaca {
	private final String racasJSONPath = "jsons/racas.json"; 
	private final Type RACA_LIST_TYPE = new TypeToken<List<Raca>>() {}.getType();
	private final Gson gson = new Gson();

	public Raca buscarRacaPorRegistro(String registroUnico) {
		try {
			return buscarTodos().stream().filter(raca -> raca.getRegistroUnico().equals(registroUnico)).findFirst().get();
		}catch (Exception e) {
			return null;
		}
	}
	
	public void inserirRaca(Raca raca) {
		List<Raca> racas = buscarTodos();
		racas.add(raca);
		gravarRacas(racas);
	}
	
	public void atualizarRaca(Raca raca) {
		List<Raca> racas = buscarTodos();
		racas = racas.stream().filter(an -> !an.getRegistroUnico().equals(raca.getRegistroUnico())).collect(Collectors.toList());
		racas.add(raca);
		gravarRacas(racas);
	}
	
	public void deletarRaca(Raca raca) {
		List<Raca> racas = buscarTodos();
		racas = racas.stream().filter(an -> !an.getRegistroUnico().equals(raca.getRegistroUnico())).collect(Collectors.toList());
		gravarRacas(racas);
	}
	
	private void gravarRacas(List<Raca> racas) {
		try {
			FileWriter f = new FileWriter(racasJSONPath);
			gson.toJson(racas, f);
			f.close();
		} catch (JsonIOException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Raca> buscarTodos() {
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(racasJSONPath));
			List<Raca> racas = gson.fromJson(reader, RACA_LIST_TYPE);
			return racas != null ? racas: new ArrayList<>();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}
