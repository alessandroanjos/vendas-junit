package residencia.vendas.servico;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import residencia.vendas.entidade.Item;
import residencia.vendas.entidade.Pedido;
import residencia.vendas.entidade.TipoItem;
import residencia.vendas.entidade.Usuario;

public class ServicoVendas {

	Pedido fecharVenda(Usuario usuario, List<Item> itens) {
		List<TipoItem> itensTemp = new ArrayList<TipoItem>();
		Map<TipoItem, Integer> mapContagemPorTipo = new HashMap<TipoItem, Integer>();
		
		BigDecimal percent5 = new BigDecimal("0.05");
		BigDecimal percent10 = new BigDecimal("0.10");
		BigDecimal percent20 = new BigDecimal("0.20");
		
		BigDecimal frete = new BigDecimal(0.0);
		BigDecimal valorPago = new BigDecimal(0.0);
		BigDecimal peso = new BigDecimal(0.0);
		int qntItens = itens.size();

		for (Item item : itens) {
			valorPago = valorPago.add(item.preco());
			peso = peso.add(item.peso()).setScale(2, RoundingMode.DOWN);
			itensTemp.add(item.tipo());
		}

		frete = calculaValorFrete(frete, peso);

		if (qntItens >= 5) {
			// cobrar frete R$ 10,00
			frete = frete.add(new BigDecimal(10.0));
		}

		// se possui mais de dois itens do mesmo tipo
		// aplica desconto de 5% no valor do frete

		boolean aplicarDescontoCincoPorcentoNoFrete = verificaSePossuiDoisItemsDoMesmoTipoItem(itensTemp,
				mapContagemPorTipo);

		frete = aplicaDescontoCincoPorcentoFrete(percent5, frete, aplicarDescontoCincoPorcentoNoFrete);

		// se o carrinho custar mais de 500,00
		// aplicar desconto de 10% nos itens
		BigDecimal totalDescontoItens = calculaDecontoSeCarrrinhoCustarMaisDeCemReais(percent10, valorPago);

		// se o carrinho custar mais de 1000
		// aplicar desconto de 20% nos itens

		totalDescontoItens = calculaDescontoSeCarrinhoCustarMaisDeMilReais(percent20, valorPago, totalDescontoItens);

		Pedido p = calculaPedidoDoCarrinho(usuario, itens, frete, valorPago, totalDescontoItens);

		return p;
	}

	private Pedido calculaPedidoDoCarrinho(Usuario usuario, List<Item> itens, BigDecimal frete, BigDecimal valorPago,
			BigDecimal totalDescontoItens) {
		valorPago = valorPago.add(frete);
		valorPago = valorPago.subtract(totalDescontoItens);

		LocalDate date = LocalDate.of(2022, Month.JULY, 20);
		Pedido p = new Pedido(usuario, itens, date, valorPago);
		return p;
	}

	private BigDecimal calculaDescontoSeCarrinhoCustarMaisDeMilReais(BigDecimal percent20, BigDecimal valorPago,
			BigDecimal totalDescontoItens) {
		if (valorPago.doubleValue() >= 1000) {
			totalDescontoItens = valorPago.multiply(percent20);
		}
		return totalDescontoItens;
	}

	private BigDecimal calculaDecontoSeCarrrinhoCustarMaisDeCemReais(BigDecimal percent10, BigDecimal valorPago) {
		BigDecimal totalDescontoItens = new BigDecimal("0.0");

		if (valorPago.doubleValue() >= 500 && valorPago.doubleValue() < 1000) {
			totalDescontoItens = valorPago.multiply(percent10);
		}
		return totalDescontoItens;
	}

	private BigDecimal aplicaDescontoCincoPorcentoFrete(BigDecimal percent5, BigDecimal frete,
			boolean aplicarDescontoCincoPorcentoNoFrete) {
		BigDecimal totalDescontoFrete = new BigDecimal("0.0");
		if (aplicarDescontoCincoPorcentoNoFrete) {
			totalDescontoFrete = frete.multiply(percent5).setScale(2, RoundingMode.DOWN);
			// frete = frete.multiply(percent5);
			frete = frete.subtract(totalDescontoFrete);
		}
		return frete;
	}

	private boolean verificaSePossuiDoisItemsDoMesmoTipoItem(List<TipoItem> itensTemp,
			Map<TipoItem, Integer> mapContagemPorTipo) {
		for (int i = 0; i < itensTemp.size(); ++i) {
			TipoItem tipoItem = itensTemp.get(i);
			// comparação se já existe um tipoItem no Map
			if (mapContagemPorTipo.containsKey(tipoItem)) {
				mapContagemPorTipo.put(tipoItem, mapContagemPorTipo.get(tipoItem) + 1);
			} else {
				mapContagemPorTipo.put(tipoItem, 1);
			}
		}

		boolean aplicarDescontoCincoPorcentoNoFrete = false;
		for (Map.Entry<TipoItem, Integer> entry : mapContagemPorTipo.entrySet()) {
			TipoItem key = entry.getKey();
			Integer val = entry.getValue();
			if (val > 2) {
				aplicarDescontoCincoPorcentoNoFrete = true;
			}
		}
		return aplicarDescontoCincoPorcentoNoFrete;
	}

	private BigDecimal calculaValorFrete(BigDecimal frete, BigDecimal peso) {
		if (peso.doubleValue() <= 2) {
			System.out.println("Não cobrou o frete");
		} else if (peso.doubleValue() > 2 && peso.doubleValue() <= 10) {
			// cobrar frete R$ 2,00 por quilo
			BigDecimal freteTemporario = new BigDecimal(2.0);
			freteTemporario = freteTemporario.multiply(peso);
			frete = frete.add(freteTemporario);
			
		} else if (peso.doubleValue() > 10 && peso.doubleValue() <= 50) {
			// cobrar frete R$ 4,00 por quilo
			BigDecimal freteTemporario = new BigDecimal(4.0);
			freteTemporario = freteTemporario.multiply(peso);
			frete = frete.add(freteTemporario);
			
		} else {
			// cobrar frete R$ 7,00 por quilo
			BigDecimal freteTemporario = new BigDecimal(7.0);
			freteTemporario = freteTemporario.multiply(peso);
			frete = frete.add(freteTemporario);
		}
		return frete;
	}
}
