apiclient=(function(){
    return {
		getCinemaByName: function(name,callback){            
			$.get( "/cinemas/"+name, function( data ) {
                callback(data);
              });
		},
		getFunction: function(name,date,movieName,callback){
        			$.get( "/cinemas/"+name+"/"+date+"/"+movieName, function( data ) {
                        callback(data);
                      });
        		}
	}
})();