

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
var viaturas = [];
var markers = [];
var markersViatura = [];
var count = 0;
function findOcorrenciaById(id) {
	for(var i in markers) {
		var o = markers[i].ocorrencia;
		if(o.id == id) return markers[i];
	}
	return false;
}

function findViaturaById(id) {
	for(var i in markersViatura) {
		var v = markersViatura[i].viatura;
		if(v.id == id) return markersViatura[i];
	}
	return false;
}

function dataToSeconds(data) {
	var hour = data.split(" ")[1];
	var split = hour.split(":");
	var h = split[0];
	var m = split[1];
	var s = split[2];
	var seconds = 0;
	seconds += h * 3600;
	seconds += m * 60;
	seconds += +s;
	return seconds;
}

function updateMap() {
	$.ajax({
		url: 'http://localhost/SozoAPI/public/mapa',
		type: "GET",
	    dataType: "json",
	    success: function (data) {
	    	var time = dataToSeconds(data.time);
	    	ocorrencias = data.ocorrencias;
	        for(var i in ocorrencias) {
	        	var o = ocorrencias[i];
	        	var m = findOcorrenciaById(o.id);
	        	
	        	if(m) {
	        		var timeDiff = time - dataToSeconds(o.dataCriacao);
	        		console.log(timeDiff)
	        		var icon = Math.ceil(timeDiff / 10);
	        		icon = (icon > 10) ? 10 : icon; 
	        		icon = (icon < 1) ? 1 : icon; 
	        		if(icon > 5) m.setAnimation(google.maps.Animation.BOUNCE);
	        		m.setIcon("/SozoWeb/javax.faces.resource/images/marker-icon-" + icon + ".png.xhtml");
	        	}else {
		            var m = new google.maps.Marker({
		            	position: new google.maps.LatLng(o.latitude, o.longitude),
		            	icon: "/SozoWeb/javax.faces.resource/images/marker-icon-1.png.xhtml"
		            });
		            m.time = new Date().getTime();
		            m.setMap(gmap);
		            markers.push(m);    
		            m.ocorrencia = o;
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
	        }
	        
	        
	        viaturas = data.viaturas;
	        for(var i in viaturas) {
	        	var v = viaturas[i];
	        	var mv = findViaturaById(v.id);
	        	if(mv) {
	        		mv.setPosition(new google.maps.LatLng(v.latitude, v.longitude));
	        	}else {
		            var m = new google.maps.Marker({
		            	position: new google.maps.LatLng(v.latitude, v.longitude),
		            	icon: "/SozoWeb/javax.faces.resource/images/ambulance-icon.png.xhtml"
		            });
		            m.setMap(gmap);
		            m.viatura = v;
		            markersViatura.push(m);
	        	}
	        }
	        	
	        if(count != 0) {
		    	for(var i = 0; i < markers.length; i++) {
		    		var o = markers[i].ocorrencia;
		    		var tem = false
		    		for(var j = 0; j < ocorrencias.length; j++) {
		    			var oo = ocorrencias[j];
		    			if(o.id == oo.id) {
		    				tem = true;
		    			}
		    			
		    		}
		    		if(tem == false) {
		    			markers[i].setMap(null);
		    			markers.splice(i, 1);
		    		}
		    		
		    	}
		    	for(var i = 0; i < markersViatura.length; i++) {
		    		var v = markersViatura[i].viatura;
		    		var tem = false
		    		for(var j = 0; j < viaturas.length; j++) {
		    			var vv = viaturas[j];
		    			if(vv.id == v.id) {
		    				tem = true;
		    			}
		    			
		    		}
		    		if(tem == false) {
		    			markersViatura[i].setMap(null);
		    			markersViatura.splice(i, 1);
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
	}, 5000);
		
		//Ao Clicar em uma ocorrencia da tabela com as ocorrencias pendentes o local da ocorrência passa a ser o centro do map
		$(".ui-datatable-selectable").on("click", function() {
			var i = $(this).index();
			var o = ocorrencias[i];
			gmap.setCenter(new google.maps.LatLng(o.latitude, o.longitude));
		});


}
google.maps.event.addDomListener(window, 'load', initialize);