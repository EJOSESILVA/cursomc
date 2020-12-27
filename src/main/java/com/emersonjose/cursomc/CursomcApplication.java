package com.emersonjose.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.emersonjose.cursomc.domain.Categoria;
import com.emersonjose.cursomc.domain.Cidade;
import com.emersonjose.cursomc.domain.Cliente;
import com.emersonjose.cursomc.domain.Endereco;
import com.emersonjose.cursomc.domain.Estado;
import com.emersonjose.cursomc.domain.ItemPedido;
import com.emersonjose.cursomc.domain.Pagamento;
import com.emersonjose.cursomc.domain.PagamentoComBoleto;
import com.emersonjose.cursomc.domain.PagamentoComCartao;
import com.emersonjose.cursomc.domain.Pedido;
import com.emersonjose.cursomc.domain.Produto;
import com.emersonjose.cursomc.domain.enums.EstadoPagamento;
import com.emersonjose.cursomc.domain.enums.TipoCliente;
import com.emersonjose.cursomc.repositories.CategoriaRepository;
import com.emersonjose.cursomc.repositories.CidadeRepository;
import com.emersonjose.cursomc.repositories.ClienteRepository;
import com.emersonjose.cursomc.repositories.EnderecoRepository;
import com.emersonjose.cursomc.repositories.EstadoRepository;
import com.emersonjose.cursomc.repositories.ItemPedidoRepository;
import com.emersonjose.cursomc.repositories.PagamentoRepository;
import com.emersonjose.cursomc.repositories.PedidoRepository;
import com.emersonjose.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itempedidoRepository;
	
	
	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
	
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat1.getProdutos().add(p2);
		
		p1.getCategorias().add(cat1);
		p1.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p1.getCategorias().add(cat1);
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade c1 = new Cidade(null,"Uberlândia",est1);
		Cidade c2 = new Cidade(null,"São Paulo",est2);
		Cidade c3 = new Cidade(null,"Campinas",est2);
		
		est1.getCidades().add(c1);
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null,"Maria Cristina","cristina@gmail.com","21354896615", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("12324565","23135465"));
		
		Endereco end1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
		Endereco end2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.getEndereco().addAll(Arrays.asList(end1,end2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(end1,end2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, end2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1,p1,0.00,1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1,p3,0.00,2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2,p2,100.00,1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itempedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		
	}

}
