package residencia.vendas.entidade;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record Pedido(
		Usuario usuario,
		List<Item> itens,
		LocalDate dataPedido,
		BigDecimal precoFinal
		) {}
