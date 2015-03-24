

function initialize() {
	if(window.location.href.indexOf("ocorrencia.xhtml") != -1)  return;
	var myCenter=new google.maps.LatLng(-8.522626,-37.062378);
	var mapProp = {
		center:myCenter,
		zoom: 7,
		minZoom: 7,
		mapTypeId:google.maps.MapTypeId.ROADMAP
	};
  
var gmap = new google.maps.Map(document.getElementById("map"),mapProp);

var ocorrencias = [];
var markers = [];
var count = 0;
function findOcorrenciaById(id) {
	for(var i in markers) {
		var o = markers[i].ocorrencia;
		if(o.id == id) return true;
	}
	return false;
}

function updateMap() {
	$.ajax({
		url: 'ocorrencias/ocorrencias-recentes-json.xhtml',
		type: "GET",
	    dataType: "json",
	    success: function (data) {
	    	
	        for(var i in data) {
	        	var o = data[i];
	        	if(findOcorrenciaById(o.id)) continue;
	            var m = new google.maps.Marker({
	            	position: new google.maps.LatLng(o.latitude, o.longitude)
	            });
	            m.setMap(gmap);
	            markers.push(m);
	            
	            
	            
	            
	            m.ocorrencia = data[i];
	        }
	        
	        for(var i in markers) {
	        	var m = markers[i];
	        	var o = m.ocorrencia;
	        	var infowindow = new google.maps.InfoWindow({
	                content: "" +
	                "<h1>" + o.id + "</h1>" +
	                "<h3> Data e Hora: " + o.data + "</h3>" +
	                "<h5> Latitude:" + o.latitude + "</h5>" +
	                "<h5> Longitude:" + o.longitude + "</h5>" +
	                "<a href='" + "ocorrencia.xhtml?ocorrencia=" + o.id + "'> Tratar Ocorrência </a>"
	                
	            });
	        	google.maps.event.addListener(m,'click', (function(m, infowindow){ 
	                return function() {
	                   infowindow.open(gmap,m);
	                };
	            })(m,infowindow)); 
	        }
	        ocorrencias = data;
	        console.log(data)
	        if(count != 0) {
		    	for(var i = 0; i < markers.length; i++) {
		    		var o = markers[i].ocorrencia;
		    		var tem = false
		    		for(var j = 0; j < data.length; j++) {
		    			var oo = data[j];
		    			if(o.id == oo.id) {
		    				tem = true;
		    				break;
		    			}
		    			
		    		}
		    		if(tem == false) {
		    			markers[i].setMap(null);
		    			markers.splice(i, 1);
		    			console.log("tentando remover", i);
		    		}
		    		
		    	}
	    	}
	        
	        count++;
	        
	    }
	});
}

	updateMap();
	
	setInterval(function() {
		updateMap();
	}, 10000);
		
		//Ao Clicar em uma ocorrencia da tabela com as ocorrencias pendentes o local da ocorrência passa a ser o centro do map
		$(".ui-datatable-selectable").on("click", function() {
			var i = $(this).index();
			var o = ocorrencias[i];
			gmap.setCenter(new google.maps.LatLng(o.latitude, o.longitude));
		});


}
google.maps.event.addDomListener(window, 'load', initialize);