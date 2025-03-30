package com.example.controller;

import com.example.dto.endereco.EnderecoDTO;
import com.example.dto.pessoa.PessoaDTO;
import com.example.enums.pessoa.Sexo;
import com.example.model.endereco.Endereco;
import com.example.model.pessoa.Pessoa;
import com.example.service.pessoa.PessoaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Path("/pessoa")
public class PessoaController {

    @Inject
    private PessoaService pessoaService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarPessoa(PessoaDTO pessoaDTO) {
        try {
            Pessoa pessoa = dtoToPessoa(pessoaDTO);
            pessoaService.salvar(pessoa);
            return Response.status(Response.Status.CREATED).entity(pessoa).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPessoaPorId(@PathParam("id") Long id) {
        Pessoa pessoa = pessoaService.buscarPorId(id);
        if (pessoa == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(pessoa).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPessoas() {
        return Response.ok(pessoaService.listar()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarPessoa(@PathParam("id") Long id, PessoaDTO pessoaDTO) {
        Pessoa pessoaExistente = pessoaService.buscarPorId(id);
        if (pessoaExistente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        try {
            Pessoa updatedPessoa = dtoToPessoa(pessoaDTO);
            pessoaExistente.setNome(updatedPessoa.getNome());
            pessoaExistente.setDataNascimento(updatedPessoa.getDataNascimento());
            pessoaExistente.setSexo(updatedPessoa.getSexo());
            pessoaExistente.setIdade(updatedPessoa.getIdade());

            for (Endereco endereco : updatedPessoa.getEnderecos()) {
                pessoaExistente.addEndereco(endereco);
            }

            pessoaService.salvar(pessoaExistente);
            return Response.ok(pessoaExistente).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response removerPessoa(@PathParam("id") Long id) {
        Pessoa existingPessoa = pessoaService.buscarPorId(id);
        if (existingPessoa == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        pessoaService.remover(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    private Pessoa dtoToPessoa(PessoaDTO dto) throws Exception {
        if (dto.getNome() == null || dto.getNome().length() > 150) {
            throw new Exception("Nome deve ter no máximo 150 caracteres e não pode ser nulo.");
        }

        Date dataNascimento;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dataNascimento = sdf.parse(dto.getDataNascimento());
        } catch (ParseException e) {
            throw new Exception("Data de nascimento inválida. Formato esperado: yyyy-MM-dd");
        }

        Sexo sexo;
        try {
            sexo = Sexo.fromString(dto.getSexo().toUpperCase());
        } catch (Exception e) {
            throw new Exception("Sexo inválido. Valores permitidos: M ou F.");
        }

        Pessoa pessoa = new Pessoa(dto.getNome(), dataNascimento, sexo);

        if (dto.getEnderecos() != null) {
            for (EnderecoDTO enderecoDTO : dto.getEnderecos()) {
                Endereco endereco = dtoToEndereco(enderecoDTO);
                pessoa.addEndereco(endereco);
                endereco.setPessoa(pessoa);
            }
        }
        return pessoa;
    }

    private Endereco dtoToEndereco(EnderecoDTO dto) throws Exception {
        if (dto.getEstado() == null || dto.getEstado().length() != 2) {
            throw new Exception("Estado deve ter exatamente 2 caracteres.");
        }
        if (dto.getCidade() == null || dto.getCidade().length() > 100) {
            throw new Exception("Cidade deve ter no máximo 100 caracteres e não pode ser nula.");
        }
        if (dto.getLogradouro() == null || dto.getLogradouro().length() > 100) {
            throw new Exception("Logradouro deve ter no máximo 100 caracteres e não pode ser nulo.");
        }
        if (dto.getCep() == null || dto.getCep().isEmpty()) {
            throw new Exception("CEP não pode ser nulo ou vazio.");
        }

        Endereco endereco = new Endereco();
        endereco.setEstado(dto.getEstado());
        endereco.setCidade(dto.getCidade());
        endereco.setBairro(dto.getBairro());
        endereco.setLogradouro(dto.getLogradouro());
        endereco.setNumero(dto.getNumero());
        endereco.setComplemento(dto.getComplemento());
        endereco.setCep(dto.getCep());
        return endereco;
    }
}
