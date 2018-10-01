package br.com.controleestoque.model.persistencia.dao;

import java.io.Serializable;
import java.util.List;

public interface DAO<T, I extends Serializable> {

    void salvar(T entity);

    void remover(Class<T> classe, I pk);

    T buscarPorId(Class<T> classe, I pk);

    List<T> buscarTodos(Class<T> classe);
}

