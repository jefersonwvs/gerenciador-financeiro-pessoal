package interfacegrafica;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import modelo.entidades.Movimentacao;
import modelo.servicos.CaixaService;
import modelo.servicos.MovimentacaoService;

public class RelatoriosViewController implements Initializable {

    private MovimentacaoService movimentacaoServico;
    private CaixaService caixaServico;
    private List<Date> meses;
    private ObservableList<String> obsMeses;
    private ObservableList<String> obsTipos;
    
    
    @FXML
    private DatePicker dpInicio;
    
    @FXML
    private DatePicker dpFim;
    
    @FXML
    private ComboBox<String> cbboxTipo;
    
    @FXML
    private Button btCancelar;
    
    @FXML
    private Button btImprimir;
    
    public void setServices(MovimentacaoService movimentacaoServico, CaixaService caixaServico) {
        this.movimentacaoServico = movimentacaoServico;
        this.caixaServico = caixaServico;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializaElementos();
    }  
    
    private void inicializaElementos() {
        movimentacaoServico = new MovimentacaoService();

        /*meses = movimentacaoServico.listaMesesMovimentacoes();
        obsMeses = FXCollections.observableArrayList(configuraComboBoxPeriodo());
        cbboxPeriodo.getItems().addAll(obsMeses);
        cbboxPeriodo.getSelectionModel().selectFirst();*/
        
        String[] tipos = {"TODAS", "RECEITA", "DESPESA"};
        obsTipos = FXCollections.observableArrayList(tipos);
        cbboxTipo.getItems().addAll(obsTipos);
        cbboxTipo.getSelectionModel().selectFirst();
    }
    
    /*private List<String> configuraComboBoxPeriodo() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        List<String> list = new ArrayList<>();
        for (Date date : meses) {
            String mesAno = sdf.format(date);
            if (!list.contains(mesAno)) {
                list.add(mesAno);
            }
        }
        return list;
    }*/
    
    @FXML
    private void acaoBtImprimir(ActionEvent evento) {
        java.sql.Date inicio = java.sql.Date.valueOf(dpInicio.getValue());
        java.sql.Date fim = java.sql.Date.valueOf(dpFim.getValue());   
        List<Movimentacao> list = movimentacaoServico.listaMovimentacoesPorPeriodo(inicio, fim);
        list.forEach(x -> {System.out.println(x);});
    }
    
}
