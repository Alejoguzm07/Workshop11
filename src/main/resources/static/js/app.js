var module = (function () {
  var functions = {};
  var cinemaName = {};
  var saveElements = function (cinema) {
    functions = cinema[0].functions;
    cinemaName = cinema[0].name;
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
    console.log(mappedFunctions);
    return mappedFunctions;
  };
  return {
    publicMethod: function (newName) {
        this.cinemaName = newName;
    },
    saveElements:saveElements,
    updateFunctions:function (cinemaName){
        apimock.getCinemaByName(cinemaName,mapFunctions);
    }
  };
})();