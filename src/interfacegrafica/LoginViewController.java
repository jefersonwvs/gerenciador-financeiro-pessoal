package interfacegrafica;

import bd.BdException;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.dao.FabricaDao;
import modelo.dao.UsuarioDao;
import modelo.entidades.Usuario;
import modelo.excecoes.ValidacaoException;
import modelo.servicos.CaixaService;
import modelo.servicos.MovimentacaoService;
import view.util.Alertas;
import view.util.Utilidades;

public class LoginViewController implements Initializable {

    private Usuario usuario;

    @FXML
    private TextField txtUsuario;

    @FXML
    private Label labelErroUsuario;

    @FXML
    private PasswordField pwdSenha;

    @FXML
    private Label labelErroSenha;

    @FXML
    private Button btEntrar;

    @FXML
    private Button btCancelar;

    public LoginViewController() {
        this.usuario = new Usuario();
    }

    @FXML
    public void acaoBtEntrar(ActionEvent evento) {

        try {
            usuario = obtemDadosUsuario();
            if (validaLogin()) {
                carregaTelaPrincipal();
                Utilidades.palcoAtual(evento).close();
            } else {
                Alertas.mostraAlerta("Erro de autenticação", null, "Credenciais de acesso inválidas", Alert.AlertType.WARNING);
            }
        } catch (ValidacaoException e) {
            configuraMensagensErros(e.obtemErros());
        } catch (BdException e) {
            Alertas.mostraAlerta("Erro ao acessar o banco de dados", null, e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    @FXML
    public void acaoBtCancelar(ActionEvent evento) {
        
        Utilidades.palcoAtual(evento).close();
    
    }

    public Usuario obtemDadosUsuario() {

        Usuario obj = new Usuario();
        ValidacaoException erros = new ValidacaoException("Erros de validação");

        if (txtUsuario.getText() == null || txtUsuario.getText().trim().equals("")) {
            erros.adicionaErro("usuario", "(campo vazio)");
        }
        obj.setLogin(txtUsuario.getText());

        if (pwdSenha.getText() == null || pwdSenha.getText().equals("")) {
            erros.adicionaErro("senha", "(campo vazio)");
        }
        obj.setSenha(pwdSenha.getText());

        if (erros.obtemErros().size() > 0) {
            throw erros;
        }
        labelErroUsuario.setText("");
        labelErroSenha.setText("");

        return obj;
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private boolean validaLogin() {

        UsuarioDao dao = FabricaDao.criaUsuarioDao();
        Usuario usuarioBd = dao.buscaUsuario(usuario.getLogin());
        if (usuarioBd == null) {
            return false;
        }
        if (usuario.getLogin().equals(usuarioBd.getLogin()) && usuario.getSenha().equals(usuarioBd.getSenha())) {
            usuario.setId(usuarioBd.getId());
            usuario.setNome(usuarioBd.getNome());
            return true;
        }
        return false;
        
    }

    private void configuraMensagensErros(Map<String, String> erros) {
        
        Set<String> campos = erros.keySet();
        
        labelErroUsuario.setText(campos.contains("usuario") ? erros.get("usuario") : "");
        labelErroSenha.setText(campos.contains("senha") ? erros.get("senha") : "");

    }

    private void carregaTelaPrincipal() {
        
        try {
            FXMLLoader carregador = new FXMLLoader(getClass().getResource("/interfacegrafica/PrincipalView.fxml"));
            ScrollPane painel = carregador.load();
            Stage palco = new Stage();

            Scene cena = new Scene(painel);
            palco.setScene(cena);
            palco.setResizable(false);
            palco.setTitle("GFP");
            
            PrincipalViewController controlador = carregador.getController();
            controlador.setMovimentacaoServico(new MovimentacaoService());
            controlador.setCaixaServico(new CaixaService());
            controlador.setPalcoAtual(palco);
            
            controlador.atualizaTabela();
            
            palco.show();

        } catch (IOException e) {
            Alertas.mostraAlerta("IO Exception", "Erro ao carregar tela", e.getMessage(), Alert.AlertType.ERROR);
        }
        
    }
}
