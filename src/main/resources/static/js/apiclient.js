apiclient=(function(){
    return {
		getCinemaByName: function(name,callback){            
			$.get( "/cinemas/"+name, function( data ) {
                callback(data);
              });
		}
	}
})();