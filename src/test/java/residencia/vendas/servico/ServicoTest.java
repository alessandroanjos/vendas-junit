package residencia.vendas.servico;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import residencia.vendas.entidade.Item;
import residencia.vendas.entidade.Pedido;
import residencia.vendas.entidade.TipoItem;
import residencia.vendas.entidade.Usuario;
import residencia.vendas.servico.ServicoVendas;

public class ServicoTest {
	
	private static Usuario usuario;
	private static ServicoVendas servicoVendas;
	private static Pedido pedido;
	
	
	@BeforeAll
	public static void setUpTest() { 
		servicoVendas = new ServicoVendas();
		usuario = new Usuario("Joao", "joao@email.com" , "rua da aurora");
	}
	
	
    
    @Test
    public void testDeveNaoCobrarFrete() {
    	Item carrinhoHotWhells = new Item("Carrinho A", "Carrinho de controle remoto", new BigDecimal(10.0), new BigDecimal(1.5), TipoItem.BRINQUEDOS);
    	
    	List<Item> itens = new ArrayList<Item>();
    	itens.add(carrinhoHotWhells);
    	
    	pedido = servicoVendas.fecharVenda(usuario, itens);
    	
    	assertEquals(new BigDecimal("10.0"), pedido.precoFinal());
    	
    }
    
    
    @Test
    public void testDeveCobrarFrete2ReaisPorQuiloAcimaDe2QuilosAbaixoDe10Quilos() {
    	Item carrinhoHotWhells = new Item("Carrinho A", "Carrinho de controle remoto", new BigDecimal(10.0), new BigDecimal(1.5), TipoItem.BRINQUEDOS);
    	Item mouse = new Item("Mouse", "Mouse Dell", new BigDecimal(50.0), new BigDecimal(3.0), TipoItem.COMPUTADORES);
    	
    	List<Item> itens = new ArrayList<Item>();
    	itens.add(carrinhoHotWhells);
    	itens.add(mouse);
    	
    	pedido = servicoVendas.fecharVenda(usuario, itens);
    	
    	assertEquals(new BigDecimal("69.00"), pedido.precoFinal());
    }
    
    
    
    @Test
    public void testDeveCobrarFrete4ReaisPorQuiloAcima10QuilosAte50Quilos() {	
    	Item carrinhoHotWhells = new Item("Carrinho A", "Carrinho de controle remoto", new BigDecimal(10.0), new BigDecimal(1.5), TipoItem.BRINQUEDOS);
    	Item mouse = new Item("Mouse", "Mouse Dell", new BigDecimal(50.0), new BigDecimal(3.0), TipoItem.COMPUTADORES);
    	Item televisao = new Item("Televisão", "Televisão 54 polegadas Sansung", new BigDecimal(300.0), new BigDecimal(12.0), TipoItem.ELETRONICOS);
    	
    	List<Item> itens = new ArrayList<Item>();
    	itens.add(carrinhoHotWhells);
    	itens.add(mouse);
    	itens.add(televisao);
    	
    	pedido = servicoVendas.fecharVenda(usuario, itens);
    	
    	assertEquals(new BigDecimal("426.00"), pedido.precoFinal());
    }
    
    
    @Test
    public void testDeveCobrarFrete7ReaisPorQuiloAcimaDe50QuilosOPeso() {
    	Item carrinhoHotWhells = new Item("Carrinho A", "Carrinho de controle remoto", new BigDecimal(10.0), new BigDecimal(1.5), TipoItem.BRINQUEDOS);
    	Item mouse = new Item("Mouse", "Mouse Dell", new BigDecimal(50.0), new BigDecimal(3.0), TipoItem.COMPUTADORES);
    	Item televisao = new Item("Televisão", "Televisão 54 polegadas Sansung", new BigDecimal(300.0), new BigDecimal(48.0), TipoItem.ELETRONICOS);
    	
    	List<Item> itens = new ArrayList<Item>();
    	itens.add(carrinhoHotWhells);
    	itens.add(mouse);
    	itens.add(televisao);
    	
    	pedido = servicoVendas.fecharVenda(usuario, itens);
    	
    	assertEquals(new BigDecimal("727.50"), pedido.precoFinal());
    	
    }
    
    
    @Test
    public void testDeveCobrarFrete10ReaisPorMaisDe5Itens() {    	
    	Item carrinhoHotWhells = new Item("Carrinho A", "Carrinho de controle remoto", new BigDecimal(10.0), new BigDecimal(0.5), TipoItem.BRINQUEDOS);
    	Item mouse = new Item("Mouse", "Mouse Dell", new BigDecimal(50.0), new BigDecimal(0.5), TipoItem.COMPUTADORES);
    	Item chaveEletronica = new Item("Chave Eletronica", "Chave PADO", new BigDecimal(10.0), new BigDecimal(0.5), TipoItem.ELETRONICOS);
    	Item bermuda = new Item("Bermuda", "Bermuda R", new BigDecimal(40.0), new BigDecimal(0.3), TipoItem.ROUPAS);
    	Item bola = new Item("Bola", "Bola FIFA 2022", new BigDecimal(50.0), new BigDecimal(0.3), TipoItem.ESPORTES);
    	
    	List<Item> itens = new ArrayList<Item>();
    	itens.add(carrinhoHotWhells);
    	itens.add(mouse);
    	itens.add(chaveEletronica);
    	itens.add(bermuda);
    	itens.add(bola);
   
    	pedido = servicoVendas.fecharVenda(usuario, itens);
    	
    	assertEquals(new BigDecimal("174.16"), pedido.precoFinal());
    	
    }
    
    
    @Test
    public void testDeveDarDesconto5PercentNoFreteComPeloMenosTresTiposDeItensIguais() {
    	Item carrinhoHotWhells = new Item("Carrinho A", "Carrinho de controle remoto", new BigDecimal(10.0), new BigDecimal(0.5), TipoItem.BRINQUEDOS);
    	Item mouse = new Item("Mouse", "Mouse Dell", new BigDecimal(50.0), new BigDecimal(0.5), TipoItem.COMPUTADORES);
    	Item chaveEletronica = new Item("Chave Eletronica", "Chave PADO", new BigDecimal(10.0), new BigDecimal(0.2), TipoItem.ELETRONICOS);
    	Item bermuda = new Item("Bermuda", "Bermuda R", new BigDecimal(40.0), new BigDecimal(0.1), TipoItem.ROUPAS);
    	Item bola = new Item("Bola", "Bola FIFA 2022", new BigDecimal(50.0), new BigDecimal(0.1), TipoItem.ESPORTES);
    	Item apito = new Item("Apito", "Apito FIFA 2022", new BigDecimal(2.0), new BigDecimal(0.1), TipoItem.ESPORTES);
    	Item caneleira = new Item("Caneleira", "Caneleira FIFA 2022", new BigDecimal(5.0), new BigDecimal(0.1), TipoItem.ESPORTES);
    	
    	List<Item> itens = new ArrayList<Item>();
    	itens.add(carrinhoHotWhells);
    	itens.add(mouse);
    	itens.add(chaveEletronica);
    	itens.add(bermuda);
    	itens.add(bola);
    	itens.add(apito);
    	itens.add(caneleira);
    	
    	pedido = servicoVendas.fecharVenda(usuario, itens);
    	
    	assertEquals(new BigDecimal("176.50"), pedido.precoFinal());
    	
    }
    
    //TODO: Implementar teste acima com o cenário que o peso passa os 2Kg
    
    @Test
    public void testDeveDarDescontoDe10PercentCasoValorSeja500Reais() {
    	Item carrinhoHotWhells = new Item("Carrinho A", "Carrinho de controle remoto", new BigDecimal(500.0), new BigDecimal(0.5), TipoItem.BRINQUEDOS);
    	Item mouse = new Item("Mouse", "Mouse Dell", new BigDecimal(50.0), new BigDecimal(0.5), TipoItem.COMPUTADORES);
  
    	
    	List<Item> itens = new ArrayList<Item>();
    	itens.add(carrinhoHotWhells);
    	itens.add(mouse);
    	
    	pedido = servicoVendas.fecharVenda(usuario, itens);
    	assertEquals(new BigDecimal("495.00"), pedido.precoFinal());
    }
    
    
    @Test
    public void testDeveDarDescontoDe20PercentCasoValorSeja1000Reais() {
    	Item carrinhoHotWhells = new Item("Carrinho A", "Carrinho de controle remoto", new BigDecimal(1000.0), new BigDecimal(0.5), TipoItem.BRINQUEDOS);
    	Item mouse = new Item("Mouse", "Mouse Dell", new BigDecimal(50.0), new BigDecimal(0.5), TipoItem.COMPUTADORES);
  
    	
    	List<Item> itens = new ArrayList<Item>();
    	itens.add(carrinhoHotWhells);
    	itens.add(mouse);
    	
    	pedido = servicoVendas.fecharVenda(usuario, itens);
    	
    	assertEquals(new BigDecimal("840.00"), pedido.precoFinal());
    }

}
