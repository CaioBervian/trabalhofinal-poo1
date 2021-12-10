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

import interfaces.IRepositorioProprietario;
import modelos.Proprietario;

public class RepositorioProprietario implements IRepositorioProprietario {
	private final String proprietariosJSONPath = "jsons/proprietarios.json"; 
	private final Type PROPRIETARIO_LIST_TYPE = new TypeToken<List<Proprietario>>() {}.getType();
	private final Gson gson = new Gson();

	public Proprietario buscarProprietarioPorCpf(String cpf) {
		try {
			return buscarTodos().stream().filter(proprietario -> proprietario.getCpf().equals(cpf)).findFirst().get();
		}catch (Exception e) {
			return null;
		}
	}
	
	public void inserirProprietario(Proprietario proprietario) {
		List<Proprietario> proprietarios = buscarTodos();
		proprietarios.add(proprietario);
		gravarProprietarios(proprietarios);
	}
	
	public void atualizarProprietario(Proprietario proprietario) {
		List<Proprietario> proprietarios = buscarTodos();
		proprietarios = proprietarios.stream().filter(an -> !an.getCpf().equals(proprietario.getCpf())).collect(Collectors.toList());
		proprietarios.add(proprietario);
		gravarProprietarios(proprietarios);
	}
	
	public void deletarProprietario(Proprietario proprietario) {
		List<Proprietario> proprietarios = buscarTodos();
		proprietarios = proprietarios.stream().filter(an -> !an.getCpf().equals(proprietario.getCpf())).collect(Collectors.toList());
		gravarProprietarios(proprietarios);
	}
	
	private void gravarProprietarios(List<Proprietario> proprietarios) {
		try {
			FileWriter f = new FileWriter(proprietariosJSONPath);
			gson.toJson(proprietarios, f);
			f.close();
		} catch (JsonIOException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Proprietario> buscarTodos() {
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(proprietariosJSONPath));
			List<Proprietario> proprietarios = gson.fromJson(reader, PROPRIETARIO_LIST_TYPE);
			return proprietarios != null ? proprietarios: new ArrayList<>();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}
