package controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import model.AuxGrafico;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import dao.EntityManagerHelper;
import dao.OcorrenciaDAO;



@ManagedBean(name = "grafico")

public class GraficoController {

    //private StreamedContent chart;
    private OcorrenciaDAO dao;
    private List<AuxGrafico> listaPorDiaFunc;
    private List<AuxGrafico> listaPorDiaStatus;
    private List<AuxGrafico> listaPorCidade;
    private BarChartModel grafico1;
    private BarChartModel grafico3;
    private BarChartModel grafico4;
    private int qtd1;
    private int qtd3;
    private int qtd4;
    
    
    	
       public GraficoController() {
        	super();
    		EntityManagerHelper emh = new EntityManagerHelper();
    		dao = new OcorrenciaDAO(emh.getEntityManager());
    		listaPorDiaFunc = dao.consultaOcorrenciaDiaFunc();
    		listaPorDiaStatus = dao.consultaOcorrenciaDiaStatus();
    		listaPorCidade = dao.consultaOcorrenciaCidade();
    		
    		
        }
        @PostConstruct
        public void init(){
        	createGraficos();
        	
        }       
        
		public List<AuxGrafico> getListaPorDia() {
			return listaPorDiaFunc;
		}

		public void setListaPorDia(List<AuxGrafico> listaPorDia) {
			this.listaPorDiaFunc = listaPorDia;
		}
	     
	    private void createGraficos() {
	         criarGrafico1();
	         criarGrafico3();
	         criarGrafico4();
	    }
	 
	    /*private void criarGrafico1() {
	    	grafico1 = new PieChartModel();
	    	SimpleDateFormat formatador = new SimpleDateFormat(
	 				"dd/MM/yyyy");
	    	 String date = formatador.format(new Date());
	    	 	grafico1.setTitle("Ocorrências - Funcionário - " + date);
		        grafico1.setLegendPosition("o");
		        grafico1.setShowDataLabels(true);
	    	
		        if(listaPorDiaFunc.size()<=0){
	    		
		        	grafico1.set("Não existe ocorrências", 0);
		        }
		        else{       
	      
		        	for(AuxGrafico auxG : listaPorDiaFunc){
            	
            	grafico1.set(auxG.getNome()+" - " + auxG.getQuantidade(), Double.parseDouble(auxG.getQuantidade()));
		        	}    
	         
	    	}
	        
	    }*/
	    
	    private BarChartModel comecarGrafico1() {
	        BarChartModel model = new BarChartModel();
	    /*    SimpleDateFormat formatador = new SimpleDateFormat(
	 				"dd/MM/yyyy");*/
	        ChartSeries ocorrencia = new ChartSeries();
	        ocorrencia.setLabel("Ocorrências");
	        if(listaPorDiaFunc.size()<=0){
	        	ocorrencia.set("Não existe ocorrêncas finalizadas até o momento.", 0);
	        	 
	        	 model.addSeries(ocorrencia);
	        }
	        else{
	        for(AuxGrafico func :listaPorDiaFunc){
	        	qtd1 += Integer.parseInt(func.getQuantidade());
	        	ocorrencia.set(func.getNome()+" - " + Double.parseDouble(func.getQuantidade()) ,Double.parseDouble(func.getQuantidade()));
	        	System.out.println(qtd1);
	        	}
	        
	        }
	        model.addSeries(ocorrencia);
	       
	           
	        return model;
	    }
	    
	    private BarChartModel comecarGrafico3() {
	        BarChartModel model = new BarChartModel();
	        /*SimpleDateFormat formatador = new SimpleDateFormat(
	 				"dd/MM/yyyy");*/
	        ChartSeries ocorrencia = new ChartSeries();
	        ocorrencia.setLabel("Ocorrências");
	       
	        for(AuxGrafico status :listaPorDiaStatus){
	        	qtd3 += Integer.parseInt(status.getQuantidade());
	        	ocorrencia.set(status.getNome(),Double.parseDouble(status.getQuantidade()));
	        }
	        System.out.println(qtd3);
	        model.addSeries(ocorrencia);
	           
	        return model;
	    }
	    
	    private BarChartModel comecarGrafico4() {
	        BarChartModel model = new BarChartModel();
	       /* SimpleDateFormat formatador = new SimpleDateFormat(
	 				"dd/MM/yyyy");*/
	    	 
	         
	        ChartSeries ocorrencia = new ChartSeries();
	        ocorrencia.setLabel("Ocorrências");
	        for(AuxGrafico cidade :listaPorCidade){
	        	qtd4 += Integer.parseInt(cidade.getQtdCidade());
	        	ocorrencia.set(cidade.getCidade(),Double.parseDouble(cidade.getQtdCidade()));
	        	
	        }
	 
	        model.addSeries(ocorrencia);
	           
	        return model;
	    }
	    
	    
	    private void criarGrafico1() {
	    	
			SimpleDateFormat formatador = new SimpleDateFormat(
 				"dd/MM/yyyy");
    	 	String date = formatador.format(new Date());
	        grafico1 = comecarGrafico1();
	         
	        grafico1.setTitle("Gráfico - Ocorrência Finalizadas - Funcionário - " + date);
	        grafico1.setLegendPosition("ne");
	        grafico1.setAnimate(true);
	        Axis xAxis = grafico1.getAxis(AxisType.X);
	        xAxis.setLabel("Funcionários");
	         
	        Axis yAxis = grafico1.getAxis(AxisType.Y);
	        yAxis.setLabel("Ocorrêcias");
	        yAxis.setMin(0);
	        yAxis.setMax(qtd1);
	    }
	    
	    private void criarGrafico3() {
	    	
	        grafico3 = comecarGrafico3();
	         
	        grafico3.setTitle("Gráfico - Ocorrência 7 dias");
	        grafico3.setLegendPosition("ne");
	        grafico3.setAnimate(true);
	        Axis xAxis = grafico3.getAxis(AxisType.X);
	        xAxis.setLabel("Dias");
	         
	        Axis yAxis = grafico3.getAxis(AxisType.Y);
	        yAxis.setLabel("Ocorrêcias");
	        yAxis.setMin(0);
	        yAxis.setMax(qtd3);
	    }

	    	private void criarGrafico4() {
	    	
	        grafico4 = comecarGrafico4();
	         
	        grafico4.setTitle("Gráfico - Ocorrência 7 dias por Cidade");
	        grafico4.setLegendPosition("ne");
	        grafico4.setAnimate(true); 
	        Axis xAxis = grafico4.getAxis(AxisType.X);
	        xAxis.setLabel("Cidade");
	         
	        Axis yAxis = grafico4.getAxis(AxisType.Y);
	        yAxis.setLabel("Ocorrêcias");
	        yAxis.setMin(0);
	        yAxis.setMax(qtd4);
	    }
			public List<AuxGrafico> getListaPorDiaFunc() {
				return listaPorDiaFunc;
			}
			public void setListaPorDiaFunc(List<AuxGrafico> listaPorDiaFunc) {
				this.listaPorDiaFunc = listaPorDiaFunc;
			}
			public List<AuxGrafico> getListaPorDiaStatus() {
				return listaPorDiaStatus;
			}
			public void setListaPorDiaStatus(List<AuxGrafico> listaPorDiaStatus) {
				this.listaPorDiaStatus = listaPorDiaStatus;
			}
			public List<AuxGrafico> getListaPorCidade() {
				return listaPorCidade;
			}
			public void setListaPorCidade(List<AuxGrafico> listaPorCidade) {
				this.listaPorCidade = listaPorCidade;
			}
		
			public BarChartModel getGrafico1() {
				return grafico1;
			}
			public void setGrafico1(BarChartModel grafico1) {
				this.grafico1 = grafico1;
			}
			public BarChartModel getGrafico3() {
				return grafico3;
			}
			public void setGrafico3(BarChartModel grafico3) {
				this.grafico3 = grafico3;
			}
			public BarChartModel getGrafico4() {
				return grafico4;
			}
			public void setGrafico4(BarChartModel grafico4) {
				this.grafico4 = grafico4;
			}
			public int getQtd1() {
				return qtd1;
			}
			public void setQtd1(int qtd1) {
				this.qtd1 = qtd1;
			}
			public int getQtd3() {
				return qtd3;
			}
			public void setQtd3(int qtd3) {
				this.qtd3 = qtd3;
			}
			public int getQtd4() {
				return qtd4;
			}
			public void setQtd4(int qtd4) {
				this.qtd4 = qtd4;
			}
			
			
}
