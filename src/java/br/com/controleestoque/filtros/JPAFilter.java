/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controleestoque.filtros;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(servletNames = "Faces Servlet")
public class JPAFilter implements Filter {

    //Classe que descobre quem cria as conexões
    private EntityManagerFactory factory;

    //Método executado quando o filtro é carregado pelo container
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Cria uma fábrica de conexão
        this.factory = Persistence.createEntityManagerFactory("ControleEstoquePU");
    }

    //Método executado a cada requisição
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //Abre a conexão
        EntityManager manager = this.factory.createEntityManager();
        //Cria um atributo para ser utilizado posteriormente
        request.setAttribute("EntityManager", manager);
        //Abre a transação
        manager.getTransaction().begin();
        //Executa o próximo filtro ou indica que é o último 
        //filtro para o container
        chain.doFilter(request, response);
        try {
            //Efetiva a transação
            manager.getTransaction().commit();
        } catch (Exception e) {
            //Desfaz a transação
            System.out.println("ERRO" + e.getMessage());
            manager.getTransaction().rollback();
        } finally {
            //Fecha a transação 
            manager.close();
        }

    }

    @Override
    public void destroy() {
        this.factory.close();
    }
}
