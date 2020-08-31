package interfacegrafica;

import interfacegrafica.utilitarios.Restricao;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.entidades.Caixa;
import modelo.entidades.Movimentacao;
import modelo.entidades.TipoMovimentacao;
import modelo.excecoes.ValidacaoException;
import modelo.servicos.CaixaService;
import modelo.servicos.MovimentacaoService;
import view.util.Alertas;
import view.util.Utilidades;

public class CadastroMovimentacaoViewController implements Initializable {

    private Movimentacao entidade;
    private MovimentacaoService movimentacaoServico;
    private CaixaService caixaServico;

    @FXML
    private TextField txtId;
    
    @FXML
    private Label labelErroId;

    @FXML
    private DatePicker dpData;
    
    @FXML
    private Label labelErroData;

    @FXML
    private ComboBox<String> cbbxTipo;
    
    @FXML
    private Label labelErroTipo;

    @FXML
    private TextField txtValor;
    
    @FXML
    private Label labelErroValor;

    @FXML
    private ComboBox<Caixa> cbbxCaixa;
    
    @FXML
    private Label labelErroCaixa;

    @FXML
    private TextArea txtDescricao;
    
    @FXML
    private Label labelErroDescricao;

    @FXML
    private Button btSalvar;

    @FXML
    private Button btCancelar;
    
    private ObservableList<String> obsTipos;
    private ObservableList<Caixa> obsCaixas;

    public void setEntidade(Movimentacao entidade) {
        this.entidade = entidade;
    }

    public void setServico(MovimentacaoService movimentacaoServico, CaixaService caixaServico) {
        
        this.movimentacaoServico = movimentacaoServico;
        this.caixaServico = caixaServico;
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        inicializarElementos();
    
    }

    private void inicializarElementos() {
        
        String[] tipos = {"RECEITA", "DESPESA"};
        obsTipos = FXCollections.observableArrayList(tipos);
        cbbxTipo.getItems().addAll(obsTipos);
        
        Restricao.setTextFieldDouble(txtValor);
        
        caixaServico = new CaixaService();
        List<Caixa> caixas = caixaServico.listaCaixas();
        obsCaixas = FXCollections.observableArrayList(caixas);
        cbbxCaixa.getItems().addAll(obsCaixas);
        
    }
    
    @FXML
    private void acaoBtSalvar(ActionEvent evento) {
        
        try {
            entidade = obtemDadosCadastro();
            caixaServico.cadastraMovimentacao(entidade);
            Utilidades.palcoAtual(evento).close();
        } catch (ValidacaoException e) {
            configuraMensagensErros(e.obtemErros());        
        } catch (IllegalArgumentException e) {
            Alertas.mostraAlerta("Erro no cadastro", null, e.getMessage(), Alert.AlertType.WARNING);
        }
        
    }

    public void atualizaDadosCadastro() {

        if (entidade == null) {
            throw new IllegalStateException();
        }

        txtId.setText( entidade.getId() == null ? "" : entidade.getId().toString());
        
        dpData.setValue( entidade.getData() == null ? LocalDate.now(ZoneId.systemDefault()) 
                : LocalDateTime.ofInstant(entidade.getData().toInstant(), ZoneId.systemDefault()).toLocalDate());
        
        cbbxTipo.getSelectionModel().select(entidade.getTipo() == null ? "RECEITA" : entidade.getTipo().toString());
        
        txtValor.setText(entidade.getValor() == null ? String.format("%.2f", 0.00) : String.format("%.2f", entidade.getValor()));
        
        if (entidade.getCaixa() == null) {
            cbbxCaixa.getSelectionModel().selectFirst();
        } else {
            cbbxCaixa.getSelectionModel().select(entidade.getCaixa());
        }
        
        txtDescricao.setText(entidade.getDescricao());
    }
    
    private Movimentacao obtemDadosCadastro() {
        
        ValidacaoException erros = new ValidacaoException("Erros de validação no cadastro da movimentação");
        
        Date data = Date.valueOf(dpData.getValue());
        if (data == null)
            erros.adicionaErro("data", "(valor inválido)");
        
        String tipo = cbbxTipo.getSelectionModel().getSelectedItem();
        
        Double valor = null;
        if ( txtValor.getText().trim().equals("") || txtValor.getText().equals("0,0") )
            erros.adicionaErro("valor", "(valor inválido)");
        else
            valor = Double.parseDouble(txtValor.getText().replaceFirst(",", "."));
        
        Caixa caixa = cbbxCaixa.getValue();
        
        if (txtDescricao.getText() == null || txtDescricao.getText().trim().equals(""))
            erros.adicionaErro("descricao", "(valor inválido)");
        String descricao = txtDescricao.getText();
        
        if (erros.obtemErros().size() > 0)
            throw erros;
        
        return new Movimentacao(null, data, valor, TipoMovimentacao.valueOf(tipo), descricao, caixa);
    }
    
    @FXML
    private void acaoBtCancelar(ActionEvent evento) {
        Utilidades.palcoAtual(evento).close();
    }
    
    private void configuraMensagensErros(Map<String, String> erros) {
        
        Set<String> campos = erros.keySet();
        
        labelErroData.setText(campos.contains("data") ? erros.get("data") : "");
        labelErroValor.setText(campos.contains("valor") ? erros.get("valor") : "");
        labelErroDescricao.setText(campos.contains("descricao") ? erros.get("descricao") : "");

    }

}
