var notification;

function initialize() {
	notification = document.getElementsByTagName('audio')[0];
	if(window.location.href.indexOf("ocorrencia.xhtml") != -1)  return;
	var myCenter=new google.maps.LatLng(-8.057838,-34.882897);
	var mapProp = {
		center:myCenter,
		zoom: 10,
		minZoom: 9,
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
	        	var confiabilidade = o.c;
	        	var m = findOcorrenciaById(o.id);
	        	
	        	if(m) {
	        		
	        	}else {
	        		notification.play();
		            var m = new google.maps.Marker({
		            	position: new google.maps.LatLng(o.la, o.lo),
		            	icon: "/SozoWeb/javax.faces.resource/images/marker-icon-1.png.xhtml"
		            });
		            
		            m.time = new Date().getTime();
		            m.setMap(gmap);
		             
		            m.ocorrencia = o;
		            var o = m.ocorrencia;
		        	var infowindow = new google.maps.InfoWindow({
		                content: "" +
		                "<h1>" + o.id + "</h1>" +
		                "<h5> Latitude:" + o.la + "</h5>" +
		                "<h5> Longitude:" + o.lo + "</h5>" +
		                "<a href='" + "ocorrencia.xhtml?ocorrencia=" + o.id + "'> Tratar Ocorrência </a>"
		                
		            });
		        	google.maps.event.addListener(m,'click', (function(m, infowindow){ 
		                return function() {
		                   infowindow.open(gmap,m);
		                };
		            })(m,infowindow));
		        	
		        	m.row = Ocorrencias.insertRow(o, (function(o) {
		        		return function() {
			        		gmap.setCenter(new google.maps.LatLng(o.la, o.lo));
		        			gmap.setZoom(15);
		        		}
		        	})(o));
		        	markers.push(m);   
	        	}
	        	var timeDiff = time - dataToSeconds(o.d);
        		var icon = Math.ceil(timeDiff / 10);
        		if(confiabilidade > 0) {
        			icon + 5;
        		}
        		
        		icon = (icon > 10) ? 10 : icon; 
        		icon = (icon < 1) ? 1 : icon; 
        		if(icon > 5) m.setAnimation(google.maps.Animation.BOUNCE);
        		m.setIcon("/SozoWeb/javax.faces.resource/images/marker-icon-" + icon + ".png.xhtml");
        		Ocorrencias.rowColor(m.row, icon);
	        }
	        
	        
	        viaturas = data.viaturas;
	        for(var i in viaturas) {
	        	var v = viaturas[i];
	        	var mv = findViaturaById(v.id);
	        	if(mv) {
	        		
	        		//mv.setPosition(new google.maps.LatLng(v.latitude, v.longitude));
	        		
	        		(function(mv, v) {
	        			var before = mv.viatura;
	        			if(before.la == v.la && before.lo == v.lo) return;
	        			//console.log(before.latitude);
		        		var difX = v.la - before.la;
		        		var difY = v.lo - before.lo;
		        		//console.log(difX / 10, difY / 10);
		        		var count = 0;
		        		
		        		var interval = setInterval(function() {
		        			//console.log("dif", v.latitude - difX / 10, v.longitude - difY / 10);
		        			mv.setPosition(new google.maps.LatLng(before.la + ((difX / 20) * count), before.lo + ((difY / 20) * count)));
		        			count++;
		        			if(count >= 21) {
		        				window.clearInterval(interval);
		        				console.log(mv.viatura);
		        				mv.viatura = v;
		        			}
			        	},100);
	        		})(mv, v);
	        		
	        		
	        	}else {
		            var m = new google.maps.Marker({
		            	position: new google.maps.LatLng(v.la, v.lo),
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
		    				break;
		    			}
		    			
		    		}
		    		if(tem == false) {
		    			markers[i].setMap(null);
		    			Ocorrencias.removeRow(i);
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
	}, 3000);
		
		//Ao Clicar em uma ocorrencia da tabela com as ocorrencias pendentes o local da ocorrência passa a ser o centro do map
		$(".ui-datatable-selectable").on("click", function() {
			var i = $(this).index();
			var o = ocorrencias[i];
			gmap.setCenter(new google.maps.LatLng(o.la, o.lo));
		});


}
google.maps.event.addDomListener(window, 'load', initialize);

var Ocorrencias = (function() {
	var tableOcorrencias;
	var colors = ["#ffde3d", "#ffbd3c", "#ff9d3c","#ff7d3c", "#ff5e3d", "#ff3c3c", "#ff1d1d", "#fc0000", "#dd0000", "#bd0000"];
	var module = {};
	module.init = function() {
		tableOcorrencias = $(".ocorrencias");
	}
	
	module.insertRow = function(ocorrencia, clickCallback) {
		var element = $('<tr data-ri="0" data-rk="32" class="ui-widget-content ui-datatable-even ui-datatable-selectable ocorrencia" role="row" aria-selected="false">' +
							'<td role="gridcell">' + ocorrencia.id + '</td>' +
							'<td role="gridcell">' + ocorrencia.la + '</td>' +
							'<td role="gridcell">' + ocorrencia.lo + '</td>' +
						'</tr>');
		tableOcorrencias.append(element);
		clickCallback && element.click(function() {
			$(".ocorrencia").each(function(i) {
				$(this).css({border: '1px solid #dddddd'})
			});
			element.css({'box-sizing': 'border-box',border: '3px solid green'})
			clickCallback();
		});
		return element;
	}
	
	module.removeRow = function(i) {
		$(".ocorrencia").eq(i).fadeOut(300, function() {
			$(this).remove();
		});
	}
	
	module.rowColor = function(row, level) {
		row.animate({
			backgroundColor: colors[level-1],
			color: (level > 4) ? "white" : "black"
		}, 'slow');
	}
	
	return module;
})();

$(function() {
	Ocorrencias.init();
});
