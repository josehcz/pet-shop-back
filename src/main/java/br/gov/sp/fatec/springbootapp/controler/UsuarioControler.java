package br.gov.sp.fatec.springbootapp.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.springbootapp.entity.Autorizacao;
import br.gov.sp.fatec.springbootapp.entity.Usuario;
import br.gov.sp.fatec.springbootapp.service.SegurancaService;

@RestController
@RequestMapping(value= "/usuario")
@CrossOrigin
public class UsuarioControler {

    @Autowired
    private SegurancaService segurancaService;

    @JsonView(View.UsuarioResumo.class)
    @GetMapping()
    public List<Usuario> buscarTodos(){
        return segurancaService.buscarTodosUsuarios();
    }

    @JsonView(View.UsuarioCompleto.class)
    @GetMapping(value = "/{id}")
    public Usuario buscarPorId(@PathVariable("id") Long id){
        return segurancaService.buscarUsuarioId(id);
    }   

    @JsonView(View.UsuarioResumo.class)
    @GetMapping(value = "/nome/{nome}")
    public Usuario buscarUsuarioPorNome(@PathVariable("nome") String nome){
        return segurancaService.buscarUsuarioPorNome(nome);
    }
    
    @JsonView(View.UsuarioResumo.class)
    @PostMapping
    public ResponseEntity<Usuario> cadastrarNovoUsuario(@RequestBody Usuario usuario,
            UriComponentsBuilder uriComponentsBuilder){
        usuario = segurancaService.criarUsuario(usuario.getNome(), usuario.getSenha(), "ROLE_USUARIO");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(
            uriComponentsBuilder.path(
                  "/usuario/" + usuario.getId()).build().toUri());
        return new ResponseEntity<Usuario>(usuario, responseHeaders, HttpStatus.CREATED);
    }

    @JsonView(View.AutorizacaoResumo.class)
    @GetMapping(value = "/autorizacao/{autorizacao}")
    public Autorizacao buscarAutorizacaoPorNome(@PathVariable("autorizacao") String nome){
        return segurancaService.buscarAutorizacaoPorNome(nome);
    }

}
