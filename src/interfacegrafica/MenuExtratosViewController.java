package interfacegrafica;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.entidades.Caixa;
import modelo.entidades.Movimentacao;
import modelo.entidades.utilitarios.Utilitaria;
import modelo.servicos.CaixaService;
import modelo.servicos.MovimentacaoService;
import view.util.Alertas;
import view.util.Utilidades;

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
        if (extratoMovimentacoes.size() != 0) {
            System.out.println(extratoMovimentacoes);
            carregaTelaExtratos(extratoMovimentacoes);
            Utilidades.palcoAtual(evento).close();
        } else {
            Alertas.mostraAlerta("Informação", null, "Não há movimentações conforme os filtros (datas, tipo, caixa) especificados", Alert.AlertType.INFORMATION);
        }
    }
    
    @FXML
    private void acaoBtCancelar(ActionEvent evento) {
        Utilidades.palcoAtual(evento).close();
    }

    private List<Movimentacao> geraExtrato() {
        java.sql.Date inicio = java.sql.Date.valueOf(dpInicio.getValue());
        java.sql.Date fim = java.sql.Date.valueOf(dpFim.getValue());

        if (cbboxTipo.getValue().equals("TODAS") && cbboxCaixa.getValue().getId() == 0) {
            return movimentacaoServico.listaMovimentacoesPorPeriodo(inicio, fim);
        }
        if (cbboxTipo.getValue().equals("TODAS") && cbboxCaixa.getValue().getId() != 0) {
            return movimentacaoServico.listaMovimentacoesPorPeriodo(inicio, fim, cbboxCaixa.getValue().getId());
        }
        if (!cbboxTipo.getValue().equals("TODAS") && cbboxCaixa.getValue().getId() == 0) {
            return movimentacaoServico.listaMovimentacoesPorPeriodo(inicio, fim, cbboxTipo.getValue());
        }
        return movimentacaoServico.listaMovimentacoesPorPeriodo(inicio, fim, cbboxCaixa.getValue().getId(), cbboxTipo.getValue());

    }

    private void carregaTelaExtratos(List<Movimentacao> extrato) {

        try {
            FXMLLoader carregador = new FXMLLoader(getClass().getResource("/interfacegrafica/ExtratosMovimentacoesView.fxml"));
            AnchorPane painel = carregador.load();

            ExtratosMovimentacoesViewController controlador = carregador.getController();
            controlador.setExtrato(extrato);
            controlador.atualizaTabela();

            Stage novoPalco = new Stage();
            novoPalco.setTitle("GFP – Extratos");
            novoPalco.setScene(new Scene(painel));
            novoPalco.setResizable(false);
            novoPalco.initModality(Modality.WINDOW_MODAL);
            novoPalco.showAndWait();

        } catch (IOException ex) {
            Alertas.mostraAlerta("Erro", null, ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

}
