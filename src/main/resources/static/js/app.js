var module = (function () {
  var functions = {};
  var cinemaName = {};
  var saveElements = function (cinema) {    
    functions = cinema.functions;
    cinemaName = cinema.name;
  };
  var printSeats = function (fun) {
    var seats = fun.seats;
    for (var i = 0; i < seats.length; i++) {
        var fila = fun.seats[i];
        var filaTxt = '<li>';
        for (var j = 0; j < fila.length; j++) {
            if(fila[j]){
                filaTxt += '  O  ';
            }else{
                filaTxt += '  X  ';
            };
        };
        filaTxt += '</li>';
        $("#sillas").append(filaTxt);
    };
  };
  var updateFunctionsTable = function(){    
    for (var i = 0; i < functions.length; i++) {
      var fun = functions[i];
      var name = fun.name;
      var date = fun.date;
      var txt = '<tr id="'+ (i + 1)+'">'+
      '<th scope="row">'+cinemaName+'</th>'+
      '<td>'+name+'</td>'+
      '<td>'+fun.seats+'</td>'+
      '<td>'+date+'<td>'+
      '<td><button id="button'+(i+1)+'" type="button" class="btn btn-success"'+
      'onclick="module.showSeats('+
      "'"+cinemaName+"',"+"'"+date+"',"+"'"+name+"'"+
      ')">Buy</button><td>';
      $("#funciones").append(txt);
    };
  };
  var mapFunctions = function (cinema) {
    saveElements(cinema);    
    var mappedFunctions = functions.map(function (fun) {
        var numeroSillas = 0;
        for (var i = 0; i < fun.seats.length; i++) {
        var fila = fun.seats[i];
            for (var j = 0; j < fila.length; j++) {
                 if(fila[j]){
                    numeroSillas ++;
                 };
             };
        };
        var newFunction = new Object();
        newFunction.name = fun.movie.name;
        newFunction.date = fun.date;
        newFunction.seats = numeroSillas;        
    	return newFunction;
    });    
    functions = mappedFunctions;
    return mappedFunctions;
  };
  return {
    publicMethod: function (newName) {
        this.cinemaName = newName;
    },
    showSeats: function (cinemaName,date,movieName) {
        $('#sillas').empty();
        apiclient.getFunction(cinemaName,date,movieName,printSeats);
    },
    saveElements:saveElements,
    updateFunctions:function (){
      $('#funciones').find('tbody').empty();
      var cinemaName = $('#cinemaName').val();      
      if(cinemaName != ''){
        //apimock.getCinemaByName(cinemaName,mapFunctions);
        apiclient.getCinemaByName(cinemaName,mapFunctions);
        updateFunctionsTable();
      };      
    }
  };
})();