package interfacegrafica;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.entidades.Caixa;
import modelo.entidades.Movimentacao;
import view.util.Utilidades;

public class ExtratosMovimentacoesViewController implements Initializable {

    private List<Movimentacao> extrato;

    @FXML
    private TableView<Movimentacao> tableViewMovimentacoes;

    @FXML
    private TableColumn<Movimentacao, Integer> clmnId;

    @FXML
    private TableColumn<Movimentacao, Date> clmnData;

    @FXML
    private TableColumn<Movimentacao, Double> clmnValor;

    @FXML
    private TableColumn<Movimentacao, String> clmnTipo;

    @FXML
    private TableColumn<Movimentacao, String> clmnDescricao;

    @FXML
    private TableColumn<Movimentacao, Caixa> clmnCaixa;

    @FXML
    private TableColumn<Caixa, String> clmnNomeCaixa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializaElementos();
    }
    
    private void inicializaElementos() {
        
        clmnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmnData.setCellValueFactory(new PropertyValueFactory<>("data"));
        Utilidades.formatTableColumnDate(clmnData, "dd/MM/yyyy");
        clmnValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        Utilidades.formatTableColumnDouble(clmnValor, 2);
        clmnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        clmnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        //clmnCaixa.setCellValueFactory(new PropertyValueFactory<>("caixa"));
        clmnNomeCaixa.setCellValueFactory(new PropertyValueFactory<>("nomeCaixa"));
        
    }

    public void setExtrato(List<Movimentacao> extrato) {
        this.extrato = extrato;
    }
    
    public void atualizaTabela() {
        ObservableList<Movimentacao> obsExtratos = FXCollections.observableArrayList(extrato);
        tableViewMovimentacoes.setItems(obsExtratos);
    }

}
