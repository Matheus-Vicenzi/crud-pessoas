package com.exemple.service.pessoa;

import com.example.exception.BusinessException;
import com.example.model.endereco.Endereco;
import com.example.model.pessoa.Pessoa;
import com.example.repository.pessoa.PessoaRepository;
import com.example.service.pessoa.PessoaService;
import com.example.util.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSalvarComDataNascimentoFutura() {
        Pessoa pessoa = new Pessoa();
        pessoa.setDataNascimento(DateUtils.localDateToDate(LocalDate.now().plusDays(1))); // Data no futuro

        assertThrows(BusinessException.class, () -> pessoaService.salvar(pessoa));
    }

    @Test
    public void testSalvarComDataNascimentoValida() {
        Pessoa pessoa = new Pessoa();
        pessoa.setDataNascimento(DateUtils.localDateToDate(LocalDate.now().minusYears(20)));

        pessoaService.salvar(pessoa);

        verify(pessoaRepository, times(1)).salvar(pessoa);
    }

    @Test
    public void testRemover() {
        Long id = 1L;
        pessoaService.remover(id);

        verify(pessoaRepository, times(1)).remover(id);
    }

    @Test
    public void testBuscarPorId() {
        Long id = 1L;
        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);
        when(pessoaRepository.buscarPorId(id)).thenReturn(pessoa);

        Pessoa resultado = pessoaService.buscarPorId(id);
        assertEquals(pessoa, resultado);
    }

    @Test
    public void testListar() {
        List<Pessoa> pessoas = Arrays.asList(new Pessoa(), new Pessoa());
        when(pessoaRepository.listar()).thenReturn(pessoas);

        List<Pessoa> resultado = pessoaService.listar();
        assertEquals(pessoas.size(), resultado.size());
    }

    @Test
    public void testAdicionarEndereco() {
        Pessoa pessoa = new Pessoa();
        Endereco endereco = new Endereco();

        pessoaService.adicionarEndereco(pessoa, endereco);

        assertTrue(pessoa.getEnderecos().contains(endereco));
        assertEquals(pessoa, endereco.getPessoa());
    }

    @Test
    public void testRemoverEndereco() {
        Pessoa pessoa = new Pessoa();
        Endereco endereco = new Endereco();
        pessoa.addEndereco(endereco);

        pessoaService.removerEndereco(pessoa, endereco);

        assertFalse(pessoa.getEnderecos().contains(endereco));
    }

    @Test
    public void testSalvarEBuscarPessoa() {
        List<Pessoa> listaDePessoas = new ArrayList<>();
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João");
        pessoa.setDataNascimento(DateUtils.localDateToDate(LocalDate.now().minusYears(25)));

        doAnswer(invocation -> {
            Pessoa p = invocation.getArgument(0);
            p.setId((long) (listaDePessoas.size() + 1));
            listaDePessoas.add(p);
            return null;
        }).when(pessoaRepository).salvar(any(Pessoa.class));

        pessoaService.salvar(pessoa);

        when(pessoaRepository.buscarPorId(anyLong())).thenAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            return listaDePessoas.stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        });

        Pessoa pessoaBuscada = pessoaService.buscarPorId(pessoa.getId());
        assertNotNull(pessoaBuscada);
        assertEquals("João", pessoaBuscada.getNome());
    }

    //ajustar
//    @Test
//    public void testRemoverPessoa() {
//        Pessoa pessoa = new Pessoa();
//        pessoa.setNome("João");
//        pessoaService.salvar(pessoa);
//
//        pessoaService.remover(pessoa.getId());
//        Pessoa pessoaRemovida = pessoaService.buscarPorId(pessoa.getId());
//        assertNull(pessoaRemovida);
//    }
}
