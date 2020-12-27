package com.emersonjose.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emersonjose.cursomc.domain.Pedido;
import com.emersonjose.cursomc.repositories.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;  
	
	
	public Pedido buscar(Integer id) {

		Optional<Pedido> categoria = repo.findById(id);

		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		
	}
	

}
