

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

function updateMap() {
	$.ajax({
		url: 'ocorrencias/ocorrencias-recentes-json.xhtml',
		type: "GET",
	    dataType: "json",
	    success: function (data) {
	    	ocorrencias = data;
	    	//remove todos os markers
            for(var i in markers) {
            	var m = markers[i];
            	m.setMap(null);
            	markers.splice(i, 1)
            }
	        $.each(data, function (i) {
	            var o = data[i]; //Object ocorrencia
	            
	            var marker = new google.maps.Marker({
	            	position: new google.maps.LatLng(o.latitude, o.longitude)
	            });
	            marker.setMap(gmap);
	            markers.push(marker);
	            var infowindow = new google.maps.InfoWindow({
	                content: "" +
	                "<h1>" + o.id + "</h1>" +
	                "<h3> Data e Hora: " + o.data + "</h3>" +
	                "<h5> Latitude:" + o.latitude + "</h5>" +
	                "<h5> Longitude:" + o.longitude + "</h5>" +
	                "<a href='" + "ocorrencia.xhtml?ocorrencia=" + o.id + "'> Tratar Ocorrência </a>"
	                
	            });
	            google.maps.event.addListener(marker, 'click', function() {
	                gmap.setCenter(marker.getPosition());
	                infowindow.open(gmap, marker);
	            });
	        });
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