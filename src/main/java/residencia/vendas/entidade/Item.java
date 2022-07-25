package residencia.vendas.entidade;

import java.math.BigDecimal;

public record Item(String nome, String descricao, BigDecimal preco, BigDecimal peso, TipoItem tipo) {
}
