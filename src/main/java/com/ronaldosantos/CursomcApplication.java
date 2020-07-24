package com.ronaldosantos;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ronaldosantos.domain.Categoria;
import com.ronaldosantos.domain.Cidade;
import com.ronaldosantos.domain.Cliente;
import com.ronaldosantos.domain.Endereco;
import com.ronaldosantos.domain.Estado;
import com.ronaldosantos.domain.ItemPedido;
import com.ronaldosantos.domain.Pagamento;
import com.ronaldosantos.domain.PagamentoComBoleto;
import com.ronaldosantos.domain.PagamentoComCartao;
import com.ronaldosantos.domain.Pedido;
import com.ronaldosantos.domain.Produto;
import com.ronaldosantos.domain.enums.EstadoPagamento;
import com.ronaldosantos.domain.enums.TipoCliente;
import com.ronaldosantos.repositories.CategoriaRepository;
import com.ronaldosantos.repositories.CidadeRepository;
import com.ronaldosantos.repositories.ClienteRepository;
import com.ronaldosantos.repositories.EnderecoRepository;
import com.ronaldosantos.repositories.EstadoRepository;
import com.ronaldosantos.repositories.ItemPedidoRepository;
import com.ronaldosantos.repositories.PagamentoRepository;
import com.ronaldosantos.repositories.PedidoRepository;
import com.ronaldosantos.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	PagamentoRepository pagamentoRepository;
	
	@Autowired
	ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
	    Cidade c2 = new Cidade(null, "São Paulo", est2);			
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
        Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
        cli1.getTelefones().addAll(Arrays.asList("32691799", "92795265"));
		
        Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
        Endereco e2 = new Endereco(null, "Rua Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
        cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
        
        clienteRepository.saveAll(Arrays.asList(cli1));
        enderecoRepository.saveAll(Arrays.asList(e1,e2));
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        Pedido ped1 = new Pedido(null, sdf.parse("23/07/2020 21:50"), cli1, e1);
        Pedido ped2 = new Pedido(null, sdf.parse("22/07/2020 10:50"), cli1, e2);
        
        Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
        ped1.setPagamento(pagto1);
        
        Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("25/07/2020 00:00"),null);
        ped2.setPagamento(pagto2);
        
       
        cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
        
        
        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
        
        
        ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2.000);
        ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.00);
        ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
        
        ped1.getItens().addAll(Arrays.asList(ip1, ip2));
        ped2.getItens().addAll(Arrays.asList(ip3));
        
        p1.getItens().addAll(Arrays.asList(ip1));
        p2.getItens().addAll(Arrays.asList(ip3));
        p3.getItens().addAll(Arrays.asList(ip2));
        
        
        itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
        
	}

}
