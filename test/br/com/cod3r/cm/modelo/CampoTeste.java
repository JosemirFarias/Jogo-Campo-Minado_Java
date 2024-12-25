package br.com.cod3r.cm.modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.com.cod3r.cm.excecao.ExplosaoException;

public class CampoTeste {
	
	private Campo campo; 
	
	@BeforeEach
	void iniciarCampo() {
		campo = new Campo(3, 3);
	}
	
	@Test
	void testeVizinhoDistancia1Esquerda() {
		Campo vizinho = new Campo(3, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistancia1Direita() {
		Campo vizinho = new Campo(3, 4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistancia1EmCima() {
		Campo vizinho = new Campo(2, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistancia1EmBaixo() {
		Campo vizinho = new Campo(4, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistancia2() {
		Campo vizinho = new Campo(2, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeNaoVizinho() {
		Campo vizinho = new Campo(1, 1);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);
	}
	
	@Test
	void testeValorPadraoAtibutoMarcado() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacao() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacaoDuasChamadas() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeAbrirNaoMinadoNaoMarcado() {
		assertTrue(campo.abrir());
	}
	
	@Test
	void testeAbriNaoMinadoMarcado() {
		campo.alternarMarcacao();
		assertFalse(campo.abrir());
	}
	
	@Test
	void testeAbrirMinadoMarcado() {
		campo.alternarMarcacao();
		campo.minar();
		assertFalse(campo.abrir());
	}
	
	@Test
	void testeAbrirMinadoNaoMarcado() {
		campo.minar();
		assertThrows(ExplosaoException.class, () -> {
			campo.abrir();
		});
		
	}
	
	@Test
	void testeAbrirComVizinhos1() {
		Campo campo11 = new Campo(1, 1);
		
		Campo campo22 = new Campo(2, 2);
		campo22.adicionarVizinho(campo11);
		
		campo.adicionarVizinho(campo22);
		campo.abrir();
		
		assertTrue(campo22.isAberto() && campo11.isAberto());
	}
	
	@Test
	void testeAbrirComVizinhos2() {
		Campo campo11 = new Campo(1, 1);
		Campo campo12 = new Campo(1, 1);
		campo12.minar();
		
		Campo campo22 = new Campo(2, 2);
		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);
		
		campo.adicionarVizinho(campo22);
		campo.abrir();
		
		assertTrue(campo22.isAberto() && campo11.isFechado());
	}
	
	@Test
    void testeVizinhancaSegura() {
        Campo vizinhoSeguro = new Campo(3, 2);
        Campo vizinhoMinado = new Campo(3, 4);
        vizinhoMinado.minar();

        campo.adicionarVizinho(vizinhoSeguro);
        campo.adicionarVizinho(vizinhoMinado);

        assertFalse(campo.vizinhancaSegura());
	}
	

    @Test
    void testeObjetivoAlcancado() {
    	
        // Caso 1: Campo aberto não minado
        campo.abrir();
        assertTrue(campo.objetivoAlcancado());

        // Caso 2: Campo minado e marcado
        campo.reiniciar();
        campo.minar();
        campo.alternarMarcacao();
        assertTrue(campo.objetivoAlcancado());

        // Caso 3: Campo minado e não marcado
        campo.reiniciar();
        campo.minar();
        assertFalse(campo.objetivoAlcancado());
    }

    @Test
    void testeReiniciar() {
        campo.abrir();
        campo.minar();
        campo.alternarMarcacao();
        campo.reiniciar();

        assertFalse(campo.isAberto());
        assertFalse(campo.isMarcado());
        assertTrue(campo.vizinhancaSegura());
    }

    @Test
    void testeToString() {
        assertEquals("?", campo.toString());
        campo.alternarMarcacao();
        assertEquals("x", campo.toString());

        campo.reiniciar();
        campo.minar();
        try {
            campo.abrir();
        } catch (ExplosaoException e) {
        	
            // Ignorar para testar toString
        }
        assertEquals("*", campo.toString());
    }

}
