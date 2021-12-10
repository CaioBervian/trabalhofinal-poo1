package enums;

import com.google.gson.annotations.SerializedName;

public enum Sexo {
	@SerializedName("Feminino")
	FEMININO,
	@SerializedName("Masculino")
	MASCULINO,
	@SerializedName("Nao_binario")
	N_BIN,
	@SerializedName("Nao_especificado")
	N_SPEC
}
