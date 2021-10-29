package dao;

import java.util.List;


public interface ICRUD<T> {
    public void registrar(T modelo) throws Exception;
    public void editar(T modelo) throws Exception;
    public void cambiarEstado(T modelo) throws Exception;
    public List<T> listar() throws Exception;
    public boolean existe(T modelo, List<T> listaModelo);
    
}
