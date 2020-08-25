package interfacegrafica;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.entidades.Caixa;
import modelo.entidades.Movimentacao;
import modelo.servicos.CaixaService;
import modelo.servicos.MovimentacaoService;
import view.util.Alertas;
import view.util.Utilidades;

public class PrincipalViewController implements Initializable {

    private Stage palcoAtual;
    
    private MovimentacaoService movimentacaoServico;
    private CaixaService caixaServico;
    
    private ObservableList<Movimentacao> listaObs;
    
    @FXML
    private MenuItem menuItemMovimentacoesCadastrar;
    
    @FXML
    private MenuItem menuItemMovimentacoesListar;
    
    @FXML
    private MenuItem menuItemCaixasListar;
    
    @FXML
    private TableView<Movimentacao> tableViewMovimentacoes;
    
    @FXML
    private TableColumn<Movimentacao, Date> clmnData;
    
    @FXML
    private TableColumn<Movimentacao, Double> clmnValor;
    
    @FXML
    private TableColumn<Movimentacao, String> clmnTipo;
    
    @FXML
    private TableColumn<Movimentacao, String> clmnDescricao;
    
    @FXML
    private TableColumn<Movimentacao, Integer> clmnId;
    
    @FXML
    private Label labelCarteira;
    
    @FXML
    private Label labelBanco;
    
    @FXML
    private Label labelTotal;
    
    @FXML
    private Button btAtualizar;

    public PrincipalViewController() {
    }

    public void setMovimentacaoServico(MovimentacaoService movimentacaoServico) {
        this.movimentacaoServico = movimentacaoServico;
    }
    
    public void setCaixaServico(CaixaService caixaServico) {
        this.caixaServico = caixaServico;
    }

    public Stage getPalcoAtual() {
        return palcoAtual;
    }

    public void setPalcoAtual(Stage palcoAtual) {
        this.palcoAtual = palcoAtual;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializaElementos();
    }

    private void inicializaElementos() {
        clmnData.setCellValueFactory(new PropertyValueFactory<>("data"));
        Utilidades.formatTableColumnDate(clmnData, "dd/MM/yyyy");
        clmnValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        Utilidades.formatTableColumnDouble(clmnValor, 2);
        clmnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        clmnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        clmnId.setCellValueFactory(new PropertyValueFactory<>("id"));   
    }
    
    public void atualizaTabela() {
        
        List<Movimentacao> lista = movimentacaoServico.listaMovimentacoes();
        listaObs = FXCollections.observableArrayList(lista);
        tableViewMovimentacoes.setItems(listaObs);
        
        List<Caixa> listaCaixa = caixaServico.listaCaixas();
        labelCarteira.setText("R$ " + String.format("%.2f", listaCaixa.get(0).getSaldoCaixa()));
        labelBanco.setText("R$ " + String.format("%.2f", listaCaixa.get(1).getSaldoCaixa()));
        labelTotal.setText("R$ " + String.format("%.2f", listaCaixa.get(0).getSaldoCaixa() + listaCaixa.get(1).getSaldoCaixa()));
    }
    
    @FXML
    private void acaoBtCadastrarMovimentacao(ActionEvent evento) {
        carregaTelaCadastroMovimentacao(new Movimentacao());
    }
    
    
    private void carregaTelaCadastroMovimentacao(Movimentacao obj) {
        
        try {
            FXMLLoader carregador = new FXMLLoader(getClass().getResource("/interfacegrafica/CadastroMovimentacaoView.fxml"));
            AnchorPane painel = carregador.load();
            
            CadastroMovimentacaoViewController controlador = carregador.getController();
            controlador.setEntidade(obj);
            controlador.setServico(new MovimentacaoService(), new CaixaService());
            controlador.atualizaDadosCadastro();
            
            Stage novoPalco = new Stage();
            novoPalco.setTitle("GFP – Cadastro de Movimentação");
            novoPalco.setScene(new Scene(painel));
            novoPalco.setResizable(false);
            novoPalco.initOwner(palcoAtual);
            novoPalco.initModality(Modality.WINDOW_MODAL);
            novoPalco.showAndWait();
            
        } catch (IOException ex) {
            Alertas.mostraAlerta("Erro", null, ex.getMessage(), Alert.AlertType.ERROR);
        } 
    }
    @FXML
    private void acaoBtAtualizar() {
        atualizaTabela();
    }
    
    
}
