(function(){
	var modelCache = {},
		conditions = [],
		checkRun = function(){
			for(var c = 0,cl = conditions.length,deps,callback,isOk,isRun,conTmp = []; c < cl; c++){
				deps = conditions[c].deps;
				callback = conditions[c].callback;
				isRun = conditions[c].isRun;
				isOk = true;
				for(var d = 0,dl =deps.length; d <dl; d++){
					if(!modelCache.hasOwnProperty(deps[d])){
						isOk = false;
						break;
					}else if(!modelCache[deps[d]].isRun){
						isOk = false;
						break;
					}
				}
				if(isOk && !isRun){
					callback();
					conditions[c].isRun = true;
					if(conditions[c].hasOwnProperty('model')){
						modelCache[ conditions[c].model].isRun = true;
					}
				}else{
					conTmp.push(conditions[c])
				}
			}
			conditions = [].concat(conTmp);
			conTmp.length = 0;
		};
	window.reachJS = function(model){
		if(!modelCache.hasOwnProperty(model)){
			modelCache[model] = {isRun:true};
		}
		checkRun();
	};
	window.runAs = function(deps,callback,model){
		var conTmp = {
			deps:deps,
			callback:callback,
			isRun:false
		};

		if(model){
			if(!modelCache.hasOwnProperty(model)){
				modelCache[model] = {isRun:false};
				conTmp.model = model;
			}
		}
		conditions.push(conTmp);
		conditions.sort(function(a,b){
			return a.deps.length - b.deps.length;
		});
		checkRun();
	}
})();