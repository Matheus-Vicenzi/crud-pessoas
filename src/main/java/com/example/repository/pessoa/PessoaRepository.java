package com.example.repository.pessoa;

import com.example.model.pessoa.Pessoa;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PessoaRepository {

    @PersistenceContext
    private EntityManager em;

    public void salvar(Pessoa pessoa) {
        if (pessoa.getId() == null) {
            em.persist(pessoa);
        } else {
            em.merge(pessoa);
        }
    }

    public void remover(Long id) {
        Pessoa pessoa = em.find(Pessoa.class, id);
        if (pessoa == null) throw new RuntimeException("Pessoa não encontrada");
        em.remove(pessoa);
    }

    public Pessoa buscarPorId(Long id) {
        return em.find(Pessoa.class, id);
    }

    public List<Pessoa> listar() {
        return em.createQuery("SELECT p FROM pessoa p", Pessoa.class).getResultList();
    }
}
