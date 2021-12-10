package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.annotations.SerializedName;

import enums.Porte;
import enums.Sexo;
import modelos.Animal;
import modelos.Endereco;
import modelos.Especie;
import modelos.Proprietario;
import modelos.Raca;
import repositorios.RepositorioAnimal;
import repositorios.RepositorioEspecie;
import repositorios.RepositorioProprietario;
import repositorios.RepositorioRaca;

public abstract class UtilAcoes {

	private static final Scanner entrada;
	private static final RepositorioAnimal repAnimal = new RepositorioAnimal();
	private static final RepositorioEspecie repEspecie = new RepositorioEspecie();
	private static final RepositorioRaca repRaca = new RepositorioRaca();
	private static final RepositorioProprietario repProprietario = new RepositorioProprietario();

	static {
		entrada = new Scanner(System.in);
	}

	public static void menuInicial() {
		System.out.println("Seja bem-vindo(a) ao sistema Pedigree. Gerencie o Pedigree do seu animal!");
		System.out.println("O que voce deseja fazer? Digite o numero da opçao: \n");
		System.out.println("1 - Opçoes para Proprietarios");
		System.out.println("2 - Opçoes para Raças");
		System.out.println("3 - Opçoes para Especies");
		System.out.println("4 - Opçoes para Animais(Pedigree)");
		System.out.println("9 - Sair");
		System.out.print("Digite Sua opçao: ");

		int resposta = entrada.nextInt();
		if (resposta == 9) {
			sair();
		}
		System.out.println("\n");
		System.out.println("\n=====\n");
		switch (resposta) {
		case 1: {
			menuProprietarios();
			break;
		}
		case 2: {
			menuRacas();
			break;
		}
		case 3: {
			menuEspecies();
			break;
		}
		case 4: {
			menuAnimais();
			break;
		}
		default:
			break;

		}

	}

	private static void menuProprietarios() {
		System.out.println("O que voce deseja fazer? Digite o numero da opçao: \n");
		System.out.println("1 - Listar Proprietarios");
		System.out.println("2 - Cadastrar um Proprietario");
		System.out.println("3 - Alterar um Proprietario");
		System.out.println("4 - Remover um Proprietario");
		System.out.println("0 - Voltar");
		System.out.println("9 - Sair");
		System.out.print("Digite Sua opçao: ");

		int resposta = entrada.nextInt();

		if (resposta == 9) {
			sair();
		}
		System.out.println("\n");
		System.out.println("\n=====\n");

		switch (resposta) {
		case 1: {
			listarProprietarios();
			menuProprietarios();
			break;
		}
		case 2: {
			Proprietario p = cadastrarProprietario();
			if (repProprietario.buscarProprietarioPorCpf(p.getCpf()) != null) {
				System.out.println(">>>Proprietario ja existe!!!<<<");
			} else {
				repProprietario.inserirProprietario(p);
			}

			menuProprietarios();
			break;
		}
		case 3: {
			Proprietario p = cadastrarProprietario();
			if (repProprietario.buscarProprietarioPorCpf(p.getCpf()) == null) {
				System.out.println(">>>Proprietario não encontrado!!!<<<");

			} else {
				repProprietario.atualizarProprietario(p);
			}
			menuProprietarios();
			break;
		}
		case 4: {
			listarProprietarios();
			System.out.print("Digite o cpf que deseja remover: ");
			String cpf = entrada.next();
			System.out.println("\n");
			List<Animal> animais = repAnimal.buscarAnimalCpfProprietario(cpf);
			if (animais.size() > 0) {
				System.out.println(">>>Impossivel deletar. \nProprietario possui vinculo com os seguintes animais:");
				for (Animal a : animais) {
					System.out.println("-" + a.getNome());
				}
			} else {
				Proprietario p = new Proprietario();
				p.setCpf(cpf);
				repProprietario.deletarProprietario(p);
				System.out.println("Proprietario " + cpf + " deletado.\n");
			}
			menuProprietarios();
			break;
		}
		case 0: {
			menuInicial();
			break;
		}
		default:
			break;
		}

	}

	private static void listarProprietarios() {
		int i = 1;
		for (Proprietario p : repProprietario.buscarTodos()) {
			System.out.println(i + " - " + p.mostrar());
			i++;
		}
	}

	private static Proprietario cadastrarProprietario() {
		Proprietario p = new Proprietario();
		Sexo[] sexos = new Sexo[] { Sexo.MASCULINO, Sexo.FEMININO, Sexo.N_BIN, Sexo.N_SPEC };

		System.out.println("===Cadastro de proprietario===\n");
		System.out.print("Nome: ");
		p.setNome(entrada.next());
		System.out.println();
		System.out.print("Cpf: ");
		p.setCpf(entrada.next());
		System.out.println();
		System.out.print("dataNascimento (DD/MM/AAAA): ");
		p.setDataNascimento(entrada.next());
		System.out.println();
		System.out.print("Sexo (1 = Masculino, 2 = Feminino, 3 = Não Binario, 4 = Não Especificado): ");
		try {
			if (entrada.hasNext()) {
				entrada.nextLine();
			}
			p.setSexo(sexos[entrada.nextInt() - 1]);
		} catch (Exception e) {
			p.setSexo(Sexo.N_SPEC);
		}
		System.out.println();
		System.out.println("Endereço: ");

		Endereco e = new Endereco();
		System.out.print("-Rua: ");
		e.setRua(entrada.next());
		System.out.println();
		System.out.print("-numero: ");
		if (entrada.hasNext()) {
			entrada.nextLine();
		}
		e.setNumero(entrada.nextInt());
		System.out.println();
		System.out.print("-Bairro: ");
		System.out.println();
		e.setBairro(entrada.next());
		System.out.print("-Complemento: ");
		System.out.println();
		e.setComplemento(entrada.next());
		System.out.print("-Cidade: ");
		System.out.println();
		e.setCidade(entrada.next());
		System.out.print("-Estado: ");
		System.out.println();
		e.setEstado(entrada.next());
	

		p.setEndereco(e);

		return p;
	}

	private static void menuRacas() {
		System.out.println("O que voce deseja fazer? Digite o numero da opçao: \n");
		System.out.println("1 - Listar Raças");
		System.out.println("2 - Cadastrar uma nova Raça");
		System.out.println("3 - Alterar uma Raça");
		System.out.println("4 - Remover uma Raça");
		System.out.println("0 - Voltar");
		System.out.println("9 - Sair");
		System.out.print("Digite Sua opçao: ");

		int resposta = entrada.nextInt();

		if (resposta == 9) {
			sair();
		}
		System.out.println("\n");
		System.out.println("\n=====\n");

		switch (resposta) {
		case 1: {
			listarRacas();
			menuRacas();
			break;
		}
		case 2: {
			Raca p = cadastrarRaca();
			repRaca.inserirRaca(p);
			menuRacas();
			break;
		}
		case 3: {
			Raca p = cadastrarRaca();
			if (repRaca.buscarRacaPorRegistro(p.getRegistroUnico()) == null) {
				System.out.println(">>>Raça não encontrada!!!<<<");

			} else {
				repRaca.atualizarRaca(p);
			}
			menuRacas();
			break;
		}
		case 4: {
			listarRacas();
			System.out.print("Digite o código da Raça que deseja remover: ");
			String codigoRaca = entrada.next();
			System.out.println("\n");
			List<Animal> animais = repAnimal.buscarAnimalRaca(codigoRaca);
			if (animais.size() > 0) {
				System.out.println(">>>Impossivel deletar. \nRaca possui vinculo com os seguintes animais:");
				for (Animal a : animais) {
					System.out.println("-" + a.getNome());
				}
			} else {
				Raca p = new Raca();
				p.setRegistroUnico(codigoRaca);
				repRaca.deletarRaca(p);
				System.out.println("Raça " + codigoRaca + " deletada.\n");
			}
			menuRacas();
			break;
		}
		case 0: {
			menuInicial();
			break;
		}
		default:
			break;
		}

	}

	private static void listarRacas() {
		int i = 1;
		for (Raca p : repRaca.buscarTodos()) {
			System.out.println(i + " - " + p.getRegistroUnico() + " = " + p.getNome());
			i++;
		}
	}

	private static Raca cadastrarRaca() {
		Raca p = new Raca();

		System.out.println("===Cadastro de raça===\n");
		System.out.print("Nome: ");
		p.setNome(entrada.next());
		System.out.println();
		p.setRegistroUnico(Util.resgistroUnico());

		return p;
	}

	private static void menuEspecies() {
		System.out.println("O que voce deseja fazer? Digite o numero da opçao: \n");
		System.out.println("1 - Listar Especies");
		System.out.println("2 - Cadastrar uma nova Especie");
		System.out.println("3 - Alterar uma Especie");
		System.out.println("4 - Remover uma Especie");
		System.out.println("0 - Voltar");
		System.out.println("9 - Sair");
		System.out.print("Digite Sua opçao: ");

		int resposta = entrada.nextInt();

		if (resposta == 9) {
			sair();
		}
		System.out.println("\n");
		System.out.println("\n=====\n");

		switch (resposta) {
		case 1: {
			listarEspecies();
			menuEspecies();
			break;
		}
		case 2: {
			Especie p = cadastrarEspecie();
			repEspecie.inserirEspecie(p);
			menuEspecies();
			break;
		}
		case 3: {
			Especie p = cadastrarEspecie();
			if (repEspecie.buscarEspeciePorRegistro(p.getRegistroUnico()) == null) {
				System.out.println(">>>Especie não encontrada!!!<<<");

			} else {
				repEspecie.atualizarEspecie(p);
			}
			menuEspecies();
			break;
		}
		case 4: {
			listarEspecies();
			System.out.print("Digite o código da Especie que deseja remover: ");
			String codigoEspecie = entrada.next();
			System.out.println("\n");
			List<Animal> animais = repAnimal.buscarAnimalEspecie(codigoEspecie);
			if (animais.size() > 0) {
				System.out.println(">>>Impossivel deletar. \nEspecie possui vinculo com os seguintes animais:");
				for (Animal a : animais) {
					System.out.println("-" + a.getNome());
				}
			} else {
				Especie p = new Especie();
				p.setRegistroUnico(codigoEspecie);
				repEspecie.deletarEspecie(p);
				System.out.println("Especie " + codigoEspecie + " deletada.\n");
			}
			menuEspecies();
			break;
		}
		case 0: {
			menuInicial();
			break;
		}
		default:
			break;
		}

	}

	private static void listarEspecies() {
		int i = 1;
		for (Especie p : repEspecie.buscarTodos()) {
			System.out.println(i + " - " + p.getRegistroUnico() + " = " + p.getNome());
			i++;
		}
	}

	private static Especie cadastrarEspecie() {
		Especie p = new Especie();

		System.out.println("===Cadastro de raça===\n");
		System.out.print("Nome: ");
		p.setNome(entrada.next());
		System.out.println();
		p.setRegistroUnico(Util.resgistroUnico());

		return p;
	}

	private static void menuAnimais() {
		System.out.println("O que voce deseja fazer? Digite o numero da opçao: \n");
		System.out.println("1 - Listar Animais");
		System.out.println("2 - Cadastrar um Animal");
		System.out.println("3 - Alterar um Animal");
		System.out.println("4 - Remover um Animal");
		System.out.println("0 - Voltar");
		System.out.println("9 - Sair");
		System.out.print("Digite Sua opçao: ");

		int resposta = entrada.nextInt();
		System.out.println("\n");
		System.out.println("\n=====\n");
		if (resposta == 9) {
			sair();
		}

		switch (resposta) {
		case 1: {
			listarAnimais();
			menuAnimais();
			break;
		}
		case 2: {
			Animal p = cadastrarAnimal();
			repAnimal.inserirAnimal(p);
			menuAnimais();
			break;
		}
		case 3: {
			Animal p = cadastrarAnimal();
			if (repAnimal.buscarAnimalChip(p.getChip()) == null) {
				System.out.println(">>>Animal não encontrado!!!<<<");
			} else {
				repAnimal.atualizarAnimal(p);
			}
			menuAnimais();
			break;
		}
		case 4: {
			listarAnimais();
			System.out.print("Digite o código do animal que deseja remover: ");
			String codigo = entrada.next();
			System.out.println("\n");
			Animal animal = repAnimal.buscarAnimalChip(codigo);
			if (animal != null) {
				repAnimal.deletarAnimal(animal);
				System.out.println("Animal" + codigo + " deletado.\n");
			}
			menuAnimais();
			break;
		}
		case 0: {
			menuInicial();
			break;
		}
		default:
			break;
		}

	}

	private static void listarAnimais() {
		int i = 1;
		for (Animal p : repAnimal.buscarTodos()) {
			System.out.println(i + " - " + p.mostrar());
			i++;
		}
	}

	private static Animal cadastrarAnimal() {
		Animal p = new Animal();
		System.out.println("===Cadastro de Animal===\n");
		System.out.println("Iremos agora cadastrar nosso Pedigree em passos...\n");
		cadastrarAnimalPasso1(p);

		return p;
	}

	private static void cadastrarAnimalPasso1(Animal animal) {
		Sexo[] sexos = new Sexo[] { Sexo.MASCULINO, Sexo.FEMININO, Sexo.N_BIN, Sexo.N_SPEC };
		Porte[] portes = new Porte[] { Porte.PEQUENO, Porte.MEDIO, Porte.GRANDE };
		System.out.println(">>>Passo 1 de 6 \n");

		System.out.println("===Cadastro de dados básicos do animal===\n");
		animal.setChip(Util.resgistroUnico());
		System.out.print("Nome: ");
		animal.setNome(entrada.next());
		System.out.println();
		System.out.print("Peso: ");
		if (entrada.hasNext()) {
			entrada.nextLine();
		}
		animal.setPeso(entrada.nextDouble());
		System.out.println();
		System.out.print("dataNascimento (DD/MM/AAAA): ");
		animal.setDataNascimento(entrada.next());
		System.out.println();
		System.out.print("Sexo (1 = Masculino, 2 = Feminino: ");
		try {
			if (entrada.hasNext()) {
				entrada.nextLine();
			}
			animal.setSexo(sexos[entrada.nextInt() - 1]);
		} catch (Exception e) {
			animal.setSexo(Sexo.N_SPEC);
		}
		System.out.println();
		System.out.print("Porte (1 = Pequeno, 2 = Medio, 3 = Grande): ");
		try {
			if (entrada.hasNext()) {
				entrada.nextLine();
			}
			animal.setPorte(portes[entrada.nextInt() - 1]);
		} catch (Exception e) {
			animal.setPorte(Porte.MEDIO);
		}
		cadastrarAnimalPasso2(animal);
	}

	private static void cadastrarAnimalPasso2(Animal animal) {
		int resposta = -1;
		do {
			System.out.println(">>>Passo 2 de 6 \n");
			System.out.println("===Cadastro de Antecessores de <" + animal.getNome() + ">===\n");
			System.out.println("O que voce deseja fazer? Digite o numero da opçao: \n");
			System.out.println("1 - Cadastrar Antecessor para " + animal.getNome());
			System.out.println("2 - Digitar código do Antecessor para " + animal.getNome());
			System.out.println("3 - Prosseguir para o próximo passo");
			System.out.println("9 - Sair");
			System.out.print("Digite Sua opçao: ");

			resposta = entrada.nextInt();

			if (resposta == 9) {
				sair();
			}
			System.out.println("\n");
			System.out.println("\n=====\n");

			if (resposta == 1) {
				Animal an = new Animal();
				cadastrarAnimalPasso1(an);
				repAnimal.inserirAnimal(an);
				List<String> ans = animal.getAntecessores();
				ans.add(an.getChip());
				animal.setAntecessores(ans);
			} else if (resposta == 2) {

				System.out.print("Digite o código do antecessor: ");

				try {
					Animal an = repAnimal.buscarAnimalChip(entrada.next());
					List<String> ans = animal.getAntecessores();
					ans.add(an.getChip());
					animal.setAntecessores(ans);
				} catch (Exception e) {
					System.out.println("Antecessor não encontrado. Tente novamente ou cadastre um.\n");
				}
			}
		} while (resposta == 1 || resposta == -1 || resposta == 2);
		if (resposta == 3) {
			cadastrarAnimalPasso3(animal);
		}

	}

	private static void cadastrarAnimalPasso3(Animal animal) {

		System.out.println(">>>Passo 3 de 6 \n");
		System.out.println("===Cadastro de Proprietario de <" + animal.getNome() + ">===\n");
		System.out.println("O que voce deseja fazer? Digite o numero da opçao: \n");
		System.out.println("1 - Cadastrar Proprietario para " + animal.getNome());
		System.out.println("2 - Digitar cpf do Proprietario para " + animal.getNome());
		System.out.println("9 - Sair");
		System.out.print("Digite Sua opçao: ");

		int resposta = entrada.nextInt();
		if (resposta == 9) {
			sair();
		}
		System.out.println("\n");
		System.out.println("\n=====\n");

		if (resposta == 1) {
			Proprietario prop = cadastrarProprietario();
			if (repProprietario.buscarProprietarioPorCpf(prop.getCpf()) == null) {
				repProprietario.inserirProprietario(prop);
			}
			animal.setCpfProprietario(prop.getCpf());
		} else if (resposta == 2) {

			System.out.print("Digite o CPF: ");

			try {
				Proprietario prop = repProprietario.buscarProprietarioPorCpf(entrada.next());
				if (prop == null) {
					System.out.println("Proprietario não encontrado. Tente novamente ou cadastre um.\n");
					cadastrarAnimalPasso3(animal);
				} else {
					animal.setCpfProprietario(prop.getCpf());
				}
			} catch (Exception e) {
				System.out.println("Proprietario não encontrado. Tente novamente ou cadastre um.\n");
				cadastrarAnimalPasso3(animal);
			}
		}

		cadastrarAnimalPasso4(animal);
	}

	private static void cadastrarAnimalPasso4(Animal animal) {

		System.out.println(">>>Passo 4 de 6 \n");
		System.out.println("===Cadastro de Raça de <" + animal.getNome() + ">===\n");
		System.out.println("O que voce deseja fazer? Digite o nÃºmero da opçao: \n");
		System.out.println("1 - Cadastrar Raça para " + animal.getNome());
		System.out.println("2 - Digitar código da Raça para " + animal.getNome());
		System.out.println("9 - Sair");
		System.out.print("Digite Sua opçao: ");

		int resposta = entrada.nextInt();
		if (resposta == 9) {
			sair();
		}
		System.out.println("\n");
		System.out.println("\n=====\n");

		if (resposta == 1) {
			Raca raca = cadastrarRaca();
			repRaca.inserirRaca(raca);
			animal.setCodigoRaca(raca.getRegistroUnico());
		} else if (resposta == 2) {

			System.out.print("Digite o código da raça: ");

			try {
				Raca raca = repRaca.buscarRacaPorRegistro(entrada.next());
				if (raca == null) {
					System.out.println("Raça nÃ£o encontrada. Tente novamente ou cadastre uma nova raça.\n");
					cadastrarAnimalPasso4(animal);
				} else {
					animal.setCodigoRaca(raca.getRegistroUnico());
				}
			} catch (Exception e) {
				System.out.println("Raça nÃ£o encontrada. Tente novamente ou cadastre uma nova raça.\n");
				cadastrarAnimalPasso4(animal);
			}
		}
		cadastrarAnimalPasso5(animal);
	}

	private static void cadastrarAnimalPasso5(Animal animal) {

		System.out.println(">>>Passo 5 de 6 \n");
		System.out.println("===Cadastro de Especie de <" + animal.getNome() + ">===\n");
		System.out.println("O que voce deseja fazer? Digite o numero da opçao: \n");
		System.out.println("1 - Cadastrar Especie para " + animal.getNome());
		System.out.println("2 - Digitar codigo da Especie para " + animal.getNome());
		System.out.println("9 - Sair");
		System.out.print("Digite Sua opçao: ");

		int resposta = entrada.nextInt();
		if (resposta == 9) {
			sair();
		}
		System.out.println("\n");
		System.out.println("\n=====\n");

		if (resposta == 1) {
			Especie especie = cadastrarEspecie();
			repEspecie.inserirEspecie(especie);
			animal.setCodigoEspecie(especie.getRegistroUnico());
		} else if (resposta == 2) {

			System.out.print("Digite o código da especie: ");

			try {
				Especie especie = repEspecie.buscarEspeciePorRegistro(entrada.next());
				if (especie == null) {
					System.out.println("Especie não encontrada. Tente novamente ou cadastre uma nova especie.\n");
					cadastrarAnimalPasso5(animal);
				} else {
					animal.setCodigoEspecie(especie.getRegistroUnico());
				}
			} catch (Exception e) {
				System.out.println("Especie não encontrada. Tente novamente ou cadastre uma nova especie.\n");
				cadastrarAnimalPasso5(animal);
			}
		}

		cadastrarAnimalPasso6(animal);

	}

	private static void cadastrarAnimalPasso6(Animal animal) {
		int resposta = -1;
		Map<Path, File> files = new HashMap<>();
		do {
			System.out.println(">>>Passo 6 de 6 \n");
			System.out.println("===Adicionar as Fotos de <" + animal.getNome() + ">===\n");
			System.out.println("O que voce deseja fazer? Digite o nÃºmero da opçao: \n");
			System.out.println("1 - Adicionar foto para " + animal.getNome());
			System.out.println("2 - Finalizar");
			System.out.println("9 - Sair");
			System.out.print("Digite Sua opçao: ");

			resposta = entrada.nextInt();
			if (resposta == 9) {
				sair();
			}
			System.out.println("\n");
			System.out.println("\n=====\n");

			if (resposta == 1) {
				files.putAll(Util.retornarImagem(animal.getChip()));
			}

		} while (resposta != 2);

		for (Map.Entry<Path, File> entry : files.entrySet()) {
			InputStream in;
			try {
				in = new FileInputStream(entry.getValue());
				Files.copy(in, entry.getKey(), StandardCopyOption.REPLACE_EXISTING);
				List<String> fotos = animal.getFotos();
				fotos.add(entry.getKey().toString());
				animal.setFotos(fotos);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private static void sair() {
		System.exit(0);
	}

}
