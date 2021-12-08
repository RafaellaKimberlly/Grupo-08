var map;
var marker;

function initialize() {
var mapOptions = {
center: new google.maps.LatLng(40.680898,-8.684059),
zoom: 11,
mapTypeId: google.maps.MapTypeId.ROADMAP
};
var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
}
// google.maps.event.addDomListener(window, "load", initialize);


function searchAddress() {

    var addressInput = document.getElementById('address-input').value;
  
    var geocoder = new google.maps.Geocoder();
  
    geocoder.geocode({address: addressInput}, function(results, status) {
  
      if (status == google.maps.GeocoderStatus.OK) {
  
        var myResult = results[0].geometry.location;
        
        var teste1 = results[0].geometry.location;
        teste.innerHTML = myResult;// referência ao valor LatLng

  
      } else { // se o valor de status é diferente de "google.maps.GeocoderStatus.OK"

        // mensagem de erro
        alert("O Geocode não foi bem sucedido pela seguinte razão: " + status);
    
      }
    });
  }


  function createMarker(latlng) {

    // Se o utilizador efetuar outra pesquisa é necessário limpar a variável marker
    if(marker != undefined && marker != ''){
     marker.setMap(null);
     marker = '';
    }
 
    marker = new google.maps.Marker({
       map: map,
       position: latlng
    });
 
 }
