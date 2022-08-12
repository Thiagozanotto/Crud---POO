package br.com.CrudPo.bean;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.jboss.logging.Messages;
import br.com.CrudPo.dao.EstadoDAO;
import br.com.CrudPo.domain.Estado;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean
public class EstadoBean implements Serializable {

	private Estado estado;
	private ArrayList<Estado> estados;

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public ArrayList<Estado> getEstados() {
		return estados;
	}

	public void setEstados(ArrayList<Estado> estados) {
		this.estados = estados;
	}

	public void novo() {
		estado = new Estado();
	}

	public void excluir(ActionEvent evento) {
		try {
			estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
			EstadoDAO estadoDAO = new EstadoDAO();
			estadoDAO.excluir(estado);
			
			estados = (ArrayList<Estado>)estadoDAO.listar(); // update na minha lista estado
			org.omnifaces.util.Messages.addGlobalInfo("Estado removido com sucesso");
			
		}catch(RuntimeException erro){
			org.omnifaces.util.Messages.addGlobalInfo("Ocorreu um erro ao tentar remover o estado");
			erro.printStackTrace();
			
		}
	}

	public void salvar(ActionEvent evento) {
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			estadoDAO.salvar(estado);

			estado = new Estado();
			estados = (ArrayList<Estado>) estadoDAO.listar();
			org.omnifaces.util.Messages.addGlobalInfo("Salvo com sucesso");

		} catch (RuntimeException erro) {
			org.omnifaces.util.Messages.addGlobalInfo("Ocorreu um erro ao tentar salvar o estado");
			erro.printStackTrace();

		}
	}
	
	public void editar(ActionEvent evento) {
		try {
			estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");

		} catch (RuntimeException erro) {
			org.omnifaces.util.Messages.addGlobalInfo("Ocorreu um erro ao tentar editar o estado");
			erro.printStackTrace();

		}
	}
	@PostConstruct
	public void listar() {
			try {
				EstadoDAO estadoDAO = new EstadoDAO();
				estados = (ArrayList<Estado>) estadoDAO.listar();
			}catch(RuntimeException erro) {
				org.omnifaces.util.Messages.addGlobalInfo("Ocorreu um erro ao listar o estado");
				erro.printStackTrace();
			}
		}
	
}