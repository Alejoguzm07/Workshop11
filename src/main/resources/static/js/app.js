var module = (function () {
  var functions = {};
  var cinemaName = {};
  var saveElements = function (cinema) {    
    functions = cinema.functions;
    cinemaName = cinema.name;
  };
  var updateFunctionsTable = function(){    
    for (var i = 0; i < functions.length; i++) {
      var fun = functions[i];
      var txt = '<tr id="'+ (i + 1)+'">'+
      '<th scope="row">'+cinemaName+'</th>'+
      '<td>'+fun.name+'</td>'+
      '<td>'+fun.seats+'</td>'+
      '<td>'+fun.date+'<td>';
      $("#funciones").append(txt);
    };
  };
  var mapFunctions = function (cinema) {
    saveElements(cinema);    
    var mappedFunctions = functions.map(function (fun) {
        var numeroSillas = 0;
        for (var i = 0; i < fun.seats.length; i++) {
            numeroSillas += fun.seats[i].length;
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