package interfacegrafica;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAccessor;
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
import modelo.entidades.Caixa;
import modelo.entidades.Movimentacao;
import modelo.servicos.CaixaService;
import modelo.servicos.MovimentacaoService;

public class MenuExtratosViewController implements Initializable {

    private MovimentacaoService movimentacaoServico;
    private CaixaService caixaServico;
    private List<Date> meses;
    private ObservableList<String> obsMeses;
    private ObservableList<String> obsTipos;
    private ObservableList<Caixa> obsCaixas;
    
    
    @FXML
    private DatePicker dpInicio;
    
    @FXML
    private DatePicker dpFim;
    
    @FXML
    private ComboBox<String> cbboxTipo;
    
    @FXML
    private ComboBox<Caixa> cbboxCaixa;
    
    @FXML
    private Button btCancelar;
    
    @FXML
    private Button btGerar;
    
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
               
        LocalDate referencia = LocalDate.now();
        LocalDate inicio = referencia.withDayOfMonth(1);
        LocalDate fim = referencia.withDayOfMonth(referencia.lengthOfMonth());
        
        dpInicio.setValue(inicio);
        dpFim.setValue(fim);
            
        String[] tipos = {"TODAS", "RECEITA", "DESPESA"};
        obsTipos = FXCollections.observableArrayList(tipos);
        cbboxTipo.getItems().addAll(obsTipos);
        cbboxTipo.getSelectionModel().selectFirst();
        
        caixaServico = new CaixaService();
        List<Caixa> caixas = caixaServico.listaCaixas();
        caixas.add(0, new Caixa(0, "Todos", null));
        obsCaixas = FXCollections.observableArrayList(caixas);
        cbboxCaixa.getItems().addAll(obsCaixas);
        cbboxCaixa.getSelectionModel().selectFirst();
        
    }
        
    @FXML
    private void acaoBtGerar(ActionEvent evento) {
        List<Movimentacao> extratoMovimentacoes = geraExtrato();
        //extratoMovimentacoes.forEach(x -> {System.out.println(x);});
    }
    
    private List<Movimentacao> geraExtrato() {
        java.sql.Date inicio = java.sql.Date.valueOf(dpInicio.getValue());
        java.sql.Date fim = java.sql.Date.valueOf(dpFim.getValue());
        
        if (cbboxTipo.getValue().equals("TODAS") && cbboxCaixa.getValue().getId() == 0)
            return movimentacaoServico.listaMovimentacoesPorPeriodo(inicio, fim);
        if (cbboxTipo.getValue().equals("TODAS") && cbboxCaixa.getValue().getId() != 0)
            return movimentacaoServico.listaMovimentacoesPorPeriodo(inicio, fim, cbboxCaixa.getValue().getId());
        if (!cbboxTipo.getValue().equals("TODAS") && cbboxCaixa.getValue().getId() == 0)
            return movimentacaoServico.listaMovimentacoesPorPeriodo(inicio, fim, cbboxTipo.getValue());
        return movimentacaoServico.listaMovimentacoesPorPeriodo(inicio, fim, cbboxCaixa.getValue().getId(), cbboxTipo.getValue());
        
    }
    
}
