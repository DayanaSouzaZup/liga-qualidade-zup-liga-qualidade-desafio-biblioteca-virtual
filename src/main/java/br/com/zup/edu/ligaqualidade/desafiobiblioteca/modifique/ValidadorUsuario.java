package br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique;

import java.time.LocalDate;
import java.util.Map;

import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.DadosExemplar;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.DadosUsuario;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.TipoExemplar;

public class ValidadorUsuario {

	private ValidadorUsuario() {
	}

	static boolean validarEmprestimoDeUsuarioPadrao(LocalDate dataExpiracao, DadosUsuario usuario,
			DadosExemplar exemplar, LocalDate dataDevolucao, Map<Integer, Integer> countEmprestimosPadrao) {
		Integer countEmprestimosFeito = countEmprestimosPadrao.get(usuario.idUsuario);
		boolean estourouMaximoDeLivros = countEmprestimosFeito != null && countEmprestimosFeito >= 5;
		return dataDevolucao.isBefore(dataExpiracao) && DataUtils.validarLimiteDeDiasPorEmprestimo(dataDevolucao)
				&& exemplar.tipo == TipoExemplar.LIVRE && !estourouMaximoDeLivros;
	}

	static boolean validarEmprestimoDePesquisador(LocalDate dataExpiracao, LocalDate dataDevolucao) {
		return dataDevolucao == null
				|| dataDevolucao.isBefore(dataExpiracao) && DataUtils.validarLimiteDeDiasPorEmprestimo(dataDevolucao);
	}

	static boolean validarEmprestimoDeExemplarRestritoParaPesquisador(LocalDate dataExpiracao, DadosUsuario usuario,
			DadosExemplar exemplar, LocalDate dataDevolucao, Map<Integer, Integer> countEmprestimosRestrito) {

		return dataDevolucao.isBefore(dataExpiracao) && exemplar.tipo == TipoExemplar.RESTRITO;
	}

	static boolean validarLimiteDeExemplaresExpirados(LocalDate dataExpiracao, DadosUsuario usuario,
			DadosExemplar exemplar, LocalDate dataDevolucao, Map<Integer, Integer> countEmprestimosPadrao) {

		Integer countEmprestimosFeito = countEmprestimosPadrao.get(usuario.idUsuario);
		boolean estourouMaximoDeLivrosExpirados = countEmprestimosFeito != null && countEmprestimosFeito == 2;
		return dataDevolucao.isAfter(dataExpiracao) && DataUtils.validarLimiteDeDiasPorEmprestimo(dataDevolucao)
				&& exemplar.tipo == TipoExemplar.LIVRE && !estourouMaximoDeLivrosExpirados;
	}
}
